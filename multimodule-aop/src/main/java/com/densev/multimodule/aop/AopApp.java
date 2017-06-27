package com.densev.multimodule.aop;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

/**
 * Created by Dzianis_Sevastseyenk on 06/27/2017.
 */
@Component
public class AopApp {

    private static final Logger LOG = LoggerFactory.getLogger(AopApp.class);

    @Autowired
    LogContainer logContainer;
    @Autowired
    private RepositoryTest repositoryTest;

    public static void main(String... args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("application-context.xml");
        AopApp application = context.getBean(AopApp.class);
        application.run();
    }


    public void run() {
        LOG.info(repositoryTest.search("something something something test"));
        logContainer.log();
    }
}
