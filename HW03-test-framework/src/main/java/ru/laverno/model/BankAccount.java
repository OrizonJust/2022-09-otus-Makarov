package ru.laverno.model;

import lombok.Data;

import java.util.UUID;

@Data
public class BankAccount {

    private UUID accountId;

    private String personName;

    private Double accountBalance;

    private boolean blocked;

    public BankAccount(UUID accountId, String personName, Double accountBalance) {
        this.accountId = accountId;
        this.personName = personName;
        this.accountBalance = accountBalance;
        this.blocked = false;
    }

    public void blockAccount() {
        if (!blocked) {
            this.blocked = true;
        } else {
            throw new RuntimeException("Account is already blocked!");
        }

    }
}
