package com.densev.multimodule.enums;

/**
 * Created by Dzianis_Sevastseyenk on 11/16/2016.
 */
public class TypeWorker {

    public static <T extends TypeParent> void work(T param){
        System.out.println(param.getName());
    }
}
