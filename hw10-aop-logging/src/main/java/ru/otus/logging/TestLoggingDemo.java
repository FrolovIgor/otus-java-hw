package ru.otus.logging;

import ru.otus.logging.service.TestLoggingInterface;

public class TestLoggingDemo {
    public static void main(String[] args) {
        TestLoggingInterface calc = Ioc.createMyClass();
        calc.calculation(6);
        calc.calculation(4, 5);
        calc.calculation(4, 5, "Test");
    }
}
