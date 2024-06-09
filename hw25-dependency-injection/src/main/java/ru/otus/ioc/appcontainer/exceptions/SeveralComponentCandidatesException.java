package ru.otus.ioc.appcontainer.exceptions;

public class SeveralComponentCandidatesException extends RuntimeException {
    public SeveralComponentCandidatesException() {
    }

    public SeveralComponentCandidatesException(String message) {
        super(message);
    }
}
