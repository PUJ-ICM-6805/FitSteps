package com.example.fitsteps.screens.social

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.FirebaseMessaging

class UserContactsViewModel: ViewModel() {
    val auth: FirebaseAuth = Firebase.auth
    var userid = auth.currentUser?.uid
    var userContacts = mutableListOf<String>()
    var userPhoneNumber = mutableStateOf("")
    val userExists = mutableStateOf(false)
    var onlineStatus = mutableStateOf(false)

    fun uploadUserContacts(
        contacts : List<String>
    ) {
        val userId = auth.currentUser?.uid
        val phoneNumber = userPhoneNumber.value
        val add = HashMap<String, Any>()
        val contactsFiltered = contacts.filter { it != phoneNumber}
        val contactos = ArrayList(LinkedHashSet(contactsFiltered))
        Log.d("numero", phoneNumber)
        FirebaseFirestore.getInstance().collection("users_contacts")
            .whereEqualTo("userid", userId)
            .get()
            .addOnSuccessListener { result ->
                var previousContacts = listOf<String>()
                if(!result.isEmpty) {
                    previousContacts = result.documents[0].get("contacts") as List<String>
                }
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
                    for (previousContact in previousContacts) {
                        if(!contactos.contains(previousContact)) {
                            FirebaseMessaging.getInstance().unsubscribeFromTopic(previousContact)
                        }
                    }
                    for (contact in contactos) {
                        FirebaseMessaging.getInstance().subscribeToTopic(contact)
                    }
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