package ru.otus.jdbc.mapper.impl;

import ru.otus.jdbc.mapper.EntityClassMetaData;
import ru.otus.jdbc.mapper.EntitySQLMetaData;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class EntitySQLMetaDataImpl<T> implements EntitySQLMetaData {
    private final EntityClassMetaData<T> classMetaData;
    private final String idFieldName;
    private final List<String> fieldsNames;
    private final String selectAllSql;
    private final String selectByIdSql;
    private final String insertSql;
    private final String updateSql;
    private final String PREPARED_STATEMENT_FIELD_STUB = "?";

    public EntitySQLMetaDataImpl(EntityClassMetaData<T> classMetaData) {
        this.classMetaData = classMetaData;
        this.idFieldName = classMetaData.getIdField().getName();
        var notIdFields = classMetaData.getFieldsWithoutId();
        int notIdFieldsAmount = notIdFields.size();
        String notIdFieldsNames = notIdFields.stream().map(Field::getName).collect(Collectors.joining(","));
        this.fieldsNames = classMetaData.getAllFields().stream().map(Field::getName).toList();
        String tableName = classMetaData.getName();
        this.selectAllSql = "SELECT * FROM %s".formatted(tableName);
        this.selectByIdSql = "SELECT * FROM %s where %s".formatted(tableName, getIdWhereExpression());
        this.insertSql = "INSERT INTO %s(%s) VALUES (%s)".formatted(tableName, notIdFieldsNames, getStubs(notIdFieldsAmount));
        this.updateSql = "UPDATE %s SET %s WHERE %s".formatted(tableName, getUpdateExpressions(), getIdWhereExpression());
    }

    @Override
    public String getSelectAllSql() {
        return this.selectAllSql;
    }

    @Override
    public String getSelectByIdSql() {
        return this.selectByIdSql;
    }

    @Override
    public String getInsertSql() {
        return this.insertSql;
    }

    @Override
    public String getUpdateSql() {
        return this.updateSql;
    }

    private String getIdWhereExpression() {
        return "%s = ?".formatted(idFieldName);
    }

    private String getStubs(int n) {
        return String.join(",", Collections.nCopies(n, PREPARED_STATEMENT_FIELD_STUB));
    }

    private String getUpdateExpressions() {
        return fieldsNames.stream().map(fieldName -> "%s = %s".formatted(fieldName, PREPARED_STATEMENT_FIELD_STUB)).collect(Collectors.joining(","));
    }
}
