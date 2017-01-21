package com.densev.multimodule.kot

/**
 * Created by Dzianis_Sevastseyenk on 01/03/2017.
 */

fun main(args: Array<String>) {
    println("hello world")
    println(task1(listOf(1, 2, 3, 4)))
    val p = Person()
    p.name("name")
            .gender(Person.Gender.FEMALE)
            .appearsAs(Person.Appearance.FEMININE)

    println(p.toString())
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