package com.densev.multimodule.injector;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.densev.multimodule.injector.annotation.Wireable;
import com.densev.multimodule.injector.annotation.Wired;

/**
 * Created by Dzianis_Sevastseyenk on 05/29/2017.
 */
@Wireable
public class OtherTestClass {

    private static final Logger LOG = LoggerFactory.getLogger(OtherTestClass.class);
    @Wired
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
