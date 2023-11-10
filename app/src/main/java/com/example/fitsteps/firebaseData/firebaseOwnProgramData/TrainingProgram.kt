package com.example.fitsteps.firebaseData.firebaseOwnProgramData


data class TrainingProgram(
    val description: String ="",
    val name: String ="",
    val objective: String ="",
    val routines: List<Routine> = emptyList(),
    var uid : String = ""

){
   override fun toString(): String {
        return "TrainingProgram(description='$description', name='$name', objective='$objective', uid='$uid')"
    }

}
