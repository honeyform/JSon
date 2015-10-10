package ua.skillsup.services;

import ua.skillsup.annotations.CustomDateFormat;
import ua.skillsup.annotations.JsonValue;

import java.lang.reflect.Field;
import java.text.ParseException;
import java.time.format.DateTimeFormatter;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

/**
 * Created by Honey on 16.09.2015.
 */
public class JsonMapper {

    public <T> String toJson(T obj) throws IllegalAccessException {

        StringBuilder builder = new StringBuilder();

        builder.append("{");

        Class<?> aClass = obj.getClass();
        Field[] fields = aClass.getDeclaredFields();

        for (int i = 0; i < fields.length; i++) {
            Field field = fields[i];

            parseFieldToString(obj, field, builder);

            if (i + 1 < fields.length) {
                builder.append(",");
            } else {
                builder.append("}");
            }
        }

        return builder.toString();
    }

    private String parseFieldToString(Object o, Field field, StringBuilder builder) throws IllegalAccessException {

        if (!field.isAccessible()) {
            field.setAccessible(true);
        }

        JsonValue annotationName = field.getAnnotation(JsonValue.class);
        CustomDateFormat annotationDateFormat = field.getAnnotation(CustomDateFormat.class);

        Object value = field.get(o);
        if (value != null) {
            builder.append("\"" + ((annotationName == null) ? field.getName() : annotationName.name()) + "\"");
            builder.append(":");
        }

        String[] type = getType(field.getType());
        builder.append(type[0]);

        if (annotationDateFormat == null) {
            builder.append(String.valueOf(field.get(o)));
        } else {
            String newName = annotationDateFormat.format();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(newName);
            String strTime = simpleDateFormat.format(new Date());
            builder.append(strTime);
        }

        builder.append(type[1]);

        return builder.toString();
    }

    private String[] getType(Class<?> fieldType) {

        String[] result = new String[2];
        if (fieldType == String.class ||
                fieldType == LocalDate.class) {
            result[0] = "\"";
            result[1] = "\"";
        } else {
            result[0] = "";
            result[1] = "";
        }
        return result;
    }

    public <T> T fromJson(String json, Class<T> clazz) throws IllegalAccessException, InstantiationException, NoSuchFieldException, ParseException {

        T o = clazz.newInstance();
        Class object = o.getClass();

        Field[] fields = object.getDeclaredFields();

        Object value;
        Object nameField;

        String[] allFields = json.split(",");

        for (int index = 0; index < allFields.length; index++) {
            String withoutExtraCharacter = allFields[index].replaceAll("[{}\",]", "");
            String[] field = withoutExtraCharacter.split(":");
            setValueForField(field[0], field[1], o);
        }
        return o;
    }

    private void setValueForField(String nameField, String value, Object o) throws NoSuchFieldException, IllegalAccessException {
        nameField = setNameWithConsiderationAnnotation(nameField, o);
        Field field = o.getClass().getDeclaredField(nameField);
        Object valueField = null;

        if (field.getType() == LocalDate.class) {
            valueField = setValueWithConsiderationAnnotationTime(value, field, o);
        } else {
            valueField = value;
        }

        if (!field.isAccessible()) {
            field.setAccessible(true);
        }
        field.set(o, valueField);
    }

    private LocalDate setValueWithConsiderationAnnotationTime(String value, Field field, Object o) {
        if (field.isAnnotationPresent(CustomDateFormat.class)) {
            String datePattern = field.getAnnotation(CustomDateFormat.class).format();
            LocalDate date = LocalDate.parse(String.valueOf(value), DateTimeFormatter.ofPattern(datePattern));
            return date;
        }
        return LocalDate.parse((CharSequence) value);
    }

    private String setNameWithConsiderationAnnotation(String nameField, Object o) {
        Field field = null;
        try {
            field = o.getClass().getDeclaredField(nameField);
        } catch (NoSuchFieldException e) {
            for (Field allFields : o.getClass().getDeclaredFields()) {
                if (allFields.isAnnotationPresent(JsonValue.class)) {
                    field = allFields;
                    break;
                }
            }
        }
        return field.getName();
    }
}