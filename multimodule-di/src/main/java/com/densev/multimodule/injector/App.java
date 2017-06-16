package com.densev.multimodule.injector;

import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.densev.multimodule.injector.annotation.Wireable;
import com.densev.multimodule.injector.annotation.Wired;
import com.google.common.base.Stopwatch;

/**
 * Created by Dzianis_Sevastseyenk on 05/29/2017.
 */
@Wireable
public class App {

    private static final Logger LOG = LoggerFactory.getLogger(App.class);
    @Wired
    private static WireableTest test;

    @Wired
    private OtherTestClass otherTestClass;

    public static void main(String... args) {
        Stopwatch stopwatch = Stopwatch.createStarted();
        App app = Injector.INSTANCE.getBean(App.class);
        app.run();
        LOG.info("Elapsed: {} ", stopwatch.elapsed(TimeUnit.MICROSECONDS));
    }

    public void run(){
        test.test();
        otherTestClass.testing();
    }
}
