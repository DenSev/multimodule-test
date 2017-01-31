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

    static Integer i = 0;

    public static void main(String... args) {

        List<String> ass = new ArrayList<>();
        ass.add("sss");
        ass.add(null);
        ass.add(null);

       ExecutorService service = Executors.newFixedThreadPool(4);
        service.submit(() -> {
            for (int j =0; j <3; j++){
                ass.add(i.toString());
                logger.info("old val: {}",i );
                i++;
                logger.info("thread 1:{} : {}", ass.toString(), i);
            }

        });

        service.submit(() -> {
            for (int j = 0; j <3; j++){
                ass.add(i.toString());
                logger.info("old val: {}",i );
                i++;
                logger.info("thread 2:{} : {}", ass.toString(), i);
            }
        });
    }


}
