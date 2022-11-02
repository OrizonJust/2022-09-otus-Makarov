package ru.laverno.test;

import ru.laverno.annotations.After;
import ru.laverno.annotations.Before;
import ru.laverno.annotations.Test;
import ru.laverno.model.BankAccount;
import ru.laverno.service.BankAccountService;

import java.util.UUID;

public class BankAccountServiceTest {

    private BankAccount bankAccount;

    private BankAccountService bankAccountService;

    @Before
    public void setAccount() {
        this.bankAccount = new BankAccount(UUID.randomUUID(), "Max Makarov", 10000D);
        this.bankAccountService = new BankAccountService();
    }

    @After
    public void printSomeInformation() {
        System.out.println(bankAccount);
    }

    @Test
    public void addMoneyToBalance() {
        var additionMoney = 5000D;
        var expectedBalance = this.bankAccount.getAccountBalance() + additionMoney;

        bankAccountService.addMoneyToBalance(this.bankAccount, additionMoney);

        if (expectedBalance != bankAccount.getAccountBalance())
            throw new RuntimeException("Test failed!");
    }

    @Test
    public void blockAccount() {
        if (!bankAccountService.blockAccount(this.bankAccount) && !bankAccount.isBlocked()) {
            throw new RuntimeException("Test Failed");
        }
    }

    @Test
    public void sendMoneyFromAccountFailed() {
        var sentMoney = 15000D;
        bankAccountService.sendMoneyFromAccount(this.bankAccount, sentMoney);
    }
}
