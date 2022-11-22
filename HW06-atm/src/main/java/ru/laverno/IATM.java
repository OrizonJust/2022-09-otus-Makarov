package ru.laverno;

import java.util.Map;

public interface IATM {

    void addMoney(Map<MoneyType, Integer> money);

    Map<MoneyType, Integer> getMoney(int money);

    void printInfo();
}
