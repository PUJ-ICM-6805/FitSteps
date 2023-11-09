package com.example.fitsteps.authentication

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {
    private val auth: FirebaseAuth = Firebase.auth
    private val _loading = MutableLiveData(false)

    fun signInWithEmailAndPassword(email: String, password: String, home: ()-> Unit, error: (String)-> Unit)
    = viewModelScope.launch {
        try {
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener {task ->
                    if(task.isSuccessful) {
                        Log.d("Autenticación", "logueado")
                        home()
                    }
                    else {
                        Log.d("Autenticación", "no logueado")
                        error("Datos incorrectos, verifique e intente nuevamente")
                    }
                }
        }catch(ex: Exception) {
            Log.d("Autenticacion", "fallo en logueo: ${ex.message}")
            error("Error en login, intente nuevamente")
        }
    }

    fun createUserWithEmailAndPassword(
        email: String,
        password: String,
        userData: User,
        home: () -> Unit,
    ) {
        if(_loading.value == false) {
            _loading.value = true
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if(task.isSuccessful) {
                        createUser(userData)
                        home()
                    }else {
                        Log.d("Creacion de cuenta", "Error al crear usuario con email $email")
                    }
                    _loading.value = false
                }
        }
    }

    fun createUser(userData: User) {
        val userId = auth.currentUser?.uid
        if(userId != null) {
            val user = User(
                user_name = userData.user_name,
                user_birth_date = userData.user_birth_date,
                gender = userData.gender,
                weight = userData.weight,
                height = userData.height,
                experience = userData.experience,
                avatar = userData.avatar,
                userId = userId,
            )
            FirebaseFirestore.getInstance().collection("users")
                .add(user.toMap())
                .addOnSuccessListener {
                    Log.d("Creacion de cuenta", "exitosa, id: ${it.id}")
                }
                .addOnFailureListener {
                    Log.d("Creacion de cuenta", "error: $it")
                }
        }
    }
}