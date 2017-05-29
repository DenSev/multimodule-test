package com.densev.multimodule.injector;

import com.densev.multimodule.injector.annotation.Wireable;

/**
 * Created by Dzianis_Sevastseyenk on 05/29/2017.
 */
@Wireable
public class WireableTest {

    public void test() {
        System.out.println("testing wiring");
    }
}
