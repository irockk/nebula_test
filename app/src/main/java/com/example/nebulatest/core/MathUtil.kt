package com.example.nebulatest.core

import java.text.DecimalFormat

fun formatDouble(value: Double): String {
    val df = DecimalFormat("0.#################")
    return df.format(value)
}