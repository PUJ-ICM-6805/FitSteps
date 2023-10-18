package com.example.fitsteps.authentication

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
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
                    break
                }
            }
        } catch (e: Exception) {
            Log.d("DatabaseUtils", "Error getting user by uid: ${e.message}")
        }

        return user
    }
}