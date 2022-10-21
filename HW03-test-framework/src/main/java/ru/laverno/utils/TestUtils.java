package ru.laverno.utils;

import lombok.NoArgsConstructor;
import ru.laverno.annotations.After;
import ru.laverno.annotations.Before;
import ru.laverno.annotations.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class TestUtils {

    public static void startTestClass(Class<?> clazz) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        var instance = clazz.getConstructor().newInstance();
        var methods = clazz.getDeclaredMethods();
        Method beforeMethod = null;
        List<Method> testMethods = new ArrayList<>();
        Method afterMethod = null;
        for (Method method : methods) {
            if (method.isAnnotationPresent(Before.class)) {
                beforeMethod = method;
            }
            if (method.isAnnotationPresent(Test.class)) {
                testMethods.add(method);
            }
            if (method.isAnnotationPresent(After.class)) {
                afterMethod = method;
            }
        }

        if (beforeMethod != null) {
            TestUtils.startTestMethod(instance, beforeMethod);
        }
        if (!testMethods.isEmpty()) {
            for (Method testMethod : testMethods) {
                TestUtils.startTestMethod(instance, testMethod);
            }
        }
        if (afterMethod != null) {
            TestUtils.startTestMethod(instance, afterMethod);
        }
    }

    private static void startTestMethod(Object obj, Method method) {
        var testMethodIsCrashed = false;

        try {
            method.invoke(obj);
        } catch (Exception ex) {
            System.out.println(method.getName() + " is FAILED!");
            testMethodIsCrashed = true;
        }

        if (!testMethodIsCrashed) {
            System.out.println(method.getName() + " FINISHED!");
        }
    }
}
