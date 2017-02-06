package com.densev.multimodule.kot.army

/**
 * Created by Dzianis_Sevastseyenk on 02/03/2017.
 */
class HorseArchers : Horsemen, Archers {

    var numbers : Int? = null
        public set
        public get

    var counter = 0
        set(value) {
            if (value >= 0) field = value
        }

    constructor()



}