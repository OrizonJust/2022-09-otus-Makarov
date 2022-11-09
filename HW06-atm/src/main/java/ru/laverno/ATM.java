package ru.laverno;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;

public class ATM {

    private final Banknotes banknotes;

    public ATM() {
        this.banknotes = new Banknotes();
    }

    public Banknotes getBanknotes() {
        return this.banknotes;
    }

    public void addPackOfMoney(Map<MoneyType, Integer> money) {
        for (var type : MoneyType.values()) {
            if (money.containsKey(type)) {
                banknotes.addBanknote(type, money.get(type));
            }
        }
    }

    public Map<MoneyType, Integer> getPackOfMoney(Integer moneyNeed) {
        if (!isATMCanDispenseMoney(moneyNeed)) {
            throw new IllegalArgumentException();
        }
        var packOfMoney = Banknotes.newInstancePackOfMoney();

        calculationMoneyNeed(packOfMoney, moneyNeed);

        return packOfMoney;
    }

    public void printRestOfMoney() {
        for (var banknote : MoneyType.values()) {
            if (banknotes.getCountOfBanknotes(banknote) > 0) {
                System.out.printf("%d - %d\n", banknote.getMoneyDenomination(), banknotes.getCountOfBanknotes(banknote));
            }
        }
    }

    private void calculationMoneyNeed(Map<MoneyType, Integer> packOfMoney, Integer moneyNeed) {
        var list = Arrays.asList(MoneyType.values());
        Collections.reverse(list);
        for (var banknote : list) {
            if (moneyNeed >= banknote.getMoneyDenomination()
                    && banknotes.getCountOfBanknotes(banknote) > 0) {
                while (moneyNeed >= banknote.getMoneyDenomination()
                        && banknotes.getCountOfBanknotes(banknote) > 0) {
                    moneyNeed -= banknote.getMoneyDenomination();
                    packOfMoney.replace(
                            banknote,
                            banknotes.getSomeBanknotes(banknote, 1) + packOfMoney.get(banknote));
                }
            }
        }
    }

    private boolean isATMCanDispenseMoney(Integer moneyNeed) {
        var list = Arrays.asList(MoneyType.values());
        Collections.reverse(list);
        for (var banknote : list) {
            if (banknotes.getCountOfBanknotes(banknote) > 0) {
                var count = moneyNeed / banknote.getMoneyDenomination();
                if (count > banknotes.getCountOfBanknotes(banknote))
                    return false;
                moneyNeed -=count * banknote.getMoneyDenomination();
            }
        }

        return moneyNeed == 0;
    }
}
