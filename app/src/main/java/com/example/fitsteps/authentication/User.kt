package com.example.fitsteps.authentication

data class User(
    var user_name: String,
    var user_birth_date: String,
    var gender: String,
    var weight: Float,
    var height: Float,
    var experience: String,
    var avatar: String = "",
    var userId: String,
    var document: String = "",
    var phoneNumber: String = "",
    var active: Boolean = false
) {
    constructor(): this(
        user_name = "",
        user_birth_date = "",
        gender = "",
        weight = 0.0f,
        height = 0.0f,
        experience = "",
        avatar = "",
        userId = ""
    )

    fun toMap(): MutableMap<String, Any> {
        return mutableMapOf(
            "user_name" to this.user_name,
            "user_birth_date" to this.user_birth_date,
            "gender" to this.gender,
            "weight" to this.weight,
            "height" to this.height,
            "experience" to this.experience,
            "avatar" to this.avatar,
            "userId" to this.userId
        )
    }
}

