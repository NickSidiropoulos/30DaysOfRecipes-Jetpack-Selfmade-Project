package com.nicksidiropoulos.a30daysofrecipes.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class Recipe(
    val day: Int,
    @StringRes val name: Int,
    @StringRes val description: Int,
    @StringRes val duration: Int,
    @DrawableRes val image: Int
)
