package ru.laverno.service;

import ru.laverno.model.BankAccount;

public class BankAccountService {

    public void addMoneyToBalance(BankAccount bankAccount, Double additionMoney) {
        bankAccount.setAccountBalance(bankAccount.getAccountBalance() + additionMoney);
    }

    public void sendMoneyFromAccount(BankAccount bankAccount, Double sentMoney) {
        if (sentMoney > bankAccount.getAccountBalance()) {
            throw new RuntimeException("Account has not enough money!");
        }

        bankAccount.setAccountBalance(bankAccount.getAccountBalance() - sentMoney);
    }

    public boolean blockAccount(BankAccount bankAccount) {
        try {
            bankAccount.blockAccount();
            return true;
        } catch (RuntimeException ex) {
            return false;
        }
    }
}
