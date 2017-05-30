package com.densev.multimodule.injector;

import com.densev.multimodule.injector.annotation.Wireable;

/**
 * Created by Dzianis_Sevastseyenk on 05/30/2017.
 */
@Wireable
public class InterfaceImpl implements InterfaceTest {

    @Override
    public void doSomething() {
        System.out.println("doing something");
    }
}
