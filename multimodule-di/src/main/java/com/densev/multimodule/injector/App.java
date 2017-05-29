package com.densev.multimodule.injector;

import com.densev.multimodule.injector.annotation.Wireable;
import com.densev.multimodule.injector.annotation.Wired;

/**
 * Created by Dzianis_Sevastseyenk on 05/29/2017.
 */
@Wireable
public class App {

    @Wired
    private static WireableTest test;

    @Wired
    private OtherTestClass otherTestClass;

    public static void main(String... args) {

        App app = Injector.INSTANCE.getBean(App.class);
        app.run();

    }

    public void run(){
        test.test();
        otherTestClass.testing();
    }
}
