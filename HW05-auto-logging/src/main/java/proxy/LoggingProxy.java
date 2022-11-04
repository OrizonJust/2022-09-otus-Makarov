package proxy;

import annotation.Log;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.Arrays;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class LoggingProxy {

    public static TestLoggingInterface createClass(TestLoggingInterface instance) {
        InvocationHandler handler = new DemoInvocationHandler(instance);
        return (TestLoggingInterface) Proxy.newProxyInstance(LoggingProxy.class.getClassLoader(),
                new Class<?>[]{TestLoggingInterface.class}, handler);
    }

    @AllArgsConstructor
    public static class DemoInvocationHandler implements InvocationHandler {

        private final TestLoggingInterface myClass;
        private final ArrayList<Method> annotatedMethods = new ArrayList<>();

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            if (annotatedMethods.isEmpty()) {
                getAnnotatedMethods();
            }
            if (annotatedMethods.stream().anyMatch(m -> m.getName().equals(method.getName())
                    && Arrays.equals(m.getParameterTypes(), method.getParameterTypes()))) {
                System.out.println("Executed method: " + method.getName() + ", Params: " + Arrays.toString(args));
            }

            return method.invoke(myClass, args);
        }

        private void getAnnotatedMethods() {
            var declaredMethods = myClass.getClass().getDeclaredMethods();
            for (var method : declaredMethods) {
                if (method.isAnnotationPresent(Log.class)) {
                    annotatedMethods.add(method);
                }
            }
        }
    }
}
