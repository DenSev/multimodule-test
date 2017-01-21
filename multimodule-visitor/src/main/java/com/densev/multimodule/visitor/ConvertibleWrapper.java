package com.densev.multimodule.visitor;

/**
 * Created by Dzianis_Sevastseyenk on 01/06/2017.
 */
public class ConvertibleWrapper implements Visitable {

    public Convertible convertible;

    public String accept(Visitor visitor) {
        return visitor.visitConvertible(this);
    }

    public ConvertibleWrapper(Convertible convertible) {
        this.convertible = convertible;
    }
}
