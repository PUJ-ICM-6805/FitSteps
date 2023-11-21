package com.example.fitsteps.screens.social

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.fitsteps.firebaseData.firebaseBodyMeasuresData.Measures
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.google.firebase.ktx.Firebase

class UserContactsViewModel: ViewModel() {
    val auth: FirebaseAuth = Firebase.auth
    var userid = auth.currentUser?.uid
    var userContacts = mutableListOf<String>()
    var userPhoneNumber = mutableStateOf("")
    val userExists = mutableStateOf(false)
    var onlineStatus = mutableStateOf(false)

    fun uploadUserContacts(
        contactos : List<String>
    ) {
        val userId = auth.currentUser?.uid
        val phoneNumber = userPhoneNumber.value
        val add = HashMap<String, Any>()
        Log.d("numero", phoneNumber)
        if (userId != null) {
            add["contacts"] = contactos
            Log.d("contactos antes de fstore", contactos.toString())
            add["userid"] = userId
            FirebaseFirestore.getInstance().collection("users_contacts")
                .document(phoneNumber)  // Establecer el número telefónico como el ID del documento
                .set(add, SetOptions.merge())
                .addOnSuccessListener {
                    Log.d("Guardado de contactos", "exitoso para el número: ${userPhoneNumber.value}")
                }
                .addOnFailureListener {
                    Log.d("Guardado de contactos", "error: $it")
                }
        }
    }

    fun setPhoneNumber(it: String) {
        userPhoneNumber.value = it
    }

    fun updateOnlineStatus(userId: String, online: Boolean) {
        onlineStatus.value = online
    }
}