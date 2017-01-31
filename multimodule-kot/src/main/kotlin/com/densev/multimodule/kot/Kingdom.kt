package com.densev.multimodule.kot

import org.slf4j.Logger
import org.slf4j.LoggerFactory

/**
 * Created by Dzianis_Sevastseyenk on 01/31/2017.
 */
class Kingdom(kingdomName: String) {

    companion object{
        val logger : Logger = LoggerFactory.getLogger(Kingdom::class.java)
    }

    init {
        logger.debug("new instance of Kingodom class was initialized with name: $kingdomName")
    }

    val kingdomName: String = ""
    var treasury: Int = 0
    var population: Int = 0
    var armyStrength: Int = 0


    fun treasury(treasury: Int): Kingdom {
        this.treasury = treasury
        return this
    }

    fun population(population: Int) : Kingdom{
        this.population = population
        return this
    }

    fun armyStrength(armyStrength: Int) : Kingdom{
        this.armyStrength = armyStrength
        return this
    }

}