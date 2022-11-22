package ru.laverno;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;

public class ATM implements IATM {

    private final Banknotes banknotes;

    public ATM() {
        this.banknotes = new Banknotes(0, 0, 0, 0, 0, 0, 0);
    }

    public ATM(final int fifth, final int oneHundred, final int twoHundred, final int fiveHundred, final int oneThousand, final int twoThousand, final int fiveThousand) {
        this.banknotes = new Banknotes(fifth, oneHundred, twoHundred, fiveHundred, oneThousand, twoThousand, fiveThousand);
    }

    public Banknotes getBanknotes() {
        return this.banknotes;
    }

    @Override
    public void addMoney(final Map<MoneyType, Integer> money) {
        for (final var type : MoneyType.values()) {
            if (money.containsKey(type)) {
                banknotes.addBanknote(type, money.get(type));
            }
        }
    }

    @Override
    public Map<MoneyType, Integer> getMoney(final int moneyNeed) {
        if (!isATMCanDispenseMoney(moneyNeed)) {
            throw new IllegalArgumentException();
        }
        var packOfMoney = Banknotes.newInstancePackOfMoney(0, 0, 0, 0, 0, 0, 0);

        calculationMoneyNeed(packOfMoney, moneyNeed);

        return packOfMoney;
    }

    @Override
    public void printInfo() {
        for (final var banknote : MoneyType.values()) {
            if (banknotes.getCountOfBanknotes(banknote) > 0) {
                System.out.printf("%d - %d\n", banknote.getMoneyDenomination(), banknotes.getCountOfBanknotes(banknote));
            }
        }
    }

    private void calculationMoneyNeed(final Map<MoneyType, Integer> packOfMoney, int moneyNeed) {
        final var list = Arrays.asList(MoneyType.values());
        Collections.reverse(list);
        for (final var banknote : list) {
            if (moneyNeed >= banknote.getMoneyDenomination() && banknotes.getCountOfBanknotes(banknote) > 0) {
                while (moneyNeed >= banknote.getMoneyDenomination() && banknotes.getCountOfBanknotes(banknote) > 0) {
                    moneyNeed -= banknote.getMoneyDenomination();
                    packOfMoney.replace(banknote, banknotes.getSomeBanknotes(banknote, 1) + packOfMoney.get(banknote));
                }
            }
        }
    }

    private boolean isATMCanDispenseMoney(int moneyNeed) {
        final var list = Arrays.asList(MoneyType.values());
        Collections.reverse(list);
        for (final var banknote : list) {
            if (banknotes.getCountOfBanknotes(banknote) > 0) {
                final var count = moneyNeed / banknote.getMoneyDenomination();
                if (count > banknotes.getCountOfBanknotes(banknote)) return false;
                moneyNeed -= count * banknote.getMoneyDenomination();
            }
        }

        return moneyNeed == 0;
    }
}
