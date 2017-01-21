package com.densev.multimodule.enums;

import java.util.EnumMap;
import java.util.List;

/**
 * Created by Dzianis_Sevastseyenk on 11/01/2016.
 */
public class RandomSelector {


    private List<Randomized> rL;

    public RandomSelector(List<Randomized> rL) {
        this.rL = rL;
    }


    public Randomized pick(){
        Randomized randomized = null;

        double total = 0.0;
        for (Randomized r : rL){
            total += r.getRarity().getWeight();
        }
        EnumMap<Rarity, Integer> occurances = new EnumMap<Rarity, Integer>(Rarity.class);
        for(Randomized r : rL){
            if(occurances.containsKey(r.getRarity())){
                int newVal = occurances.get(r.getRarity()) + 1;
                occurances.put(r.getRarity(), newVal );
            } else {
                occurances.put(r.getRarity(), 1);
            }
        }

        double bestValue = Double.MAX_VALUE;

        for (Randomized element : rL) {
            double value = -Math.log(Main.rand.nextDouble()) / (element.getRarity().getWeight() /occurances.get(element.getRarity()));

            if (value < bestValue) {
                bestValue = value;
                randomized = element;
            }
        }
        return randomized;
    }
}
