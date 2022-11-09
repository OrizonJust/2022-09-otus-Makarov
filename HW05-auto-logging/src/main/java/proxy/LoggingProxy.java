package proxy;

import annotation.Log;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import service.TestLoggingInterface;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

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
        private final Set<MethodInfo> methodInfos = new HashSet<>();

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            if (methodInfos.isEmpty()) {
                getAnnotatedMethods();
            }

            if (methodInfos.contains(new MethodInfo(method.getName(), method.getParameterTypes()))) {
                System.out.println("Executed method: " + method.getName() + ", Params: " + Arrays.toString(args));
            }

            return method.invoke(myClass, args);
        }

        private void getAnnotatedMethods() {
            var declaredMethods = myClass.getClass().getDeclaredMethods();
            for (var method : declaredMethods) {
                if (method.isAnnotationPresent(Log.class)) {
                    methodInfos.add(new MethodInfo(method.getName(), method.getParameterTypes()));
                }
            }
        }

        private record MethodInfo(String name, Class<?>[] methodParametersType) {

            @Override
            public boolean equals(Object o) {
                if (this == o) return true;
                if (o == null || getClass() != o.getClass()) return false;
                MethodInfo that = (MethodInfo) o;
                return Objects.equals(name, that.name) && Arrays.equals(methodParametersType, that.methodParametersType);
            }

            @Override
            public int hashCode() {
                int result = Objects.hash(name);
                result = 31 * result + Arrays.hashCode(methodParametersType);
                return result;
            }
        }
    }
}
