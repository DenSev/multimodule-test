package com.densev.multimodule.visitor;

/**
 * Created by Dzianis_Sevastseyenk on 01/05/2017.
 */
public class Converter implements Visitor {

    public String visitConvertible(ConvertibleWrapper visitable) {
        System.out.println("1234");
        return visitable.convertible.getNumber().toString();
    }

    public String convert(Visitable visitable){
        return visitable.accept(this);
    }


}
