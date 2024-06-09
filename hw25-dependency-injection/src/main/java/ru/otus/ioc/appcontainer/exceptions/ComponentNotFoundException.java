package ru.otus.ioc.appcontainer.exceptions;

public class ComponentNotFoundException extends RuntimeException {
    public ComponentNotFoundException() {
    }

    public ComponentNotFoundException(String message) {
        super(message);
    }
}
