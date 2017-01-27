package com.densev.multimodule.concurrency;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dzianis_Sevastseyenk on 01/24/2017.
 */
public class ConcurrencyApp {

    static Logger logger = LoggerFactory.getLogger(ConcurrencyApp.class);

    private static Integer number;

    public static void main(String... args) {

        List<String> ass = new ArrayList<>();
        ass.add("sss");
        ass.add(null);
        ass.add(null);
        for (String s : ass) {
            logger.error(s);
        }

       /* ExecutorService service = Executors.newFixedThreadPool(4);
        service.submit(() -> {
            for(int i= 0; i< 4;i ++) {

                number = 1;
                synchPrinter(number);
            }

        });*/

    }

    public static void synchPrinter(Object o) {
        synchronized (o) {
            logger.info(o.toString());
        }

    }


}
