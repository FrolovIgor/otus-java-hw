package ru.otus.logging.service.impl;

import ru.otus.logging.annotations.Log;
import ru.otus.logging.service.SimpleLoggingInterface;

public class SimpleLogging implements SimpleLoggingInterface {
    @Log
    public void parse(int param) {

    }

    @Log
    public void test(int param1, int param2) {

    }

    @Log
    public void parse(int param1, int param2, String param3) {

    }
}
