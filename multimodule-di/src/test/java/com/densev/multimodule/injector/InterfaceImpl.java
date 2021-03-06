package com.densev.multimodule.injector;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Singleton;

/**
 * Created by Dzianis_Sevastseyenk on 05/30/2017.
 */
@Singleton
public class InterfaceImpl implements InterfaceTest {

    private static final Logger LOG = LoggerFactory.getLogger(InterfaceImpl.class);

    @Override
    public void doSomething() {

        LOG.info("doing something");
    }
}
