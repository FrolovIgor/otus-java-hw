package ru.otus.logging;

import ru.otus.logging.service.SimpleLoggingInterface;
import ru.otus.logging.service.TestLoggingInterface;
import ru.otus.logging.service.impl.AnotherTestLogging;
import ru.otus.logging.service.impl.SimpleLogging;
import ru.otus.logging.service.impl.TestLogging;

public class TestLoggingDemo {
    public static void main(String[] args) {
        var calc = Ioc.createMyClass(TestLoggingInterface.class, new TestLogging());
        calc.calculation(6);
        calc.calculation(4, 5);
        calc.calculation(4, 5, "Test");

        var calc2 = Ioc.createMyClass(TestLoggingInterface.class, new AnotherTestLogging());
        calc2.calculation(6);
        calc2.calculation(4, 5);
        calc2.calculation(4, 5, "Test");

        var calc3 = Ioc.createMyClass(SimpleLoggingInterface.class, new SimpleLogging());
        calc3.parse(6);
        calc3.test(4, 5);
        calc3.parse(4, 5, "Test");
    }
}
