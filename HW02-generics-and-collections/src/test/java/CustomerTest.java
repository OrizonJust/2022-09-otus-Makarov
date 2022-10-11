import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class CustomerTest {

    @Test
    @DisplayName("Проверяем, что класс Customer не сломан.")
    void setterCustomerTest() {
        final var expectedName = "updatedName";
        final var name = "nameVas";
        final var customer = new Customer(1, name, 2);

        customer.setName(expectedName);

        assertThat(customer.getName()).isEqualTo(expectedName);
    }

    @Test
    @DisplayName("Объект Customer как ключ в карте.")
    void customerAsKeyTest() {
        final var customerId = 1L;
        final var customer = new Customer(customerId, "Max", 133);
        Map<Customer, String> map = new HashMap<>();

        String expectedData = "data";
        map.put(customer, expectedData);

        final var newScore = customer.getScores() + 10;
        final var factData = map.get(new Customer(customerId, "MaxChangedName", newScore));

        assertThat(factData).isEqualTo(expectedData);

        final var newScoreSecond = customer.getScores() + 20;
        customer.setScores(newScoreSecond);
        final var factDataSecond = map.get(customer);

        assertThat(factDataSecond).isEqualTo(expectedData);
    }

    @Test
    @DisplayName("Возвращение в обратном порядке.")
    void reverseOrderTest() {
        final var customer1 = new Customer(1, "Max", 133);
        final var customer2 = new Customer(2, "Anton", 199);
        final var customer3 = new Customer(3, "Alice", 155);

        final var customerReverseOrder = new CustomerReverseOrder();
        customerReverseOrder.add(customer1);
        customerReverseOrder.add(customer2);
        customerReverseOrder.add(customer3);

        final var customerLast = customerReverseOrder.take();

        assertThat(customerLast).usingRecursiveComparison().isEqualTo(customer3);

        final var customerMiddle = customerReverseOrder.take();

        assertThat(customerMiddle).usingRecursiveComparison().isEqualTo(customer2);

        final var customerFirst = customerReverseOrder.take();

        assertThat(customerFirst).usingRecursiveComparison().isEqualTo(customer1);
    }

    @Test
    @DisplayName("Сортировка по полю score, итерация по возрастанию")
    void scoreSortingTest() {
        Customer customer1 = new Customer(1, "Max", 133);
        Customer customer2 = new Customer(2, "Anton", 199);
        Customer customer3 = new Customer(3, "Alice", 155);

        CustomerService customerService = new CustomerService();
        customerService.add(customer1, "Data1");
        customerService.add(customer2, "Data2");
        customerService.add(customer3, "Data3");


        Map.Entry<Customer, String> smallestScore = customerService.getSmallest();

        assertThat(smallestScore.getKey()).isEqualTo(customer1);

        Map.Entry<Customer, String> middleScore = customerService.getNext(new Customer(10, "Key", 135));

        assertThat(middleScore.getKey()).isEqualTo(customer3);
        middleScore.getKey().setScores(10000);
        middleScore.getKey().setName("Ivan");

        Map.Entry<Customer, String> biggestScore = customerService.getNext(customer3);
        assertThat(biggestScore.getKey()).isEqualTo(customer2);

        Map.Entry<Customer, String> notExists = customerService.getNext(new Customer(100, "Not exists", 20000));

        assertThat(notExists).isNull();

    }

    @Test
    @DisplayName("Модификация коллекции")
    void mutationTest() {
        Customer customer1 = new Customer(1, "Anton", 199);
        Customer customer2 = new Customer(2, "Max", 133);
        Customer customer3 = new Customer(3, "Alice", 155);

        CustomerService customerService = new CustomerService();
        customerService.add(customer1, "Data1");
        customerService.add(new Customer(customer2.getId(), customer2.getName(), customer2.getScores()), "Data2");
        customerService.add(customer3, "Data3");

        Map.Entry<Customer, String> smallestScore = customerService.getSmallest();
        smallestScore.getKey().setName("Ivan");

        assertThat(customerService.getSmallest().getKey().getName()).isEqualTo(customer2.getName());
    }
}
