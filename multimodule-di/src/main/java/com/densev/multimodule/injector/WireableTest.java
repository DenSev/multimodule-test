package com.densev.multimodule.injector;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

/**
 * Created by Dzianis_Sevastseyenk on 05/29/2017.
 */
@Singleton
@Named("test")
public class WireableTest {
    private static final Logger LOG = LoggerFactory.getLogger(WireableTest.class);
    private OtherTestClass circularDependencyTest;
    private InterfaceTest interfaceTest;

    @Inject
    public WireableTest(OtherTestClass circularDependencyTest, InterfaceTest interfaceTest) {
        this.circularDependencyTest = circularDependencyTest;
        this.interfaceTest = interfaceTest;
    }

    public void test() {
        LOG.info("Class: {}", circularDependencyTest);
        circularDependencyTest.testOther();
        LOG.info("testing wiring");
        interfaceTest.doSomething();
    }
}
