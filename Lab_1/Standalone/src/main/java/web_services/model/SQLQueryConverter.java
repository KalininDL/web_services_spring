package web_services.model;

import web_services.annotations.QueryClass;
import web_services.annotations.QueryField;
import web_services.errors.SQLConvertException;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Objects;

public class SQLQueryConverter {

    private void checkIfConvertable(Object object) throws SQLConvertException {
        if (Objects.isNull(object)) {
            throw new SQLConvertException("The object to serialize is null");
        }
        Class<?> clazz = object.getClass();
        if (!clazz.isAnnotationPresent(QueryClass.class)) {
            throw new SQLConvertException("The class "
                    + clazz.getSimpleName()
                    + " is not annotated with QueryClass");
        }
    }

    private HashMap<String, String> buildMap(Object object) throws Exception {
        Class<?> clazz = object.getClass();
        HashMap<String, String> queryMap = new HashMap<>();
        for (Field field : clazz.getDeclaredFields()) {
            field.setAccessible(true);
            if (field.isAnnotationPresent(QueryField.class)) {
                if(field.get(object) != null) {
                    if(field.getType() == int.class && (int)field.get(object) > 0)
                        queryMap.put(field.getName(), Integer.toString((int)field.get(object)));
                    if(field.get(object) instanceof String && !((field.get(object))).equals(""))
                        queryMap.put(field.getName(), (String) (field.get(object)));
                }
            }
        }
        return queryMap;
    }

    public HashMap<String, String> getQueryMap(Object object) throws SQLConvertException {
        try {
            checkIfConvertable(object);
            return buildMap(object);
        } catch (Exception e) {
            throw new SQLConvertException(e.getMessage());
        }
    }
}
