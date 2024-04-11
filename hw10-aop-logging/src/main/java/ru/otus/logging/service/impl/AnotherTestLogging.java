package ru.otus.logging.service.impl;

import ru.otus.logging.annotations.Log;
import ru.otus.logging.service.TestLoggingInterface;

public class AnotherTestLogging implements TestLoggingInterface {
    @Log
    public void calculation(int param) {

    }

    @Log
    public void calculation(int param1, int param2) {

    }

    public void calculation(int param, int param2, String param3) {

    }

}
