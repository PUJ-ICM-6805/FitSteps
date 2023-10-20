package com.example.fitsteps.firebaseRunningData

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.fitsteps.authentication.User
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
    var actualRoute = Route()
    lateinit var user: User
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
            add["timestamp"] = Date().time
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
    fun uploadUserData() {
        FirebaseFirestore.getInstance().collection("users")
            .whereEqualTo("userId", userid)
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    val experience = document.data["experience"] as String
                    val gender = document.data["gender"] as String
                    val height = document.data["height"] as Double
                    val weight = document.data["weight"] as Double
                    val birthDate = document.data["user_birth_date"] as String
                    val name = document.data["user_name"] as String
                    val avatar = document.data["avatar"] as String
                    user = User(name, birthDate, gender, weight.toFloat(), height.toFloat(), experience, avatar, userid.toString())
                }
            }
            .addOnFailureListener { exception ->
                Log.d("Error", "Error getting documents: ", exception)
            }
    }
}