package ru.laverno;

import ru.laverno.annotations.After;
import ru.laverno.annotations.Before;
import ru.laverno.annotations.Test;
import ru.laverno.test.BankAccountServiceTest;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class BankAccountTestStarter {

    public static void main(String[] args) {
        try {
            bankAccountServiceTest();
        } catch (InvocationTargetException | NoSuchMethodException | InstantiationException |
                 IllegalAccessException ex) {
            ex.printStackTrace();
        }
    }

    private static void bankAccountServiceTest() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        Class<?> clazz = BankAccountServiceTest.class;
        startTestClass(clazz);
    }

    private static void startTestClass(Class<?> clazz) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
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

        if (!testMethods.isEmpty()) {
            for (Method testMethod : testMethods) {
                var instance = clazz.getConstructor().newInstance();
                if (beforeMethod != null) {
                    startTestMethod(instance, beforeMethod);
                }
                startTestMethod(instance, testMethod);
                if (afterMethod != null) {
                    startTestMethod(instance, afterMethod);
                }
            }
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
