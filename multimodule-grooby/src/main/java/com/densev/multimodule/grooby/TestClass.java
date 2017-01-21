package com.densev.multimodule.grooby;

/**
 * Created by Dzianis_Sevastseyenk on 12/12/2016.
 */
public class TestClass {

    private String a;
    private String b;

    public String getA() {
        return a;
    }

    public void setA(String a) {
        this.a = a;
    }

    public String getB() {
        return b;
    }

    public void setB(String b) {
        this.b = b;
    }

    @Override
    public String toString() {
        return "TestClass{" +
            "a='" + a + '\'' +
            ", b='" + b + '\'' +
            '}';
    }
}
