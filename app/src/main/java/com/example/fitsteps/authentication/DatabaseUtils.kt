package com.example.fitsteps.authentication

import android.content.ContentValues.TAG
import android.net.Uri
import android.util.Log
import androidx.compose.runtime.MutableState
import com.example.fitsteps.screens.body.firebaseMeasuresData.Measure
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.tasks.await

class DatabaseUtils {
    suspend fun getUserDataByUID(uid: String): User? {
        val database = FirebaseFirestore.getInstance()
        val usersRef = database.collection("users")
        var user: User? = null

        try {
            val querySnapshot = usersRef.whereEqualTo("userId", uid).get().await()
            if (!querySnapshot.isEmpty) {
                for (document in querySnapshot.documents) {
                    user = document.toObject(User::class.java)
                    user?.document = document.id
                    break
                }
            }
        } catch (e: Exception) {
            Log.d("DatabaseUtils", "Error getting user by uid: ${e.message}")
        }

        return user
    }

    suspend fun getMeasureDataByUID(uid: String): Measure? {
        val database = FirebaseFirestore.getInstance()
        val measuresRef = database.collection("users").document(uid).collection("measures")
        var measure: Measure? = null

        try {
            val querySnapshot = measuresRef.get().await()
            if (!querySnapshot.isEmpty) {
                for (document in querySnapshot.documents) {
                    measure = document.toObject(Measure::class.java)
                    measure?.document = document.id
                    break
                }
            }
        } catch (e: Exception) {
            Log.d("DatabaseUtils", "Error getting measure by uid: ${e.message}")
        }

        return measure
    }

    fun updateUserAvatar(selectedImageUri: Uri, uid: String, usuario: MutableState<User>) {
        try {
            val storage = FirebaseStorage.getInstance()
            val downloadUrl = storage.reference.child("images").child(uid).putFile(selectedImageUri)
                .continueWithTask { task ->
                    if (!task.isSuccessful) {
                        task.exception?.let {
                            throw it
                        }
                    }
                    Log.d("DatabaseUtils", "Image uploaded successfully")
                    storage.reference.child("images").child(uid).downloadUrl
                }.addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val downloadUri = task.result
                        val database = FirebaseFirestore.getInstance()
                        val usersRef = database.collection("users")
                        usersRef.document(usuario.value.document).update("avatar", downloadUri.toString())
                            .addOnCompleteListener { updateTask ->
                                if (updateTask.isSuccessful) {
                                    // Actualiza el valor de avatarUrl en el estado de Compose
                                    usuario.value.avatar = downloadUri.toString()
                                    Log.d(TAG, "Success updating user avatar: ${usuario.value.avatar}")
                                }
                            }
                    }
                }
        } catch (e: Exception) {
            Log.d(TAG, "Error updating user avatar: ${e.message}")
        }
    }
}