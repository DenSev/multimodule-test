package com.densev.multimodule.kot

/**
 * Created by Dzianis_Sevastseyenk on 01/31/2017.
 */
object NameFactory {

    private val maleNames : List<String> = listOf("Jack","John","Brad")
    private val femaleNames : List<String> = listOf("Jane","Jennifer", "Betty")

    fun getName( gender: Gender) : String {
        when(gender){
            Gender.MALE -> {
                val index : kotlin.Int = RandomFactory.int(maleNames.size)
                return maleNames[index]
            }
            Gender.FEMALE -> {
                val index : kotlin.Int = RandomFactory.int(femaleNames.size)
                return femaleNames[index]
            }
        }
    }

}