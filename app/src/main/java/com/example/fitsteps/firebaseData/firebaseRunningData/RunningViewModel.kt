package com.example.fitsteps.firebaseData.firebaseRunningData

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.fitsteps.authentication.User
import com.google.android.gms.maps.model.LatLng
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await
import org.json.JSONException
import org.json.JSONObject
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
    fun updateLocation(latLng: LatLng, userPhoneNumber: String) {
        val userId = auth.currentUser?.uid
        val add = HashMap<String,Any>()
        if(userId != null) {
            add["latitude"] = latLng.latitude
            add["longitude"] = latLng.longitude
            Log.d("user number", userPhoneNumber)
            FirebaseFirestore.getInstance().collection("running_users").document(userPhoneNumber)
                .set(add, SetOptions.merge())
                .addOnSuccessListener {
                    Log.d("Location", "updated")
                }
                .addOnFailureListener {
                    Log.d("Location", "Failed")
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
    suspend fun getNumber(): String{
        val userId = Firebase.auth.currentUser?.uid
        val database = FirebaseFirestore.getInstance()
        val usersRef = database.collection("users_contacts")
        var doc = ""
        try {
            val querySnapshot = usersRef.whereEqualTo("userid", userId).get().await()
            if (!querySnapshot.isEmpty) {
                for (document in querySnapshot.documents) {
                    doc = document.id
                    break
                }
            }
        }catch (e: Exception) {
            Log.d("Phone number", "Error getting user by uid: ${e.message}")
        }
        Log.d("Phone search", doc)
        return doc
    }
     fun updateState(startUpdates: Boolean, context: Context, number: String) {
         if (startUpdates) {
             val key =
                 "key=AAAAHNy6L3U:APA91bGXlvQE0EwOQWOnhLQRwFL1lK6OflEeZR4zYkVK5EbR9-06KUnXYsiPx6ZCh7Nzl6u_UeyYjithI-eVQTjvZtT2YVCof7-l2_jOW9alizoSmrjU18YHH-OGnzBkoXRAnX9E-lh7"
             val FCM_API = "https://fcm.googleapis.com/fcm/send"
             val contentType = "application/json"
             val topic =
                 "/topics/$number" //topic has to match what the receiver subscribed to

             val notification = JSONObject()
             val notificationBody = JSONObject()

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
                     try {
                         notificationBody.put("title", "Corre junto a ${user.user_name}")
                         notificationBody.put(
                             "body",
                             "¡Corre con FitSteps! tu amigo ${user.user_name} está corriendo ¡Únete a él! "
                         )   //Enter your notification message
                         notification.put("to", topic)
                         notification.put("title", "Running notification")
                         notification.put("notification", notificationBody)
                         Log.e("TAG", "try")
                     } catch (e: JSONException) {
                         Log.e("TAG", "onCreate: " + e.message)
                     }

                     val requestQueue: RequestQueue by lazy {
                         Volley.newRequestQueue(context)
                     }
                     val jsonObjectRequest =
                         object : JsonObjectRequest(FCM_API, notification,
                             Response.Listener<JSONObject> { response ->
                                 Log.i("TAG", "onResponse: $response")
                             },
                             Response.ErrorListener {
                                 Log.i("TAG", "onErrorResponse: Didn't work")
                             }) {
                             override fun getHeaders(): Map<String, String> {
                                 val params = java.util.HashMap<String, String>()
                                 params["Authorization"] = key
                                 params["Content-Type"] = contentType
                                 return params
                             }
                         }
                     requestQueue.add(jsonObjectRequest)
                 }
                 .addOnFailureListener { exception ->
                     Log.d("Error", "Error getting documents: ", exception)
                 }
         } else {
             val db = FirebaseFirestore.getInstance()
             val documentReference = db.collection("running_users").document(number)
             documentReference.delete()
                 .addOnSuccessListener {
                     Log.d("Firestore", "User location deleted")
                 }
                 .addOnFailureListener { e ->
                     Log.e("Firestore", "Error at deleting user location", e)
                 }
         }
     }
}