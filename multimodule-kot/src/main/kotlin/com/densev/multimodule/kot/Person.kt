package com.densev.multimodule.kot

/**
 * Created by Dzianis_Sevastseyenk on 01/04/2017.
 */
class Person {

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

    fun gender(gender: Gender) : Person {
        this.gender = gender
        return this
    }

    fun mother(mother: Person) : Person {
        this.mother = mother
        return this
    }

    fun father(father: Person) : Person {
        this.father = father
        return this
    }

    fun appearsAs(appearsAs: Appearance) : Person {
        this.appearsAs = appearsAs
        return this
    }
}
