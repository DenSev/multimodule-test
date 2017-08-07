package com.densev.multimodule.aop;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by Dzianis_Sevastseyenk on 08/07/2017.
 */
@Entity
@Table(name = "testEntity")
public class TestEntity {

    private String name;
    @Id
    private Integer id;


    public TestEntity(String name, Integer id) {
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
