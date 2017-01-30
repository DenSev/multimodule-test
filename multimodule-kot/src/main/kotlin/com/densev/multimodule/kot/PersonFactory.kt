package com.densev.multimodule.kot

/**
 * Created on 30.01.2017.
 */
object PersonFactory {

    fun createPerson() : Person {
        return Person("factory_person", Person.Gender.MALE, Person.Appearance.MANLY);
    }

}