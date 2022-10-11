import java.util.AbstractMap;
import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

public class CustomerService {

    private final TreeMap<Customer, String> customerTreeMap;

    public CustomerService() {
        this.customerTreeMap = new TreeMap<>(Comparator.comparing(Customer::getScores));
    }

    public Map.Entry<Customer, String> getSmallest() {
        return createNewCustomerEntry(customerTreeMap.firstEntry());
    }

    public Map.Entry<Customer, String> getNext(Customer customer) {
        return createNewCustomerEntry(customerTreeMap.higherEntry(customer));
    }

    public void add(Customer customer, String data) {
        customerTreeMap.put(customer, data);
    }

    private Map.Entry<Customer, String> createNewCustomerEntry(Map.Entry<Customer, String> customer) {
        if (customer == null) return null;
        final var keyParams = customer.getKey();
        return new AbstractMap.SimpleEntry<>(new Customer(keyParams.getId(), keyParams.getName(), keyParams.getScores()), customer.getValue());
    }
}
