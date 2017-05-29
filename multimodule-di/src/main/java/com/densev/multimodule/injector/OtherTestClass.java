package com.densev.multimodule.injector;

import com.densev.multimodule.injector.annotation.Wireable;
import com.densev.multimodule.injector.annotation.Wired;

/**
 * Created by Dzianis_Sevastseyenk on 05/29/2017.
 */
@Wireable
public class OtherTestClass {

    @Wired
    private WireableTest test;

    public void testing() {
        System.out.println("a new challenger appears");
        test.test();
    }
}
