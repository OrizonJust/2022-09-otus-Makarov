import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class LoggingProxy {

    public static TestLoggingInterface createClass() {
        InvocationHandler handler = new DemoInvocationHandler(new TestLogging());
        return (TestLoggingInterface) Proxy.newProxyInstance(LoggingProxy.class.getClassLoader(),
                new Class<?>[]{TestLoggingInterface.class}, handler);
    }

    @AllArgsConstructor
    public static class DemoInvocationHandler implements InvocationHandler {

        private final TestLoggingInterface myClass;

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            for (Method m : myClass.getClass().getDeclaredMethods()) {
                if (m.isAnnotationPresent(Log.class) &&
                        m.getName().equals(method.getName()) &&
                        Arrays.equals(m.getParameterTypes(), method.getParameterTypes()))
                    System.out.println("Executed method: " + method.getName() + ", Params: " + Arrays.toString(args));
            }

            return method.invoke(myClass, args);
        }
    }
}
