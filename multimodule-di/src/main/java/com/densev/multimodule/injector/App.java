package com.densev.multimodule.injector;

/**
 * Created by Dzianis_Sevastseyenk on 05/29/2017.
 */
public class App {

    @Wired
    public static WireableTest test;

    static {
        Injector.INSTANCE.wire(null, App.class);
    }

    public static void main(String... args) {

        test.test();

        new OtherTestClass().testing();
    }
}
