package com.example.fitsteps.firebaseData.firebaseOwnProgramData

import android.util.Log

data class Routine(
    val name: String ="",
    val days: String ="",
    var exercises: ArrayList<ExerciseRecord> = arrayListOf(),
    var time: Int = 0,
){
    init {
        calculateTime()
    }
     fun calculateTime(){
        time = 0
        for(exercise in exercises){
            for(record in exercise.records){
                time += (((record.rest/60)+1)*record.sets.size)+1
            }
        }

    }
    fun addExercise(exercise: ExerciseRecord){
        exercises.add(exercise)
        calculateTime()
        Log.d("EXERCISE ADDED", "Exercise added to routine")
    }
}

