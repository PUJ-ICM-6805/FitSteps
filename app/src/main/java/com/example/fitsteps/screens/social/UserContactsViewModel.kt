package com.example.fitsteps.screens.social

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import com.example.fitsteps.firebaseData.firebaseBodyMeasuresData.Measures
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.google.firebase.ktx.Firebase

class UserContactsViewModel {
    val auth: FirebaseAuth = Firebase.auth
    var userid = auth.currentUser?.uid
    var setUserContacts = setOf<String>()
    var userPhoneNumber = mutableStateOf("")

    fun uploadUserContacts(
        contactos : List<String>
    ) {
        val userId = auth.currentUser?.uid
        val phoneNumber = userPhoneNumber.value
        val add = HashMap<String, Any>()
        setUserContacts = contactos.toSet()
        Log.d("numero", phoneNumber)
        if (userId != null) {
            add["contacts"] = setUserContacts.toList()
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
}