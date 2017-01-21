package com.densev.multimodule.grooby;

import groovy.lang.Binding;
import groovy.lang.GroovyShell;

/**
 * Created by Dzianis_Sevastseyenk on 12/12/2016.
 */
public class Main {

    public static void main(String[] agrs){

        TestClass tt = new TestClass();
        tt.setA("asd");
        tt.setB("123");


        String script = "tt.setA('new test value')";
        Binding binding = new Binding();

        binding.setProperty("tt", tt);
        GroovyShell shell = new GroovyShell(binding);
        Object result = shell.evaluate(script);


        System.out.println(tt);
    }
}
