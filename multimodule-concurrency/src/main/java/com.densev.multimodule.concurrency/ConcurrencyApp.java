package com.densev.multimodule.concurrency;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadFactory;

/**
 * Created by Dzianis_Sevastseyenk on 01/24/2017.
 */
public class ConcurrencyApp {

    static Logger logger = LoggerFactory.getLogger(ConcurrencyApp.class);


    //SYCNHRONIZED
    //VOLATILE
    //TODO: EXECUTOR SERVICE, EXECUTORS, TYPES OF EXECUTORS
    //TODO: CALLABLE
    //TODO: FUTURE
    //TODO: LOCK

    private static volatile int MY_INT = 0;

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        ThreadFactory threadFactory = new ThreadFactoryBuilder()
            .setDaemon(true)
            .setNameFormat("my factory")
            .build();

        ExecutorService executorService = Executors.newFixedThreadPool(2, threadFactory);
        Future<String> changeListener = executorService.submit(new ChangeListener());
        Future<String> changeMaker = executorService.submit(new ChangeMaker());

        logger.info("changeMaker returned results: {}", changeMaker.get());
        logger.info("changeListener returned results: {}", changeListener.get());

    }

    static class ChangeListener implements Callable<String> {
        @Override
        public String call() {
            int local_value = MY_INT;
            while (local_value < 5) {
                if (local_value != MY_INT) {
                    logger.info("Got Change for MY_INT : {}", MY_INT);
                    local_value = MY_INT;
                }
            }
            return "done";
        }
    }

    static class ChangeMaker implements Callable<String> {
        @Override
        public String call() {

            int local_value = MY_INT;
            while (MY_INT < 5) {
                logger.info("Incrementing MY_INT to {}", local_value + 1);
                MY_INT = ++local_value;
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    logger.error("caught InterruptedException: ", e);
                    return "exception";
                }
            }
            return "done";
        }
    }
}
