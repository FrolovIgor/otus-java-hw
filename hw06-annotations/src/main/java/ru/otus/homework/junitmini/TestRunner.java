package ru.otus.homework.junitmini;

import ru.otus.homework.annotations.After;
import ru.otus.homework.annotations.Before;
import ru.otus.homework.annotations.Test;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

public class TestRunner {
    private static Logger log = null;

    static {
        System.setProperty("java.util.logging.SimpleFormatter.format",
                "[%1$tF %1$tT] [%4$-7s] %5$s %n");
        log = Logger.getLogger(TestRunner.class.getName());
    }

    public void runTestsByClassName(String className) {
        mediumSplitter();
        log.info("Run tests in " + className);
        Class<?> clazz = getClassByName(className);
        Constructor<?> constructor = getConstructor(clazz);
        Method[] declaredMethods = clazz.getDeclaredMethods();
        var beforeMethods = getBeforeMethods(declaredMethods);
        var afterMethods = getAfterMethods(declaredMethods);
        var testMethods = getTestMethods(declaredMethods);
        var allTestsCount = testMethods.size();
        var successTestsCount = 0;
        var failureTestsCount = 0;

        for (Method method : testMethods) {
            try {
                runTest(constructor,method,beforeMethods,afterMethods);
                successTestsCount++;
                log.info("test %s succeeded!".formatted(method.getName()));
            } catch (Exception e) {
                failureTestsCount++;
                log.warning("test %s failed!".formatted(method.getName()));
            }
        }

        smallSplitter();
        printStatistics(className, successTestsCount, failureTestsCount, allTestsCount);
    }

    private void runTest(Constructor<?> constructor, Method testMethod, List<Method> beforeMethods, List<Method> afterMethods) {
        var object = getNewInstance(constructor);
        invokeMethods(beforeMethods, object);
        invokeMethod(testMethod, object);
        invokeMethods(afterMethods, object);
    }

    private Class<?> getClassByName(String className) {
        try {
            return Class.forName(className);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    private Constructor<?> getConstructor(Class<?> clazz) {
        try {
            return clazz.getConstructor();
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    private Object getNewInstance(Constructor<?> constructor) {
        try {
            return constructor.newInstance();
        } catch (InstantiationException | InvocationTargetException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private void printStatistics(String className, Integer success, Integer failure, Integer total) {
        var statsMessage = "Success %s, Failure %s, Total %s".formatted(success, failure, total);
        if (total.equals(success))
            log.info("Tests passed in %s: ".formatted(className) + statsMessage);
        else log.warning("Tests failed in %s: ".formatted(className) + statsMessage);
    }

    private void printSplitter(int count) {
        for (int i = 0; i < count; i++) log.info("========================");
    }

    private void smallSplitter() {
        printSplitter(1);
    }

    private void mediumSplitter() {
        printSplitter(2);
    }

    private void largeSplitter() {
        printSplitter(3);
    }

    private List<Method> getFilteredMethods(Method[] methods, Class<? extends Annotation> annotationClass) {
        return Arrays.stream(methods).filter(method -> method.isAnnotationPresent(annotationClass)).toList();
    }

    private List<Method> getBeforeMethods(Method[] methods) {
        return getFilteredMethods(methods, Before.class);
    }

    private List<Method> getAfterMethods(Method[] methods) {
        return getFilteredMethods(methods, After.class);
    }

    private List<Method> getTestMethods(Method[] methods) {
        return getFilteredMethods(methods, Test.class);
    }

    private void invokeMethod(Method method, Object object) {
        try {
            if (method.canAccess(object)) {

                method.invoke(object);

            } else {
                method.setAccessible(true);
                method.invoke(object);
                method.setAccessible(false);
            }
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    private void invokeMethods(List<Method> methods, Object object) {
        methods.forEach(method -> {
            invokeMethod(method, object);
        });
    }
}
