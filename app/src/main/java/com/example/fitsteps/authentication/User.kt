package com.example.fitsteps.authentication

import java.util.Date

data class User(
    val name: String,
    val birthDate: String,
    val gender: String,
    val weight: Float,
    val height: Float,
    val experience: String,
    val avatarUrl: String = "",
    val userId: String,
) {
    fun toMap(): MutableMap<String, Any> {
        return mutableMapOf(
            "user_name" to this.name,
            "user_birth_date" to this.birthDate,
            "gender" to this.gender,
            "weight" to this.weight,
            "height" to this.height,
            "experience" to this.experience,
            "avatar" to this.avatarUrl,
            "userId" to this.userId
        )
    }
}

