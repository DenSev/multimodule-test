package com.densev.multimodule.enums;


import java.util.Random;

public class Main {
    public static final Random rand = new Random();

    public static void main(String[] args) throws IllegalAccessException, InstantiationException {

        TypeChild tc = new TypeChild("nnn");
        long t1 = System.nanoTime();
        //System.out.println(tc instanceof TypeChild);
        System.out.println(tc.getClass().equals(TypeChild.class));
        long t2 = System.nanoTime();
        System.out.println(t2 - t1);

        /*TestEnum.BYE.say();*/

    }
}
