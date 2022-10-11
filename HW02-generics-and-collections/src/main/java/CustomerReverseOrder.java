import java.util.LinkedList;

public class CustomerReverseOrder {

    private final LinkedList<Customer> customerQueue;

    public CustomerReverseOrder() {
        this.customerQueue = new LinkedList<>();
    }

    public void add(Customer customer) {
        customerQueue.add(customer);
    }

    public Customer take() {
        return customerQueue.pollLast();
    }
}
