package com.densev.metrics.app;

import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.stereotype.Component;

@Component
public class JerseyConfig extends ResourceConfig {

    public JerseyConfig() {
        registerContentRoot();
    }

    private void registerContentRoot() {
        packages("com.densev.metrics.rest");
    }

}
