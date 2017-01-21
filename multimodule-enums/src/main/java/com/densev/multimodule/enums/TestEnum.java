package com.densev.multimodule.enums;

/**
 * Created by Dzianis_Sevastseyenk on 10/26/2016.
 */
public enum TestEnum {

    HI {
        @Override
        public void say() {
            System.out.println("HI");
        }
    },
    BYE {
        @Override
        public void say() {
            System.out.println("BYE");
        }
    };

    public abstract void say();
}