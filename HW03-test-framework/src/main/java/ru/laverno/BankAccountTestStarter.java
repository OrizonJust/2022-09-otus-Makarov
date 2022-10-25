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
        int passed = 0;
        int failed = 0;

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
                    if (startTestMethod(instance, beforeMethod)) {
                        passed++;
                    } else {
                        failed++;
                    }
                }
                if (startTestMethod(instance, testMethod)) {
                    passed++;
                } else {
                    failed++;
                }
                if (afterMethod != null) {
                    if (startTestMethod(instance, afterMethod)) {
                        passed++;
                    } else {
                        failed++;
                    }
                }
            }
        }

        System.out.println("TESTS PASSED: " + passed + "; TESTS FAILED: " + failed);
    }

    private static boolean startTestMethod(Object obj, Method method) {
        try {
            method.invoke(obj);
        } catch (Exception ex) {
            System.out.println(method.getName() + " FAILED!");
            return false;
        }


        System.out.println(method.getName() + " PASSED!");
        return true;
    }
}
