package ru.laverno;

import java.util.HashMap;
import java.util.Map;

public class Banknotes {

    private final Map<MoneyType, Integer> banknotes;

    public Banknotes() {
        banknotes = new HashMap<>();
    }

    public Integer getCountOfBanknotes(MoneyType key) {
        return banknotes.get(key);
    }

    public Integer getSomeBanknotes(MoneyType key, int count) {
        var countOfBanknotes = banknotes.get(key);
        if (countOfBanknotes >= count) {
            banknotes.replace(key, countOfBanknotes, countOfBanknotes - count);
            return count;
        }
        return 0;
    }

    public void addBanknote(MoneyType key, int value) {
        banknotes.put(key, value);
    }

    public static Map<MoneyType, Integer> newInstancePackOfMoney() {
        var result = new HashMap<MoneyType, Integer>();
        result.put(MoneyType.FIFTY, 0);
        result.put(MoneyType.ONE_HUNDRED, 0);
        result.put(MoneyType.TWO_HUNDREDS, 0);
        result.put(MoneyType.FIVE_HUNDREDS, 0);
        result.put(MoneyType.ONE_THOUSAND, 0);
        result.put(MoneyType.TWO_THOUSANDS, 0);
        result.put(MoneyType.FIVE_THOUSANDS, 0);

        return result;
    }
}
