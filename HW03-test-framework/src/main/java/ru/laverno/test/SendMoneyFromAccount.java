package ru.laverno.test;

import ru.laverno.annotations.After;
import ru.laverno.annotations.Before;
import ru.laverno.annotations.Test;
import ru.laverno.model.BankAccount;
import ru.laverno.service.BankAccountService;

import java.util.UUID;

public class SendMoneyFromAccount {

    private BankAccount bankAccount;

    private BankAccountService bankAccountService;

    @Before
    public void setAccount() {
        this.bankAccount = new BankAccount(UUID.randomUUID(), "Max Makarov", 10000D);
        this.bankAccountService = new BankAccountService();
    }

    @Test
    public void sendMoneyFromAccountFailed() {
        var sentMoney = 15000D;
        bankAccountService.sendMoneyFromAccount(this.bankAccount, sentMoney);
    }

    @After
    public void setNewAccount() {
        this.bankAccount = new BankAccount(UUID.randomUUID(), "Petr Popov", 1345D);
    }
}
