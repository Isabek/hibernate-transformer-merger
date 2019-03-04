package com.itashiev.merger.util;

import lombok.NonNull;
import lombok.SneakyThrows;
import org.apache.commons.lang3.reflect.FieldUtils;

import javax.persistence.Id;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Optional;

public class ReflectionUtils {

    private ReflectionUtils() {

    }

    @SneakyThrows
    public static <T> T getFieldValue(Field field, Object instance) {
        field.setAccessible(true);
        return (T) field.get(instance);
    }

    public static Optional<Field> findFirstFieldWithIdAnnotation(Object instance) {
        return FieldUtils.getFieldsListWithAnnotation(instance.getClass(), Id.class).stream().findFirst();
    }

    public static Object getIdFieldValue(Object instance) {
        return findFirstFieldWithIdAnnotation(instance)
                .map(field -> getFieldValue(field, instance))
                .orElse(null);
    }

    public static boolean isCollection(Class clazz) {
        return Collection.class.isAssignableFrom(clazz);
    }
}
