package ru.otus.logging.service.impl;

import ru.otus.logging.annotations.Log;
import ru.otus.logging.service.TestLoggingInterface;

public class TestLogging implements TestLoggingInterface {
    @Log
    public void calculation(int param) {

    }

    public void calculation(int param1, int param2) {

    }

    @Log
    public void calculation(int param, int param2, String param3) {

    }

}
