public class TestLogging implements TestLoggingInterface {

    @Log
    @Override
    public void calculation(int param) {
        System.out.println(param);
    }

    @Override
    public void calculation(int param1, int param2) {
        System.out.println(param1 + param2);
    }

    @Log
    @Override
    public void calculation(int param1, int param2, int param3) {
        System.out.println(param1 + param2 + param3);
    }


}
