package com.densev.multimodule.enums;

/**
 * Created by Dzianis_Sevastseyenk on 11/01/2016.
 */
public enum Rarity {

    NONE(0.2),
    COMMON(0.5),
    UNCOMMON(0.35),
    RARE(0.1),
    LEGENDARY(0.05);

    Rarity(double weight){
        this.weight = weight;
    }

    private double weight;

    public double getWeight(){
        return this.weight;
    }
}
