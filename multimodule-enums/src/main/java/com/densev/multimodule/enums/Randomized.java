package com.densev.multimodule.enums;

/**
 * Created by Dzianis_Sevastseyenk on 11/01/2016.
 */
public class Randomized {

    private String name;
    private Rarity rarity;



    public Randomized(String name, Rarity rarity) {
        this.name = name;
        this.rarity = rarity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Rarity getRarity() {
        return rarity;
    }

    public void setRarity(Rarity rarity) {
        this.rarity = rarity;
    }
}
