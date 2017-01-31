package com.densev.multimodule.kot

import java.util.*

/**
 * Created by Dzianis_Sevastseyenk on 01/31/2017.
 */
object RandomFactory {

    private val rand : Random = Random()

    fun int(bound: kotlin.Int) : kotlin.Int {
        return rand.nextInt(bound)
    }
}