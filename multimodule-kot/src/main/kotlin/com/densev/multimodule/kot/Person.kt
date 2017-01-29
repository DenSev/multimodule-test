package com.densev.multimodule.kot

import org.slf4j.Logger
import org.slf4j.LoggerFactory

/**
 * Created by Dzianis_Sevastseyenk on 01/04/2017.
 */
class Person(name: String?, mother: Person?, father: Person?, gender: Gender?, appearsAs: Appearance?) {

    companion object {
        val logger: Logger = LoggerFactory.getLogger(Person::class.java);
    }

    constructor (name: String, gender: Gender, appearsAs: Appearance) : this(name, null, null, gender, appearsAs){
        this.name = name
        this.gender = gender
        this.appearsAs = appearsAs
    }

    init {
        logger.debug("Person initialized with following params:\nname: ${name}, gender: ${gender}, mother: ${mother?.name}, father: ${father?.name}")
    }

    var name: String? = null
    var mother: Person? = null
    var father: Person? = null
    var gender: Gender? = null
    var appearsAs: Appearance? = null

    enum class Gender {
        MALE,
        FEMALE
    }

    enum class Appearance {
        MANLY,
        FEMININE,
        ANDROGYNOUS,
        AMAZON,
        REGULAR,
        BEAR,
        MAMA_BEAR
    }

    override fun toString(): String {
        return "com.lordvekh.multimodule.kot.Person(name=$name, mother=$mother, father=$father, gender=$gender, appearsAs=$appearsAs)"
    }

    fun name(name: String): Person {
        this.name = name
        return this
    }

    fun gender(gender: Gender): Person {
        this.gender = gender
        return this
    }

    fun mother(mother: Person): Person {
        this.mother = mother
        return this
    }

    fun father(father: Person): Person {
        this.father = father
        return this
    }

    fun appearsAs(appearsAs: Appearance): Person {
        this.appearsAs = appearsAs
        return this
    }
}
