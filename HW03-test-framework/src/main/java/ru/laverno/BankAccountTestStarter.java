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
        List<Method> testMethods = new ArrayList<>();
        Method beforeMethod = null;
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

                var isFailed = beforeMethod != null && startTestMethod(instance, beforeMethod);

                if (startTestMethod(instance, testMethod)) {
                    isFailed = true;
                }
                if (afterMethod != null && startTestMethod(instance, afterMethod)) {
                    isFailed = true;
                }

                if (isFailed) {
                    failed++;
                } else {
                    passed++;
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
            return true;
        }


        System.out.println(method.getName() + " PASSED!");
        return false;
    }
}
