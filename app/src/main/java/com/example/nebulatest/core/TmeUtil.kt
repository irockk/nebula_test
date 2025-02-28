package com.example.nebulatest.core

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun formatLongToDate(timestamp: Long): String {
    val sdf = SimpleDateFormat("dd MMM yyyy, HH:mm", Locale.getDefault())
    return sdf.format(Date(timestamp))
}

fun formatLongToTime(timestamp: Long): String {
    val sdf = SimpleDateFormat("HH:mm", Locale.getDefault())
    return sdf.format(Date(timestamp))
}