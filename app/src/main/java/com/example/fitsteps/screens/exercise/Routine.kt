package com.example.fitsteps.screens.exercise

data class Routine(
    val name: String ="",
    val days: List<Char> = emptyList(),//TODO: Change to DayOfWeek
    val exercises: List<Exercise> = emptyList(),
    val time: String ="",
    val kcal :String = ""
    ,
)
