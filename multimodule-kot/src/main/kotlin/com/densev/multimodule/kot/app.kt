package com.densev.multimodule.kot

/**
 * Created by Dzianis_Sevastseyenk on 01/03/2017.
 */

fun main(args: Array<String>) {
    for (i in 1..10) {
        PersonFactory.createPerson()
    }
}

fun test() {

    val p = Person(
            name = "its a name",
            mother = null,
            father = null,
            gender = Gender.MALE,
            appearsAs = Appearance.MANLY
    )

    p.name("name")
            .gender(Gender.FEMALE)
            .appearsAs(Appearance.FEMININE)
}

fun task1(collection: Collection<Int>): String {
    val sb = StringBuilder()
    sb.append("{")
    val iterator = collection.iterator()
    while (iterator.hasNext()) {
        val element = iterator.next()
        sb.append(element)
        if (iterator.hasNext()) {
            sb.append(", ")
        }
    }
    sb.append("}")
    return sb.toString()
}