package com.densev.multimodule.kot

import java.util.*

/**
 * Created by Dzianis_Sevastseyenk on 01/31/2017.
 */
enum class Gender {

    MALE(EnumSet.of(Appearance.BEAR, Appearance.MANLY, Appearance.FEMININE, Appearance.ANDROGYNOUS, Appearance.REGULAR)),
    FEMALE(EnumSet.of(Appearance.ANDROGYNOUS, Appearance.FEMININE, Appearance.AMAZON, Appearance.MAMA_BEAR));

    private var acceptableAppearance: EnumSet<Appearance> = EnumSet.noneOf(Appearance::class.java);

    constructor(acceptableAppearance: EnumSet<Appearance>) {
        this.acceptableAppearance = acceptableAppearance
    }

    fun getAcceptableAppearance(): EnumSet<Appearance> {
        return this.acceptableAppearance
    }


}