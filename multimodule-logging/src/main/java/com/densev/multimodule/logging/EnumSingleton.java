package com.densev.multimodule.logging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Dzianis_Sevastseyenk on 01/17/2017.
 */
public enum EnumSingleton {

    THIS;

    private final static Logger logger = LoggerFactory.getLogger(EnumSingleton.class);

    public void test(){
        logger.info("text enum {}", 1);
        logger.warn("text enum {}", 2);
        logger.debug("text enum {}", 3);
        logger.error("text enum {}", 4);
        logger.trace("text enum {}", 5);

        throw new RuntimeException("something something test exception");
    }
}
