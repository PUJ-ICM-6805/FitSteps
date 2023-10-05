package com.example.fitsteps.authentication

import androidx.lifecycle.ViewModel

class NewUserViewModel : ViewModel() {
    var email: String = ""
    var name: String = ""
    var password: String = ""
    var birthDate: String = ""
    var gender: String = ""
    var weight: Float = 0.0f
    var height: Float = 0.0f
    var experience: String = ""

}