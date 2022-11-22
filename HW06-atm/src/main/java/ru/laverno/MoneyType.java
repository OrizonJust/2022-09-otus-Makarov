package ru.laverno;

public enum MoneyType {

    FIFTY(50),
    ONE_HUNDRED(100),
    TWO_HUNDREDS(200),
    FIVE_HUNDREDS(500),
    ONE_THOUSAND(1000),
    TWO_THOUSANDS(2000),
    FIVE_THOUSANDS(5000);

    private final int moneyDenomination;

    MoneyType(int moneyDenomination) {
        this.moneyDenomination = moneyDenomination;
    }

    public int getMoneyDenomination() {
        return this.moneyDenomination;
    }
}
