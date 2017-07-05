package com.densev.multimodule.injector;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by Dzianis_Sevastseyenk on 05/29/2017.
 */
@Singleton
public class OtherTestClass {

    private static final Logger LOG = LoggerFactory.getLogger(OtherTestClass.class);
    @Inject
    private WireableTest test;

    public void testing() {
        LOG.info("a new challenger appears");
        test.test();
    }

    public void testOther() {
        LOG.info("dependency present? {}", test != null);
        LOG.info("other dependency test");
    }
}
