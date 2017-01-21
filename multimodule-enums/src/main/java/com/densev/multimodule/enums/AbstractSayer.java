package com.densev.multimodule.enums;

/**
 * Created by Dzianis_Sevastseyenk on 11/23/2016.
 */
public abstract class AbstractSayer <T extends AbstractSayer<T>>{

    public abstract void say();
}
