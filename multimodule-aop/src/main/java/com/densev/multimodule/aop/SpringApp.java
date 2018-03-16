package com.densev.multimodule.aop;

import com.densev.multimodule.aop.spring.AppConfig;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Created by Dzianis_Sevastseyenk on 03.16.2018.
 */
public class SpringApp {

    public static void main(String... args){
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        Person person = (Person)context.getBean("person");
        PersonType ptype = person.getType();
        System.out.println(ptype.getTypeName());
    }
}
