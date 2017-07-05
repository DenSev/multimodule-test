package com.densev.multimodule.injector;

import com.google.common.base.Stopwatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;
import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by Dzianis_Sevastseyenk on 05/29/2017.
 */
@Singleton
public class App {

    private static final Logger LOG = LoggerFactory.getLogger(App.class);
    @Inject
    private static WireableTest test;

    @Inject
    private OtherTestClass otherTestClass;

    public static void main(String... args) {
        Stopwatch stopwatch = Stopwatch.createStarted();
        App app = Injector.INSTANCE.getBean(App.class);
        app.run();
        LOG.info("Elapsed: {} ", stopwatch.elapsed(TimeUnit.MICROSECONDS));
    }

    public void run() {
        test.test();
        otherTestClass.testing();
    }
}
