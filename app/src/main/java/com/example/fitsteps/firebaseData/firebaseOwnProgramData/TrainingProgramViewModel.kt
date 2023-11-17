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
    private val _selectedRoutine = MutableLiveData<Routine>()

    val selectedProgram: LiveData<TrainingProgram>
        get() = _selectedProgram

    val selectedRoutine: LiveData<Routine>
        get() = _selectedRoutine

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
    fun setSelectedRoutine(routine: Routine) {
        _selectedRoutine.value = routine
        loadExercisesFromActualRoutine({
            Log.d("EXERCISES","Exercises loaded")
        },{
            Log.e("EXERCISES","Error loading exercises",it)
        })
        Log.d("DATA CHANGED", "Routine changed to${_selectedRoutine.value}")
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
        val add = HashMap<String,Any>()
        programsCollection.whereEqualTo("uid",userid).whereEqualTo("name",_selectedProgram.value?.name)
            .get()
            .addOnSuccessListener {result ->
                for(document in result){
                    val auxRef = programsCollection.document(document.id)
                    add["name"] = routine.name
                    add["days"] = routine.days
                    add["time"] = routine.time
                    auxRef.collection("routines")
                        .add(add)
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
    private fun loadExercisesFromActualRoutine(onSuccess: () -> Unit, onFailure: (Exception) -> Unit){
        programsCollection.whereEqualTo("uid",userid).whereEqualTo("name",_selectedProgram.value?.name)
            .get()
            .addOnSuccessListener {result ->
                for(document in result){
                    val auxRef = programsCollection.document(document.id)
                    auxRef.collection("routines").whereEqualTo("name",_selectedRoutine.value?.name)
                        .get()
                        .addOnSuccessListener {routineResult ->
                            for(routineDocument in routineResult){
                                val auxRoutineRef = auxRef.collection("routines").document(routineDocument.id)
                                auxRoutineRef.collection("exercises")
                                    .get()
                                    .addOnSuccessListener {exerciseResult ->
                                        val exercises = ArrayList<ExerciseRecord>()
                                        for(exerciseDocument in exerciseResult){
                                            val exercise = exerciseDocument.toObject(ExerciseRecord::class.java)
                                            exercises.add(exercise)
                                        }
                                        _selectedRoutine.value?.exercises = exercises
                                        updateTimeFromActualRoutine()
                                        onSuccess()
                                    }
                                    .addOnFailureListener { e ->
                                        Log.e("Exercise", "Error loading exercises", e)
                                        onFailure(e)
                                    }
                            }
                        }
                }
            }
    }
    private fun updateTimeFromActualRoutine(){
        programsCollection.whereEqualTo("uid",userid).whereEqualTo("name",_selectedProgram.value?.name)
            .get()
            .addOnSuccessListener {result ->
                for(document in result){
                    val auxRef = programsCollection.document(document.id)
                    auxRef.collection("routines").whereEqualTo("name",_selectedRoutine.value?.name)
                        .get()
                        .addOnSuccessListener {routineResult ->
                            for(routineDocument in routineResult){
                                val auxRoutineRef = auxRef.collection("routines").document(routineDocument.id)
                                _selectedRoutine.value?.calculateTime()
                                auxRoutineRef.update("time",_selectedRoutine.value?.time)
                                    .addOnSuccessListener {
                                        Log.d("Routine","Routine updated")
                                    }
                                    .addOnFailureListener { e ->
                                        Log.e("Routine", "Error updating routine", e)
                                    }
                            }
                        }
                }
            }
    }
    fun addExerciseToActualRoutine(exercise: ExerciseRecord,onSuccess: () -> Unit,onFailure: (Exception) -> Unit){
        _selectedRoutine.value?.addExercise(exercise)
        programsCollection.whereEqualTo("uid",userid).whereEqualTo("name",_selectedProgram.value?.name)
            .get()
            .addOnSuccessListener {result ->
                for(document in result){
                    val auxRef = programsCollection.document(document.id)
                    auxRef.collection("routines").whereEqualTo("name",_selectedRoutine.value?.name)
                        .get()
                        .addOnSuccessListener {routineResult ->
                            for(routineDocument in routineResult){
                                val auxRoutineRef = auxRef.collection("routines").document(routineDocument.id)
                                auxRoutineRef.collection("exercises")
                                    .add(exercise)
                                    .addOnSuccessListener {
                                        Log.d("Exercise","Exercise added with ID: ${it.id}")
                                        onSuccess()
                                    }
                                    .addOnFailureListener { e ->
                                        Log.e("Exercise", "Error adding exercise", e)
                                        onFailure(e)
                                    }
                            }
                        }
                }
            }
    }

}