package com.densev.multimodule.visitor;

/**
 * Created by Dzianis_Sevastseyenk on 01/05/2017.
 */
public interface Visitor {

    String visitConvertible(ConvertibleWrapper visitable);
}
