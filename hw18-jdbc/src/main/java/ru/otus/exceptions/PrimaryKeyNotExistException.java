package ru.otus.exceptions;

public class PrimaryKeyNotExistException extends RuntimeException {
    public PrimaryKeyNotExistException() {
        super("Field with @Id annotation is required!");
    }
}
