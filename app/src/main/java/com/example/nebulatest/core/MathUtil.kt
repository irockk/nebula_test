package com.example.nebulatest.core

import java.text.DecimalFormat

fun formatDoubleToString(value: Double): String {
    val df = DecimalFormat("#.##########")
    return df.format(value)
}