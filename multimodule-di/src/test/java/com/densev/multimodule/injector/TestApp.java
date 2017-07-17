package com.densev.multimodule.injector;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by Dzianis_Sevastseyenk on 05/29/2017.
 */
@Singleton
public class TestApp {

    private static final Logger LOG = LoggerFactory.getLogger(TestApp.class);

    @Inject
    static WireableTest test;

    public String run(String testString) {
        return test.test(testString);
    }
}
