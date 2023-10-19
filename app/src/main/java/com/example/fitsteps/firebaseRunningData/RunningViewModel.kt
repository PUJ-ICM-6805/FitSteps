package com.example.fitsteps.firebaseRunningData

import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.android.gms.maps.model.LatLng
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale

class RunningViewModel : ViewModel() {
    val auth: FirebaseAuth = Firebase.auth
    val userid = auth.currentUser?.uid
    var ActualRoute = Route()
    fun uploadRoute(route: List<LatLng>, time: String, steps: Int, distance: String) {
        val userId = auth.currentUser?.uid
        val add = HashMap<String,Any>()
        val currentTime = Date()
        val format = SimpleDateFormat("h:mm a", Locale.getDefault())
        if(userId != null) {
            add["route"] = route
            add["uid"] = userId.toString()
            add["time"] = time
            add["steps"] = steps
            add["distance"] = distance
            add["date"] = LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
            add["hour"] = format.format(currentTime)
            FirebaseFirestore.getInstance().collection("users_routes")
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