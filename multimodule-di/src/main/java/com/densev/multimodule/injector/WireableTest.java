package com.densev.multimodule.injector;

import com.densev.multimodule.injector.annotation.Wireable;
import com.densev.multimodule.injector.annotation.Wired;

/**
 * Created by Dzianis_Sevastseyenk on 05/29/2017.
 */
@Wireable("test")
public class WireableTest {

    @Wired
    private OtherTestClass circularDependencyTest;

    public void test() {
        System.out.println("testing wiring");
    }
}
