package com.densev.multimodule.injector;

import com.google.common.base.Stopwatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;

import javax.inject.Named;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.concurrent.TimeUnit;

public class ProxyTest {

    interface TestInterface {
        Object get();
    }

    class TestImpl implements TestInterface {
        @Override
        public Object get() {
            return "asd";
        }
    }

    public static class DynamicInvocationHandler implements InvocationHandler {

        private static final Logger LOG = LoggerFactory.getLogger(DynamicInvocationHandler.class);
        private Object target;

        public DynamicInvocationHandler(Object target) {
            this.target = target;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args)
            throws Throwable {

            Stopwatch stopwatch = Stopwatch.createStarted();

            Object result = method.invoke(target, args);
            LOG.debug("Invoked method: {}, time elapsed: {}", method.getName(), stopwatch.stop().elapsed(TimeUnit.NANOSECONDS));
            return result;
        }
    }

    @Test
    public void test() {
        TestInterface impl = new TestImpl();
        TestInterface testProxy = (TestInterface) Proxy.newProxyInstance(this.getClass().getClassLoader(),
            new Class[]{TestInterface.class},
            new DynamicInvocationHandler(impl));

        for (int i = 0; i < 10; i++) {
            Stopwatch stopwatch = Stopwatch.createStarted();
            System.out.println(testProxy.get());
            System.out.println(stopwatch.stop().elapsed(TimeUnit.NANOSECONDS));
        }

    }


}
