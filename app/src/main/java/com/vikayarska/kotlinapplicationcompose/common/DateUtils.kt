package com.vikayarska.kotlinapplicationcompose.common

import java.util.*

fun Calendar.generateRandomDate(): Date {
    val previousYear = Calendar.getInstance().get(Calendar.YEAR) - 1
    this.set((1950..previousYear).random(), (1..12).random(), (1..28).random())

    return this.time
}