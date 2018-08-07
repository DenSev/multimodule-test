package com.densev.metrics.app;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import({AppConfig.class})
public class MetricsDemoApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        new MetricsDemoApplication()
            .configure(new SpringApplicationBuilder(MetricsDemoApplication.class))
            .run(args);
    }
}
