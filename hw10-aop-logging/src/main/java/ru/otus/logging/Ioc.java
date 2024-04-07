package ru.otus.logging;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;

import ru.otus.logging.annotations.Log;

class Ioc {
    private Ioc() {
    }

    static<I,E extends I> I createMyClass(Class<I> interfaceType, E object) {
        InvocationHandler handler = new LoggerInvocationHandler<I>(object);
        return (I) Proxy.newProxyInstance(Ioc.class.getClassLoader(), new Class<?>[]{interfaceType}, handler);
    }

    static class LoggerInvocationHandler<T> implements InvocationHandler {
        private final T proxyTarget;

        LoggerInvocationHandler(T proxyTarget) {
            this.proxyTarget = proxyTarget;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

            if (proxyTarget.getClass().getMethod(method.getName(), method.getParameterTypes()).isAnnotationPresent(Log.class)) {
                System.out.printf("executed method: %s, param: %s%n", method.getName(), Arrays.stream(args).toList());
            }
            return method.invoke(proxyTarget, args);
        }
    }
}
