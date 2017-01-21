package com.densev.multimodule.visitor;

/**
 * Created by Dzianis_Sevastseyenk on 01/05/2017.
 */
public class App {

    public static void main(String... args) {

        Converter c = new Converter();


        Convertible cc = new Convertible();
        cc.setNumber(1);

        assert c.convert(new ConvertibleWrapper(cc)).equals("3");
    }
}
