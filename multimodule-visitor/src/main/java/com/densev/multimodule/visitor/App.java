package com.densev.multimodule.visitor;

import com.google.common.base.Stopwatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

/**
 * Created by Dzianis_Sevastseyenk on 01/05/2017.
 */
public class App {

    public static final Logger logger = LoggerFactory.getLogger(App.class);

    public static void main(String... args) {

        Stopwatch stopwatch = Stopwatch.createStarted();

        Converter converter = new Converter();
        Convertible convertible = new Convertible();
        convertible.setNumber(1);

        converter.convert(convertible);

        logger.info("elapsed {} {}",stopwatch.elapsed(TimeUnit.MICROSECONDS), TimeUnit.MICROSECONDS.toString());
    }
}
