package com.example.fitsteps.firebaseData.firebaseOwnProgramData

import java.time.LocalDate
data class ExerciseSet(
    var reps: Int=0,
    var weight: Float=0f,
    )
data class Record(
    val date: String = LocalDate.now().toString(),
    val sets : ArrayList<ExerciseSet> = arrayListOf(),
    val rest:Int = 0,
)
