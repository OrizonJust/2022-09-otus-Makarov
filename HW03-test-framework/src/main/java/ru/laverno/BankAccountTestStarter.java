package ru.laverno;

import ru.laverno.test.AddMoneyToAccountTest;
import ru.laverno.test.BlockAccountTest;
import ru.laverno.test.SendMoneyFromAccount;
import ru.laverno.utils.TestUtils;

import java.lang.reflect.InvocationTargetException;

public class BankAccountTestStarter {

    public static void main(String[] args) {
        try {
            addMoneyTest();
            sendMoneyTest();
            blockAccount();
        } catch (InvocationTargetException | NoSuchMethodException | InstantiationException |
                 IllegalAccessException ex) {
            ex.printStackTrace();
        }
    }

    private static void addMoneyTest() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        Class<?> clazz = AddMoneyToAccountTest.class;
        TestUtils.startTestClass(clazz);
    }

    private static void sendMoneyTest() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        Class<?> clazz = BlockAccountTest.class;
        TestUtils.startTestClass(clazz);
    }

    private static void blockAccount() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        Class<?> clazz = SendMoneyFromAccount.class;
        TestUtils.startTestClass(clazz);
    }
}
