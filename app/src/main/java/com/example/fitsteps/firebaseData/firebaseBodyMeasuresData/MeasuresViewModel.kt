package com.example.fitsteps.firebaseData.firebaseBodyMeasuresData

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Date

class MeasuresViewModel {
    val auth: FirebaseAuth = Firebase.auth
    val userid = auth.currentUser?.uid
    var actualMeasure = mutableStateOf(Measures())
    var selectedMeasure = mutableStateOf(Measures())
    fun uploadMeasure(

    ) {
        val userId = auth.currentUser?.uid
        val add = HashMap<String, Any>()
        if (userId != null) {
            add["fecha"] = LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
            add["foto"] = actualMeasure.value.foto
            add["hombros"] = actualMeasure.value.hombros
            add["pecho"] = actualMeasure.value.pecho
            add["antebrazoIzq"] = actualMeasure.value.antebrazoIzq
            add["antebrazoDer"] = actualMeasure.value.antebrazoDer
            add["brazoIzq"] = actualMeasure.value.brazoIzq
            add["brazoDer"] = actualMeasure.value.brazoDer
            add["cintura"] = actualMeasure.value.cintura
            add["cadera"] = actualMeasure.value.cadera
            add["piernaIzq"] = actualMeasure.value.piernaIzq
            add["piernaDer"] = actualMeasure.value.piernaDer
            add["pantorrillaDer"] = actualMeasure.value.pantorrillaDer
            add["pantorrillaIzq"] = actualMeasure.value.pantorrillaIzq
            add["timestamp"] = Date().time
            add["uid"] = userId
            FirebaseFirestore.getInstance().collection("users_body_measures")
                .add(add)
                .addOnSuccessListener {
                    Log.d("Guardado de ruta", "exitoso, id: ${it.id}")
                }
                .addOnFailureListener {
                    Log.d("Guardado de ruta", "error: $it")
                }
        }
    }

}