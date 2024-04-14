package ru.otus.jdbc.mapper;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Stream;

import ru.otus.core.repository.DataTemplate;
import ru.otus.core.repository.DataTemplateException;
import ru.otus.core.repository.executor.DbExecutor;
import ru.otus.crm.model.Client;

/**
 * Сохратяет объект в базу, читает объект из базы
 */
@SuppressWarnings("java:S1068")
public class DataTemplateJdbc<T> implements DataTemplate<T> {

    private final DbExecutor dbExecutor;
    private final EntitySQLMetaData entitySQLMetaData;
    private final EntityClassMetaData<T> entityClassMetaData;

    public DataTemplateJdbc(DbExecutor dbExecutor, EntitySQLMetaData entitySQLMetaData, EntityClassMetaData<T> entityClassMetaData) {
        this.dbExecutor = dbExecutor;
        this.entitySQLMetaData = entitySQLMetaData;
        this.entityClassMetaData = entityClassMetaData;
    }

    @Override
    public Optional<T> findById(Connection connection, Object id) {
        return dbExecutor.executeSelect(connection, entitySQLMetaData.getSelectByIdSql(), List.of(id), rs -> {
            try {
                if (rs.next()) {
                    return buildInstance(rs);
                }
                return null;
            } catch (SQLException e) {
                throw new DataTemplateException(e);
            }
        });
    }

    @Override
    public List<T> findAll(Connection connection) {
        return dbExecutor
                .executeSelect(connection, entitySQLMetaData.getSelectAllSql(), Collections.emptyList(), rs -> {
                    var resultList = new ArrayList<T>();
                    try {
                        while (rs.next()) {
                            resultList.add(buildInstance(rs));
                        }
                        return resultList;
                    } catch (Exception e) {
                        throw new DataTemplateException(e);
                    }
                })
                .orElseThrow(() -> new RuntimeException("Unexpected error"));
    }

    @Override
    public long insert(Connection connection, T object) {
        try {
            return dbExecutor.executeStatement(connection, entitySQLMetaData.getInsertSql(), getNotIdFieldsValues(object));
        } catch (Exception e) {
            throw new DataTemplateException(e);
        }
    }

    @Override
    public void update(Connection connection, T object) {
        var fieldsToUpdate = new ArrayList<>(getAllFieldsValues(object));
        fieldsToUpdate.add(getFieldValue(object, entityClassMetaData.getIdField()));
        try {
            dbExecutor.executeStatement(connection, entitySQLMetaData.getUpdateSql(), fieldsToUpdate);
        } catch (Exception e) {
            throw new DataTemplateException(e);
        }
    }

    private List<Object> getNotIdFieldsValues(T object) {
        return entityClassMetaData.getFieldsWithoutId().stream().map(field -> getFieldValue(object, field)).toList();
    }

    private List<Object> getAllFieldsValues(T object) {
        return entityClassMetaData.getAllFields().stream().map(field -> getFieldValue(object, field)).toList();
    }

    private Object getFieldValue(Object object, Field field){
        try {
            field.setAccessible(true);
            var result = field.get(object);
            field.setAccessible(false);
            return result;
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
    private T buildInstance(ResultSet rs) {
        var fieldsValues = entityClassMetaData.getAllFields()
                .stream().map(field-> {
                    try {
                        return getFieldValue(rs,field.getName(),field.getType());
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }).toArray();
        try {
            return entityClassMetaData.getConstructor().newInstance(fieldsValues);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    private Object getFieldValue(ResultSet rs, String name, Class<?> type) throws SQLException {
        if (type == Long.class) return rs.getLong(name);
        if (type == String.class) return rs.getString(name);
        if (type == BigDecimal.class) return rs.getBigDecimal(name);
        if (type == Float.class) return rs.getFloat(name);
        if (type == Double.class) return rs.getDouble(name);
        if (type == Date.class) return rs.getDate(name);
        if (type == Boolean.class) return rs.getBoolean(name);
        return null;
    }
}
