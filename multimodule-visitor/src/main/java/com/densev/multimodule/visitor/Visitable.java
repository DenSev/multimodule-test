package com.densev.multimodule.visitor;

/**
 * Created by Dzianis_Sevastseyenk on 01/05/2017.
 */
public interface Visitable {

    String accept(Visitor visitor);
}
