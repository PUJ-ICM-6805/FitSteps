package com.example.fitsteps.firebaseData.firebaseOwnProgramData

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class TrainingProgramViewModel {

    //Firebase auth
    val auth: FirebaseAuth = Firebase.auth
    val userid = auth.currentUser?.uid

    //Firebase firestore
    private val db = Firebase.firestore
    private val programsCollection = db.collection("users_own_programs")
    private val _ownProgramList = MutableLiveData<List<TrainingProgram>>()

    //Shared data
    private val _selectedProgram = MutableLiveData<TrainingProgram>()

    val selectedProgram: LiveData<TrainingProgram>
        get() = _selectedProgram


    val ownProgramList: LiveData<List<TrainingProgram>>
        get() = _ownProgramList

    private val snapshotListener = EventListener<QuerySnapshot> { snapshot, exception ->
        if(exception != null) {
            Log.e("TrainingProgram", "Error loading exercises in listener", exception)
            return@EventListener
        }
        loadAllOwnPrograms()
    }
    init {
        programsCollection.addSnapshotListener(snapshotListener)
        loadAllOwnPrograms()
    }
    fun setSelectedProgram(trainingProgram: TrainingProgram) {
        _selectedProgram.value = trainingProgram
        Log.d("DATA CHANGED", "Training program changed to${_selectedProgram.value}")
    }

    private fun loadAllOwnPrograms() {
        programsCollection.get()
            .addOnSuccessListener { querySnapshot ->
                val programs = ArrayList<TrainingProgram>()
                for (document in querySnapshot) {
                    val trainingProgram = document.toObject(TrainingProgram::class.java)

                    //Accessing the subcolelction "routines"
                    programsCollection.document(document.id)
                        .collection("routines")
                        .get()
                        .addOnSuccessListener { queryRoutineSnapshot  ->
                            val routines = ArrayList<Routine>()
                            for (routineDocument in queryRoutineSnapshot) {
                                val routine = routineDocument.toObject(Routine::class.java)
                                routines.add(routine)
                            }
                            trainingProgram.routines = routines

                            programs.add(trainingProgram)

                            _ownProgramList.value = programs
                        }
                        .addOnFailureListener{
                            Log.e("LoadRoutines", "Error loading routines", it)
                        }
                }
            }
            .addOnFailureListener{
                Log.e("LoadPrograms", "Error loading programs", it)
            }
    }
    fun saveTrainingProgram(trainingProgram: TrainingProgram, onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
       val add = HashMap<String,Any>()
        if (userid != null) {
            add["description"] = trainingProgram.description
            add["name"] = trainingProgram.name
            add["objective"] = trainingProgram.objective
            add["uid"] = userid.toString()
            programsCollection
                .add(add)
                .addOnSuccessListener { documentReference ->
                    Log.d(
                        "TrainingProgram",
                        "Training program added with ID: ${documentReference.id}"
                    )
                    onSuccess()
                }
                .addOnFailureListener { e ->
                    Log.e("TrainingProgram", "Error adding training program", e)
                    onFailure(e)
                }
        }

    }
    fun addRoutineToActualProgram(routine: Routine,onSuccess: () -> Unit,onFailure: (Exception) -> Unit){
        _selectedProgram.value?.addRoutine(routine)
        programsCollection.whereEqualTo("uid",userid).whereEqualTo("name",_selectedProgram.value?.name)
            .get()
            .addOnSuccessListener {result ->
                for(document in result){
                    val auxRef = programsCollection.document(document.id)
                    auxRef.collection("routines")
                        .add(routine)
                        .addOnSuccessListener {
                            Log.d("Routine","Routine added with ID: ${it.id}")
                            onSuccess()
                        }
                        .addOnFailureListener { e ->
                            Log.e("Routine", "Error adding routine", e)
                            onFailure(e)
                        }
                }
            }
    }

}