import org.junit.jupiter.api.Test;
import ru.laverno.ATM;
import ru.laverno.Banknotes;
import ru.laverno.MoneyType;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static ru.laverno.MoneyType.*;

public class ATMTest {

    @Test
    public void addPackOfMoneyTest() {
        var packOfMoney = new HashMap<MoneyType, Integer>();
        packOfMoney.put(FIVE_THOUSANDS, 10);
        packOfMoney.put(ONE_THOUSAND, 3);

        var atm = new ATM();
        atm.addPackOfMoney(packOfMoney);

        var banknotesInATM = atm.getBanknotes();

        assertEquals(packOfMoney.get(FIVE_THOUSANDS), banknotesInATM.getCountOfBanknotes(FIVE_THOUSANDS));
        assertEquals(packOfMoney.get(ONE_THOUSAND), banknotesInATM.getCountOfBanknotes(ONE_THOUSAND));
    }

    @Test
    public void getPackOfMoney() {
        var atm = new ATM();
        var packOfMoney = Banknotes.newInstancePackOfMoney();
        packOfMoney.put(FIVE_THOUSANDS, 10);
        packOfMoney.put(ONE_THOUSAND, 3);
        packOfMoney.put(MoneyType.ONE_HUNDRED, 4);
        atm.addPackOfMoney(packOfMoney);
        var map = atm.getPackOfMoney(13200);

        assertEquals(2, map.get(FIVE_THOUSANDS));
        assertEquals(3, map.get(ONE_THOUSAND));
        assertEquals(2, map.get(ONE_HUNDRED));
    }

    @Test
    public void atmCantDispenseMoney() {
        var atm = new ATM();
        var packOfMoney = Banknotes.newInstancePackOfMoney();
        packOfMoney.put(FIVE_THOUSANDS, 10);
        packOfMoney.put(ONE_THOUSAND, 3);
        packOfMoney.put(MoneyType.ONE_HUNDRED, 4);
        atm.addPackOfMoney(packOfMoney);

        assertThrows(IllegalArgumentException.class, () -> atm.getPackOfMoney(13123));
    }

    @Test
    public void notEnoughMoney() {
        var atm = new ATM();
        var packOfMoney = Banknotes.newInstancePackOfMoney();
        atm.addPackOfMoney(packOfMoney);

        assertThrows(IllegalArgumentException.class, () -> atm.getPackOfMoney(1000));
    }

    @Test
    public void printRestOfMoney() {
        var atm = new ATM();
        var packOfMoney = Banknotes.newInstancePackOfMoney();
        packOfMoney.put(FIVE_THOUSANDS, 10);
        packOfMoney.put(ONE_THOUSAND, 3);
        packOfMoney.put(MoneyType.ONE_HUNDRED, 4);
        atm.addPackOfMoney(packOfMoney);
        atm.getPackOfMoney(13200);

        atm.printRestOfMoney();

        assertEquals(8, atm.getBanknotes().getCountOfBanknotes(FIVE_THOUSANDS));
        assertEquals(2, atm.getBanknotes().getCountOfBanknotes(ONE_HUNDRED));
    }
}
