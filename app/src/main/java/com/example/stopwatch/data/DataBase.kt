package com.example.stopwatch.data

import kotlin.random.Random

internal object DataBase {
    fun fetchData() = Random.nextInt()
}