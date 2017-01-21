package com.densev.multimodule.enums;


import java.util.Random;

public class Main {
    public static final Random rand = new Random();

    public static void main(String[] args) throws IllegalAccessException, InstantiationException {

        TestEnum.BYE.say();

    }
}
