package com.densev.multimodule.injector;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.densev.multimodule.injector.annotation.Wireable;
import com.densev.multimodule.injector.annotation.Wired;

/**
 * Created by Dzianis_Sevastseyenk on 05/29/2017.
 */
@Wireable("test")
public class WireableTest {
    private static final Logger LOG = LoggerFactory.getLogger(WireableTest.class);
    private OtherTestClass circularDependencyTest;
    private InterfaceTest interfaceTest;

    @Wired
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
