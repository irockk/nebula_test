package com.example.nebulatest.core

import org.koin.core.annotation.Factory

@Factory
class TimeProvider {
    fun getCurrentTimeMillis() = System.currentTimeMillis()
}