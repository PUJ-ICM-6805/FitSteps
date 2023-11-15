package com.example.fitsteps.firebaseData.firebaseExerciseData

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class ExerciseViewModel : ViewModel() {

    //Firebase firestore
    private val db = Firebase.firestore
    private val exercisesCollection = db.collection("exercises")
    private val _exerciseList = MutableLiveData<List<Exercise>>()
    val exerciseList: LiveData<List<Exercise>>
        get() = _exerciseList

    //Shared data
    private val _selectedMuscle = MutableLiveData<String>()
    val selectedMuscle: LiveData<String>
        get() = _selectedMuscle

    private val _selectedExercise = MutableLiveData<Exercise>()

    val selectedExercise: LiveData<Exercise>
        get() = _selectedExercise
    fun setSelectedMuscle (muscle: String){
        _selectedMuscle.value = muscle
        Log.d("DATA CHANGED", "Muscle selected changed to ${_selectedMuscle.value}")
    }
    fun setSelectedExercise (exercise: Exercise){
        _selectedExercise.value = exercise
        Log.d("DATA CHANGED", "Exercise selected changed to ${_selectedExercise.value}")
    }
    init {

    }

    fun loadExercisesPerMuscle(muscle: String){
        exercisesCollection.whereArrayContains("muscleGroups", muscle).get()
            .addOnSuccessListener { querySnapshot ->
                if(querySnapshot== null){
                    Log.d("Exercise", "No exercises found")
                }else {
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

}
