package com.example.fitsteps.screens.body.firebaseMeasuresData

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale

class MeasuresViewModel {
    val auth: FirebaseAuth = Firebase.auth
    val userid = auth.currentUser?.uid
    var ActualMeasure = Measure()
    fun uploadMeasure(
        foto: String,
        hombros: Int,
        pecho: Int,
        antebrazoIzq: Int,
        antebrazoDer: Int,
        brazoIzq: Int,
        brazoDer: Int,
        cintura: Int,
        cadera: Int,
        piernaDer: Int,
        piernaIzq: Int,
        pantorrillaDer: Int,
        pantorrillaIzq: Int,
        uid: String
    ) {
        val userId = auth.currentUser?.uid
        val add = HashMap<String, Any>()
        if (userId != null) {
            add["fecha"] = LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
            add["foto"] = foto
            add["hombros"] = hombros
            add["pecho"] = pecho
            add["antebrazoIzq"] = antebrazoIzq
            add["antebrazoDer"] = antebrazoDer
            add["brazoIzq"] = brazoIzq
            add["brazoDer"] = brazoDer
            add["cintura"] = cintura
            add["cadera"] = cadera
            add["piernaIzq"] = piernaIzq
            add["piernaDer"] = piernaDer
            add["pantorrillaDer"] = pantorrillaDer
            add["pantorrillaIzq"] = pantorrillaIzq
            add["uid"] = uid
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