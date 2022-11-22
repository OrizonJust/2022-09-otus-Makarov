import org.junit.jupiter.api.Test;
import ru.laverno.ATM;
import ru.laverno.Banknotes;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static ru.laverno.MoneyType.*;

public class ATMTest {

    @Test
    public void addPackOfMoneyTest() {
        var atm = new ATM(0,0,0,0,0,0,0);
        var packOfMoney = Banknotes.newInstancePackOfMoney(0, 0,0,0,3,0,10);
        atm.addMoney(packOfMoney);

        var banknotesInATM = atm.getBanknotes();

        assertEquals(packOfMoney.get(FIVE_THOUSANDS), banknotesInATM.getCountOfBanknotes(FIVE_THOUSANDS));
        assertEquals(packOfMoney.get(ONE_THOUSAND), banknotesInATM.getCountOfBanknotes(ONE_THOUSAND));
    }

    @Test
    public void getPackOfMoney() {
        var atm = new ATM();
        var packOfMoney = Banknotes.newInstancePackOfMoney(0, 4,0,0,3,0,10);
        atm.addMoney(packOfMoney);
        var map = atm.getMoney(13200);

        assertEquals(2, map.get(FIVE_THOUSANDS));
        assertEquals(3, map.get(ONE_THOUSAND));
        assertEquals(2, map.get(ONE_HUNDRED));
    }

    @Test
    public void atmCantDispenseMoney() {
        var atm = new ATM();
        var packOfMoney = Banknotes.newInstancePackOfMoney(0, 4,0,0,3,0,10);
        atm.addMoney(packOfMoney);

        assertThrows(IllegalArgumentException.class, () -> atm.getMoney(13123));
    }

    @Test
    public void notEnoughMoney() {
        var atm = new ATM();
        var packOfMoney = Banknotes.newInstancePackOfMoney(0,0,0,0,0,0,0);
        atm.addMoney(packOfMoney);

        assertThrows(IllegalArgumentException.class, () -> atm.getMoney(1000));
    }

    @Test
    public void printRestOfMoney() {
        var atm = new ATM();
        var packOfMoney = Banknotes.newInstancePackOfMoney(0, 4,0,0,3,0,10);
        atm.addMoney(packOfMoney);
        atm.getMoney(13200);

        atm.printInfo();

        assertEquals(8, atm.getBanknotes().getCountOfBanknotes(FIVE_THOUSANDS));
        assertEquals(2, atm.getBanknotes().getCountOfBanknotes(ONE_HUNDRED));
    }
}
