package lab8.orm;


import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SQL<T> {
    private Class<T> tableClass;
    private String tableName;
    private String pkColumn;
    private Map<String, String> columnsWithType = new HashMap<>();

    public SQL(Class<T> tableClass) {
        this.tableClass = tableClass;
        this.tableName = tableClass.getDeclaredAnnotation(Table.class).name();
        analyzeTable();
    }

    private void analyzeTable() {
        for (Field f : tableClass.getDeclaredFields()) {
            if (Modifier.isPublic(f.getModifiers())) {
                String type = "text";

                if (f.getType() == int.class || f.getType() == Integer.class)
                    type = "integer";
                else if (f.getType() == double.class || f.getType() == Double.class)
                    type = "double precision";
                else if (f.getType() == LocalDateTime.class)
                    type = "timestamp";

                columnsWithType.put(f.getName(), type);

                if (f.getAnnotation(PrimaryKey.class) != null) {
                    pkColumn = f.getName();
                }
            }
        }
    }

    public String createTable() {
        StringBuilder sb = new StringBuilder("CREATE TABLE " + tableName + " (");

        for (Map.Entry<String, String> column : columnsWithType.entrySet()) {
            String columnName = column.getKey();
            String type = column.getValue();

            sb.append(columnName);

            if (columnName.equals(pkColumn))
                sb.append(" serial PRIMARY KEY");
            else
                sb.append(" " + type);
            sb.append(",");
        }

        sb.deleteCharAt(sb.length() - 1);
        sb.append(")");
        return sb.toString();
    }

    public String insert(T object) {
        StringBuilder sb = new StringBuilder("INSERT INTO " + tableName + " (");

        for (Field f : tableClass.getDeclaredFields())
            if (!f.getName().equals(pkColumn))
                sb.append(f.getName() + ",");

        sb.deleteCharAt(sb.length() - 1);
        sb.append(") VALUES (");

        for (Field f : tableClass.getDeclaredFields())
            if (!f.getName().equals(pkColumn))
                sb.append(getValueOfField(f, object) + ",");

        sb.deleteCharAt(sb.length() - 1);
        sb.append(") RETURNING " + pkColumn);
        return sb.toString();
    }

    public String update(T object) {
        StringBuilder sb = new StringBuilder("UPDATE " + tableName + " SET ");

        String pkValue = "-1";

        for (Field f : tableClass.getDeclaredFields()) {
            if (!f.getName().equals(pkColumn))
                sb.append(f.getName() + " = " + getValueOfField(f, object) + ",");
            else {
                try {
                    pkValue = Integer.toString((int) f.get(object));
                }
                catch (IllegalAccessException e) { }
            }
        }

        sb.deleteCharAt(sb.length() - 1);
        sb.append(" WHERE " + pkColumn + " = " + pkValue);
        return sb.toString();
    }

    public String delete(T object) {
        String pkValue = "-1";

        for (Field f : tableClass.getDeclaredFields())
            if (f.getName().equals(pkColumn))
                try {
                    pkValue = Integer.toString((int) f.get(object));
                }
                catch (IllegalAccessException e) { }

        return "DELETE FROM " + tableName + " WHERE " + pkColumn + " = " + pkValue;
    }

    public String deleteAll() {

        return "DELETE FROM " + tableName;
    }

    public String selectAll() {
        return "SELECT * FROM " + tableName;
    }

    public List<T> resultsToObjects(ResultSet results) {
        List<T> objects = new ArrayList<>();

        try {
            while (results.next()) {
                T object = (T) tableClass.newInstance();

                for (String column : columnsWithType.keySet()) {
                    Field f = tableClass.getField(column);

                    if (results.getObject(column) == null)
                        f.set(object, null);
                    else if (f.getType() == String.class)
                        f.set(object, results.getString(column));
                    else if (f.getType() == int.class || f.getType() == Integer.class)
                        f.set(object, results.getInt(column));
                    else if (f.getType() == double.class || f.getType() == Double.class)
                        f.set(object, results.getDouble(column));
                    else if (f.getType() == LocalDateTime.class)
                        f.set(object, LocalDateTime.parse(results.getString(column), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
                    else if (f.getType().isEnum())
                        f.set(object, Enum.valueOf((Class<Enum>) f.getType(), results.getString(column)));
                }

                objects.add(object);
            }
        }
        catch (Exception e) { e.printStackTrace();}

        return objects;
    }

    private String getValueOfField(Field field, T object) {
        try {
            Object result = field.get(object);

            if (result == null)
                return null;
            if (result instanceof String)
                return "'" + result + "'";
            else if (Integer.class.isInstance(result))
                return Integer.toString((int) result);
            else if (Double.class.isInstance(result))
                return Double.toString((double) result);
            else if (result instanceof LocalDateTime)
                return "'" + ((LocalDateTime) result).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) + "'";
            else if (field.getType().isEnum())
                return "'" + result.toString() + "'";
            else return null;
        } catch (IllegalAccessException e) {
            return null;
        }
    }
}