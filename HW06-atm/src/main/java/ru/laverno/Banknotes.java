package ru.laverno;

import java.util.HashMap;
import java.util.Map;

public class Banknotes {

    private final Map<MoneyType, Integer> banknotes;

    public Banknotes(final int fifth, final int oneHundred, final int twoHundred, final int fiveHundred, final int oneThousand, final int twoThousand, final int fiveThousand) {
        banknotes = newInstancePackOfMoney(fifth, oneHundred, twoHundred, fiveHundred, oneThousand, twoThousand, fiveThousand);
    }

    public Integer getCountOfBanknotes(final MoneyType key) {
        return banknotes.get(key);
    }

    public Integer getSomeBanknotes(final MoneyType key, final int count) {
        final var countOfBanknotes = banknotes.get(key);
        if (countOfBanknotes >= count) {
            banknotes.replace(key, countOfBanknotes, countOfBanknotes - count);
            return count;
        }
        return 0;
    }

    public void addBanknote(final MoneyType key, final int value) {
        banknotes.put(key, value);
    }

    public static Map<MoneyType, Integer> newInstancePackOfMoney(final int fifth, final int oneHundred, final int twoHundred, final int fiveHundred,
                                                                 final int oneThousand, final int twoThousand, final int fiveThousand) {
        final var result = new HashMap<MoneyType, Integer>();
        result.put(MoneyType.FIFTY, fifth);
        result.put(MoneyType.ONE_HUNDRED, oneHundred);
        result.put(MoneyType.TWO_HUNDREDS, twoHundred);
        result.put(MoneyType.FIVE_HUNDREDS, fiveHundred);
        result.put(MoneyType.ONE_THOUSAND, oneThousand);
        result.put(MoneyType.TWO_THOUSANDS, twoThousand);
        result.put(MoneyType.FIVE_THOUSANDS, fiveThousand);

        return result;
    }
}
