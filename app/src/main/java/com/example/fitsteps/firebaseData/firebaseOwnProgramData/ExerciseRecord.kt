package com.example.fitsteps.firebaseData.firebaseOwnProgramData

import com.example.fitsteps.firebaseData.firebaseExerciseData.Exercise

data class ExerciseRecord(
    val exercise: Exercise = Exercise(),
    val records : ArrayList<Record> = arrayListOf(),
){
    fun addRecord(record: Record){
        records.add(record)
    }

}

