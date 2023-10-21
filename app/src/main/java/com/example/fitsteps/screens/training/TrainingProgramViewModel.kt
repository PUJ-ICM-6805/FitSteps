package com.example.fitsteps.screens.training

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class TrainingProgramViewModel {
    private val db = Firebase.firestore
    private val exercisesCollection = db.collection("users_own_programs")
    private val _ownProgramList = MutableLiveData<List<TrainingProgram>>()

    val ownProgramList: LiveData<List<TrainingProgram>>
        get() = _ownProgramList

    init {
        loadAllOwnPrograms()
    }

    private fun loadAllOwnPrograms() {
        exercisesCollection.get()
            .addOnSuccessListener { querySnapshot ->
                val programs = ArrayList<TrainingProgram>()
                for (document in querySnapshot) {
                    val trainingProgram = document.toObject(TrainingProgram::class.java)
                    programs.add(trainingProgram)
                    Log.d("TrainingProgram", trainingProgram.name)
                }
                _ownProgramList.value = programs
            }
    }
    fun saveTrainingProgram(trainingProgram: TrainingProgram, onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
        exercisesCollection
            .add(trainingProgram)
            .addOnSuccessListener { documentReference ->
                Log.d("TrainingProgram", "Training program added with ID: ${documentReference.id}")
                onSuccess()
            }
            .addOnFailureListener { e ->
                Log.e("TrainingProgram", "Error adding training program", e)
                onFailure(e)
            }
    }


}