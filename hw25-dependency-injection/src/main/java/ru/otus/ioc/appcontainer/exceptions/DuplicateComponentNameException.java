package ru.otus.ioc.appcontainer.exceptions;

public class DuplicateComponentNameException extends RuntimeException {
    public DuplicateComponentNameException() {
    }

    public DuplicateComponentNameException(String message) {
        super(message);
    }
}
