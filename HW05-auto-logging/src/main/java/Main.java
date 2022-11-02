public class Main {

    public static void main(String[] args) {
        TestLoggingInterface myClass = LoggingProxy.createClass();
        myClass.calculation(1);
        myClass.calculation(1, 2);
        myClass.calculation(1, 2, 3);
    }
}
