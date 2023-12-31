package com.example.fitsteps.authentication

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.SetOptions
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {

    //Firebase auth
    private val auth: FirebaseAuth = Firebase.auth
    val userid = auth.currentUser?.uid

    //Firebase firestore
    private val db = Firebase.firestore
    private  val usersCollection = db.collection("users")
    private val _currentUser = MutableLiveData<User>()

    val currentUser: MutableLiveData<User>
        get() = _currentUser

    private val _loading = MutableLiveData(false)


    init{
        loadCurrentUser()
    }
    private fun loadCurrentUser(){
        if(userid!= null){
            usersCollection.whereEqualTo("userId", userid)
                .get()
                .addOnSuccessListener { documents ->
                    if(documents.size() > 0){
                        val user = documents.first().toObject(User::class.java)
                        user.document = documents.first().id
                        _currentUser.value = user
                        Log.d ("LoginViewModel", "Current user: ${user.user_name}")
                        Log.d ("Weight and height", "Current user: ${user.weight} ${user.height}")
                    }
                }
                .addOnFailureListener { exception ->
                    Log.d("LoginViewModel", "Error getting documents: ", exception)
                }
        }
    }

    fun signInWithEmailAndPassword(email: String, password: String, home: ()-> Unit, error: (String)-> Unit)
    = viewModelScope.launch {
        try {
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener {task ->
                    if(task.isSuccessful) {
                        setOnlineStatus()
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

    fun setOnlineStatus() {
        val userid = auth.currentUser?.uid
        val add = HashMap<String, Any>()
        if(userid != null) {
            add["online"] = true
            Log.d("Usuario", "es: $userid")
            FirebaseFirestore.getInstance().collection("users_statuses")
                .document(userid)
                .set(add, SetOptions.merge())
                .addOnSuccessListener {
                    Log.d("Guardado de estado", "exitoso para el id: $userid")
                }
                .addOnFailureListener {
                    Log.d("Guardado de estado", "error: $it")
                }
        }
    }
}