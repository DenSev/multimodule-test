package com.densev.multimodule.concurrency;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Dzianis_Sevastseyenk on 01/24/2017.
 */
public class ConcurrencyApp {

    static Logger logger = LoggerFactory.getLogger(ConcurrencyApp.class);


    private static volatile int MY_INT = 0;

    public static void main(String[] args) {
        new Thread(new ChangeListener()).start();
        new Thread(new ChangeMaker()).start();
    }

    static class ChangeListener implements Runnable {
        @Override
        public void run() {
            int local_value = MY_INT;
            while ( local_value < 5){
                if( local_value!= MY_INT){
                    logger.info("Got Change for MY_INT : {}", MY_INT);
                    local_value= MY_INT;
                }
            }
        }
    }

    static class ChangeMaker implements Runnable{
        @Override
        public void run() {

            int local_value = MY_INT;
            while (MY_INT <5){
                logger.info("Incrementing MY_INT to {}", local_value+1);
                MY_INT = ++local_value;
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) { e.printStackTrace(); }
            }
        }
    }
}
