package com.example.nebulatest.core

import java.text.DecimalFormat

fun formatDouble(value: Double): String {
    val df = DecimalFormat("#.##########")
    return df.format(value)
}