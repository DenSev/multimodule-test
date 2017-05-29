package com.densev.multimodule.injector;

import com.densev.multimodule.injector.annotation.Wired;

/**
 * Created by Dzianis_Sevastseyenk on 05/29/2017.
 */
public class OtherTestClass {

    @Wired
    private WireableTest test;

    OtherTestClass() {
        Injector.INSTANCE.wire(this, OtherTestClass.class);
    }

    public void testing() {
        System.out.println("a new challenger appears");
        test.test();
    }
}
