package com.example.fitsteps.firebaseData.firebaseExerciseData

data class Exercise(
    val name: String ="",
    val description: String ="",
    val image: String ="",
    val video: String ="",
    val muscleGroups: ArrayList<String> = arrayListOf(),
)


