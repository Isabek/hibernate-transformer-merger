package com.itashiev.merger.impl;

import com.itashiev.merger.BaseTransformer;
import com.itashiev.merger.util.ReflectionUtils;
import lombok.SneakyThrows;
import org.apache.commons.lang3.reflect.FieldUtils;

import java.lang.reflect.Field;
import java.util.*;

import static com.itashiev.merger.util.ReflectionUtils.findFirstFieldWithIdAnnotation;

public class EntityTransformer implements BaseTransformer {

    @Override
    public Collection transform(List dtos) {
        Collection result = new ArrayList<>();
        if (null == dtos) {
            return result;
        }

        for (Object dto : dtos) {
            Optional<Field> fieldOptional = findFirstFieldWithIdAnnotation(dto);
            if (!fieldOptional.isPresent()) {
                continue;
            }

            Optional existsOptional = getExistingObject(result, dto);

            if (existsOptional.isPresent()) {
                Object existingObject = existsOptional.get();
                merge(existingObject, dto);
            } else {
                result.add(dto);
            }
        }
        return result;
    }

    private Optional getExistingObject(Collection<?> result, Object item) {
        return result.stream()
                .filter(o -> Objects.equals(ReflectionUtils.getIdFieldValue(item), ReflectionUtils.getIdFieldValue(o)))
                .findFirst();
    }

    @SneakyThrows
    private void merge(Object left, Object right) {
        List<Field> leftFields = FieldUtils.getAllFieldsList(left.getClass());
        for (Field leftField : leftFields) {
            if (ReflectionUtils.isCollection(leftField.getType())) {
                Field rightField = FieldUtils.getField(right.getClass(), leftField.getName(), true);

                Collection leftCollection = (Collection) FieldUtils.readField(leftField, left, true);
                Collection rightCollection = (Collection) FieldUtils.readField(rightField, right, true);
                leftField.set(left, mergeCollection(leftCollection, rightCollection));
            }
        }
    }

    private Collection<List> mergeCollection(Collection<Object> left, Collection<Object> right) {

        if (null == left && null == right) {
            return null;
        }

        List result = new ArrayList();

        if (null != left) {
            result.addAll(left);
        }

        if (null != right) {
            result.addAll(right);
        }
        return transform(result);
    }
}
