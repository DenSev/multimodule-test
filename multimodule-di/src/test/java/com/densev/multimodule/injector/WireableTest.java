package com.densev.multimodule.injector;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Named;
import javax.inject.Singleton;

/**
 * Created by Dzianis_Sevastseyenk on 05/29/2017.
 */
@Singleton
@Named("test")
public class WireableTest {
    private static final Logger LOG = LoggerFactory.getLogger(WireableTest.class);

    public String test(String testString) {
        LOG.info("test called");
        return testString;
    }
}
