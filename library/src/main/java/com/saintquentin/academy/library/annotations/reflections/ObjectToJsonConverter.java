package com.saintquentin.academy.customannotations.annotations.reflections;

import com.saintquentin.academy.customannotations.annotations.interfaces.Format;
import com.saintquentin.academy.customannotations.annotations.interfaces.JsonElement;
import com.saintquentin.academy.customannotations.annotations.interfaces.JsonSerializable;
import com.saintquentin.academy.customannotations.exceptions.JsonSerializationException;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class ObjectToJsonConverter {
    private void checkIfSerializable(Object object) {
        if (Objects.isNull(object)) {
            throw new JsonSerializationException("Can't serialize a null object!");
        }

        // class is a reserved word :(
        Class<?> clazz = object.getClass();

        if (!clazz.isAnnotationPresent(JsonSerializable.class)) {
            throw new JsonSerializationException("The class " + clazz.getSimpleName() + " is not annotated with JsonSerializable!");
        }
    }

    private void formatObject(Object object) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        Class<?> clazz = object.getClass();

        for (Method method: clazz.getDeclaredMethods()) {
            if (method.isAnnotationPresent(Format.class)) {
                method.setAccessible(true);
                method.invoke(object);
            }
        }
    }

    private String getKey(Field field) {
        String value = field.getAnnotation(JsonElement.class).key();
        return value.isEmpty() ? field.getName() : value;
    }

    private String getJsonString(Object object) throws IllegalArgumentException, IllegalAccessException {
        Class<?> clazz = object.getClass();
        Map<String, String> jsonElementsMap = new HashMap<>();

        for (Field field: clazz.getDeclaredFields()) {
            field.setAccessible(true);

            if (field.isAnnotationPresent(JsonElement.class)) {
                jsonElementsMap.put(getKey(field), (String) field.get(object));
            }
        }

        String jsonString = jsonElementsMap.entrySet()
                .stream()
                .map(entry -> "\"" + entry.getKey() + "\":\"" + entry.getValue() + "\"")
                .collect(Collectors.joining(","));

        return "{" + jsonString + "}";
    }

    public String convertToJson(Object object) throws JsonSerializationException {
        try {
            checkIfSerializable(object);
            formatObject(object);
            return getJsonString(object);
        } catch (Exception exception) {
            throw new JsonSerializationException(exception.getMessage());
        }
    }
}
