package com.densev.multimodule.kot

import java.util.*

/**
 * Created on 30.01.2017.
 */
object PersonFactory {


    fun createPerson() : Person {

        val genderI : kotlin.Int = RandomFactory.int(Gender.values().size)
        val chosenGender : Gender = Gender.values()[genderI]

        val appearanceI : kotlin.Int = RandomFactory.int(chosenGender.getAcceptableAppearance().size)
        val chosenAppearance : Appearance = chosenGender.getAcceptableAppearance().elementAt(appearanceI)


        return Person(NameFactory.getName(chosenGender), chosenGender, chosenAppearance);
    }

}