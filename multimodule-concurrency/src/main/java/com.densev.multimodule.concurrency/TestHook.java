package com.densev.multimodule.concurrency;

/**
 * Created by Dzianis_Sevastseyenk on 04/10/2017.
 */
public class TestHook {
    public static void main(String[] args) {

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {

        }));


        System.out.println("Application Terminating ...");
        Runtime.getRuntime().halt(0);
    }

    public void a(){

    }
}