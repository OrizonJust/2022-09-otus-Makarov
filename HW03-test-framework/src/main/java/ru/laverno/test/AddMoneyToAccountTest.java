package ru.laverno.test;

import ru.laverno.annotations.After;
import ru.laverno.annotations.Before;
import ru.laverno.annotations.Test;
import ru.laverno.model.BankAccount;
import ru.laverno.service.BankAccountService;

import java.util.UUID;

public class AddMoneyToAccountTest {

    private BankAccount bankAccount;

    private BankAccountService bankAccountService;

    @Before
    public void setAccount() {
        this.bankAccount = new BankAccount(UUID.randomUUID(), "Max Makarov", 10000D);
        this.bankAccountService = new BankAccountService();
    }

    @Test
    public void addMoneyToBalance() {
        var additionMoney = 5000D;
        var expectedBalance = this.bankAccount.getAccountBalance() + additionMoney;

        bankAccountService.addMoneyToBalance(this.bankAccount, additionMoney);

        if (expectedBalance != bankAccount.getAccountBalance())
            throw new RuntimeException("Test failed!");
    }

    @After
    public void setNewAccount() {
        this.bankAccount = new BankAccount(UUID.randomUUID(), "Petr Popov", 1345D);
    }
}
