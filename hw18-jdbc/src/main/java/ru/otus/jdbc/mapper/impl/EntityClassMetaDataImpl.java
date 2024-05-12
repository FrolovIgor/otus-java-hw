package ru.otus.jdbc.mapper.impl;

import ru.otus.annotations.Id;
import ru.otus.exceptions.PrimaryKeyNotExistException;
import ru.otus.jdbc.mapper.EntityClassMetaData;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EntityClassMetaDataImpl<T> implements EntityClassMetaData<T> {
    private final Class<T> clazz;
    private final String name;
    private final List<Field> fields;
    private final Field idField;
    private final List<Field> fieldsWithoutId;
    private final Constructor<T> constructor;

    public EntityClassMetaDataImpl(Class<T> clazz) {
        this.clazz = clazz;
        this.fields = Arrays.asList(this.clazz.getDeclaredFields());
        this.name = this.clazz.getSimpleName();
        this.constructor = buildConstructor();
        this.fieldsWithoutId = fields.stream().filter(x -> !x.isAnnotationPresent(Id.class)).toList();
        this.idField = fields.stream().filter(x -> x.isAnnotationPresent(Id.class)).findFirst().orElseThrow(PrimaryKeyNotExistException::new);
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public Constructor<T> getConstructor() {
        return this.constructor;
    }

    @Override
    public Field getIdField() {
        return this.idField;
    }

    @Override
    public List<Field> getAllFields() {
        return fields;
    }

    @Override
    public List<Field> getFieldsWithoutId() {
        return this.fieldsWithoutId;
    }

    private Constructor<T> buildConstructor() {
        try {
            List<Class<?>> types = new ArrayList<>();
            fields.forEach(x -> types.add(x.getType()));
            return clazz.getConstructor(types.toArray(new Class<?>[fields.size()]));
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }
}
