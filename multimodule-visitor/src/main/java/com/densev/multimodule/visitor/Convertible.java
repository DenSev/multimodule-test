package com.densev.multimodule.visitor;

/**
 * Created by Dzianis_Sevastseyenk on 01/05/2017.
 */
public class Convertible implements Visitable{

    private Integer number;

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }


    public String accept(Visitor visitor) {
        return visitor.visitConvertible(this);
    }


}
