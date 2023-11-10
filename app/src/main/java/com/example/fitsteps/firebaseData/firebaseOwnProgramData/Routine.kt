package com.example.fitsteps.firebaseData.firebaseOwnProgramData

import com.example.fitsteps.firebaseData.firebaseExerciseData.Exercise

data class Routine(
    val name: String ="",
    val days: String ="",
    val exercises: List<Exercise> = emptyList(),
    val time: String ="",
    val kcal :String = ""
    ,
)
