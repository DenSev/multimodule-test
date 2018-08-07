package com.densev.metrics.app;

import com.densev.metrics.repository.elastic.ClientProvider;
import com.densev.metrics.repository.elastic.ConnectionFactory;
import com.densev.metrics.repository.elastic.ElasticsearchRepository;
import com.densev.metrics.services.MapperProvider;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.PropertyOverrideConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.core.io.ClassPathResource;

@Configuration
@EnableAspectJAutoProxy
@ComponentScan("com.densev.metrics")
public class AppConfig {

    @Bean
    public ObjectMapper getMapper(@Autowired MapperProvider mapperProvider) {
        return mapperProvider.getMapper();
    }

    @Bean
    @Qualifier("localProvider")
    public ClientProvider getLocalProvider() {
        return new ConnectionFactory("localhost", 11200);
    }

    @Bean
    @Qualifier("dockerProvider")
    public ClientProvider getDockerProvider() {
        return new ConnectionFactory("localhost", 12200);
    }

    @Bean
    @Qualifier("localElasticRepository")
    public ElasticsearchRepository getLocalRepository(@Autowired ObjectMapper mapper,
                                                      @Autowired @Qualifier("localProvider") ClientProvider localProvider) {
        return new ElasticsearchRepository(localProvider.getClient(), mapper, localProvider.getAddress(), localProvider.getPort());
    }

    @Bean
    @Qualifier("dockerElasticRepository")
    public ElasticsearchRepository getDockerRepository(@Autowired ObjectMapper mapper,
                                                      @Autowired @Qualifier("dockerProvider") ClientProvider dockerProvider) {
        return new ElasticsearchRepository(dockerProvider.getClient(), mapper, dockerProvider.getAddress(), dockerProvider.getPort());
    }


    @Bean
    public static PropertyOverrideConfigurer propertyOverrideConfigurer() {
        PropertyOverrideConfigurer propertyOverrideConfigurer = new PropertyOverrideConfigurer();
        propertyOverrideConfigurer.setLocation(new ClassPathResource("application.properties"));
        propertyOverrideConfigurer.setIgnoreResourceNotFound(true);
        propertyOverrideConfigurer.setIgnoreInvalidKeys(true);
        return propertyOverrideConfigurer;
    }
}
