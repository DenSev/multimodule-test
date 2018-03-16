package com.densev.multimodule.aop;

/**
 * Created by Dzianis_Sevastseyenk on 03.16.2018.
 */
public class PersonType {
    private String typeName;
    public PersonType(String typeName){
        this.typeName = typeName;
    }
    public PersonType(){
    }
    public String getTypeName() {
        return typeName;
    }
    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }
}
