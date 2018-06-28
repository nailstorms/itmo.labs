package lab8.orm;

import com.google.gson.Gson;

import java.lang.reflect.Field;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

abstract class AbstractSQLWriter<T> {
    private String nameTable;
    private List<String> columns;
    private List<Field> fields;
    private String primaryKey;
    private Field fieldPrimaryKey;
    private Map<Field, String> fieldsWithType;
    private Gson gson;
    private Class<?> aClass;

    public AbstractSQLWriter(Class<?> tClass) {

            this.aClass = tClass;
            FieldCreator fieldCreator = new FieldCreator(tClass);
            this.nameTable = fieldCreator.getNameTable();
            this.fields = fieldCreator.getFields();
            this.columns = fieldCreator.getNameColumns();
            this.primaryKey = fieldCreator.getPrimaryKey();
            this.fieldPrimaryKey = fieldCreator.getFieldPrimaryKey();
            this.fieldsWithType = new HashMap<>();
            this.gson = new Gson();

    }

    public List<String> getColumns() {
        return columns;
    }

    public List<Field> getFields() {
        return fields;
    }

    public String getNameTable() {
        return nameTable;
    }

    public String getPrimaryKey() {
        return primaryKey;
    }

    public Field getFieldPrimaryKey() {
        return fieldPrimaryKey;
    }

    public Map<Field, String> getFieldsWithType() {
        return fieldsWithType;
    }

    public Gson getGson() {
        return gson;
    }

    public Class<?> getaClass() {
        return aClass;
    }
}
