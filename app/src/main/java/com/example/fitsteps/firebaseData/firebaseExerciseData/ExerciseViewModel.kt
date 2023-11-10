package com.example.fitsteps.firebaseData.firebaseExerciseData

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class ExerciseViewModel : ViewModel() {
    private val db = Firebase.firestore
    private val exercisesCollection = db.collection("exercises")
    private val _exerciseList = MutableLiveData<List<Exercise>>()
    val exerciseList: LiveData<List<Exercise>>
        get() = _exerciseList

    init {
        loadAllExercises()
    }

    private fun loadAllExercises() {
        exercisesCollection.get()
            .addOnSuccessListener { querySnapshot ->
                val exercises = ArrayList<Exercise>()
                for (document in querySnapshot) {
                    val exercise = document.toObject(Exercise::class.java)
                    exercises.add(exercise)
                    Log.d("Exercise", exercise.name)
                }
                _exerciseList.value = exercises
            }
    }

}
