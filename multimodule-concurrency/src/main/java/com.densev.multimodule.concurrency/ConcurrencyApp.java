package com.densev.multimodule.concurrency;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.*;
import java.util.stream.IntStream;

/**
 * Created by Dzianis_Sevastseyenk on 01/24/2017.
 */
public class ConcurrencyApp {

    private static final Logger logger = LoggerFactory.getLogger(ConcurrencyApp.class);


    //SYCNHRONIZED
    //VOLATILE
    //EXECUTOR SERVICE, EXECUTORS, TYPES OF EXECUTORS
    //CALLABLE
    //FUTURE
    //TODO: LOCK

    private static volatile int MY_INT = 0;

    public void incrementSync() {
        MY_INT++;
    }

    public void func() throws InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(2);

        executor.submit(() -> System.out.println("asdasd"));

        IntStream.range(0, 10000)
                .forEach(i -> executor.submit(this::incrementSync));

        //executor.shutdown();

        executor.awaitTermination(1, TimeUnit.SECONDS);
        logger.info("result: {}", MY_INT);
    }

    public static void main(String[] args) throws InterruptedException, ExecutionException {

        ConcurrencyApp app
                = new ConcurrencyApp();
        app.func();

        /*ThreadFactory threadFactory = new ThreadFactoryBuilder()
                .setDaemon(true)
                .setNameFormat("my factory")
                .build();



        ExecutorService executorService = Executors.newFixedThreadPool(2, threadFactory);
        Future<String> changeListener = executorService.submit(new ChangeListener());
        Future<String> changeMaker = executorService.submit(new ChangeMaker());

        logger.info("changeMaker returned results: {}", changeMaker.get());
        logger.info("changeListener returned results: {}", changeListener.get());*/

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
