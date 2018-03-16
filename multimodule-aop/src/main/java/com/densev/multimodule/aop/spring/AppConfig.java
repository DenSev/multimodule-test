package com.densev.multimodule.aop.spring;

import com.densev.multimodule.aop.Person;
import com.densev.multimodule.aop.PersonType;
import com.google.common.collect.ImmutableMap;
import org.springframework.beans.factory.config.CustomEditorConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * Created by Dzianis_Sevastseyenk on 03.16.2018.
 */
@Configuration
@ComponentScan("com.densev.multimodule.aop")
@EnableAspectJAutoProxy
public class AppConfig {

    @Bean
    public static CustomEditorConfigurer customEditorConfigurer() {
        final CustomEditorConfigurer customEditorConfigurer = new CustomEditorConfigurer();
        customEditorConfigurer.setCustomEditors(ImmutableMap.of(PersonType.class, PersonTypeEditor.class));

        return customEditorConfigurer;
    }

    @Bean
    public Person person(){
        Person p = new Person();
        p.setName("Ram");
        p.setType(new PersonType("admin"));


        return p;
    }

}
