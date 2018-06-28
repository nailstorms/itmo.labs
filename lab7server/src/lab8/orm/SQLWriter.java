package lab8.orm;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;


public class SQLWriter<T> extends AbstractSQLWriter<T> {

    public SQLWriter(Class<?> tClass) {
        super(tClass);
    }


    public String create() {
        String query = "create table " + getNameTable() + "(\n";

        for (int i = 0; i < getFields().size(); i++) {

            String type = getType(getFields().get(i));
            String nameTableReferences = getFieldsWithType().get(getFields().get(i));

            if (nameTableReferences != null) {

                query += getColumns().get(i) + " " + type + " references " + nameTableReferences + " on delete cascade,\n";


            } else {
                query += getColumns().get(i) + " " + type + " ,\n";
            }

        }

        if (getPrimaryKey() != null) {
            query += "primary key (" + getPrimaryKey() + "))";
        } else {
            query = query.substring(0, query.length() - 2) + ")";
        }
        return query;
    }


    public String insert(T object) {
        StringBuilder result = new StringBuilder();
        result.append("insert into " + getNameTable() + " values(");

        getFields().stream().forEach(
                e -> {
                    e.setAccessible(true);
                    result.append(getValue(e, object) + ",");
                }
        );

        result.deleteCharAt(result.length() - 1);
        result.append(")");
        return result.toString();
    }



//    public String update(T object) {
//        return true;
//    }



    public String delete(T object) {
        StringBuilder result = new StringBuilder();

        result.append("delete from " + getNameTable() + " where " + getPrimaryKey() + " = " + getValue(getFieldPrimaryKey(), object));

        return result.toString();
    }



    public String dropTable() {
        StringBuilder result = new StringBuilder();
        result.append("drop table " + getNameTable());

        return result.toString();
    }


    public String getType(Field field) {
        Class type = field.getType();

        if (type == String.class) {
            return "varchar(30)";
        } else if (type == int.class) {
            return "integer";
        } else if (type == double.class) {
            return "double precision";
        } else
            return "jsonb";
    }


    public String getValue(Field field, T object) {
        Object result = null;

        try {
            result = field.get(object);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            System.out.println("Нулевое значение");
        }

        if (result instanceof String) {
            return "'" + result + "'";
        } else if (Integer.class.isInstance(result)) {
            return Integer.toString((int) result);
        } else if (Double.class.isInstance(result)) {
            return Double.toString((double) result);
        } else if (result == null) {
            return null;

        } else {
            return "'" + getGson().toJson(result) + "'";
        }
    }


    public T getElement(ResultSet resultSet) {
        Field[] fields = getaClass().getDeclaredFields();

        try {
            Object object = getaClass().newInstance();

            Arrays.stream(fields).forEach(
                    e -> {
                        if (e.getAnnotation(Column.class) != null) {
                            e.setAccessible(true);
                            Object type = e.getType();

                            try {
                                if (type == int.class) {
                                    e.set(object, Integer.parseInt(resultSet.getString(e.getAnnotation(Column.class).name())));
                                } else if (type == double.class) {
                                    e.set(object, Double.parseDouble(resultSet.getString(e.getAnnotation(Column.class).name())));
                                } else {
                                    e.set(object, getGson().fromJson(resultSet.getString(e.getAnnotation(Column.class).name()), e.getType()));

                                }
                            } catch (IllegalAccessException | SQLException e1) {
                                e1.printStackTrace();
                            }
                        }
                    }
            );

            return (T) object;


        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }

        return null;

    }


    public String getAllElements() {
        String res = null;
        try {
            res = "select * from " + getNameTable() + ";";

        } catch (Exception ex) {
        } finally {
            return res;
        }
    }


}
