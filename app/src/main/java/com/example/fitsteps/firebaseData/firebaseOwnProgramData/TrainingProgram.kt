package com.example.fitsteps.firebaseData.firebaseOwnProgramData


data class TrainingProgram(
    val description: String ="",
    val name: String ="",
    val objective: String ="",
    var routines: MutableList<Routine> = mutableListOf(),
    var uid : String = "",
    var image : String = ""
){
   override fun toString(): String {
        return "TrainingProgram(description='$description', name='$name', objective='$objective', uid='$uid')"
    }
    fun addRoutine(routine: Routine){
        routines.add(routine)
    }

}
