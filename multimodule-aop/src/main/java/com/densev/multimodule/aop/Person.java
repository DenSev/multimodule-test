package com.densev.multimodule.aop;

import org.springframework.stereotype.Component;

/**
 * Created by Dzianis_Sevastseyenk on 03.16.2018.
 */
public class Person {

    private String name;
    private PersonType type;
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public PersonType getType() {
        return type;
    }
    public void setType(PersonType type) {
        this.type = type;
    }
}
