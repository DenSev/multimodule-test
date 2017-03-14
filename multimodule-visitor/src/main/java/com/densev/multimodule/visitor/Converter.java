package com.densev.multimodule.visitor;

/**
 * Created by Dzianis_Sevastseyenk on 01/05/2017.
 */
public class Converter implements Visitor {

    public String visitConvertible(Convertible visitable) {
        return visitable.getNumber().toString();
    }

    public String convert(Visitable visitable) {
        return visitable.accept(this);
    }

    public String bruteConvert(Visitable visitable) {
        if (visitable.getClass().equals(Convertible.class)) {
            return visitConvertible((Convertible) visitable);
        }
        return null;
    }


}
