package ru.otus.logging;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;

import ru.otus.logging.annotations.Log;
import ru.otus.logging.service.TestLoggingInterface;
import ru.otus.logging.service.impl.TestLogging;

class Ioc {
    private Ioc() {
    }

    static TestLoggingInterface createMyClass() {
        InvocationHandler handler = new DemoInvocationHandler(new TestLogging());
        return (TestLoggingInterface)
                Proxy.newProxyInstance(Ioc.class.getClassLoader(), new Class<?>[]{TestLoggingInterface.class}, handler);
    }

    static class DemoInvocationHandler implements InvocationHandler {
        private final TestLoggingInterface myClass;

        DemoInvocationHandler(TestLogging myClass) {
            this.myClass = myClass;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

            if (myClass.getClass().getMethod(method.getName(), method.getParameterTypes()).isAnnotationPresent(Log.class)) {
                System.out.printf("executed method: %s, param: %s%n", method.getName(), Arrays.stream(args).toList());
            }
            return method.invoke(myClass, args);
        }
    }
}
