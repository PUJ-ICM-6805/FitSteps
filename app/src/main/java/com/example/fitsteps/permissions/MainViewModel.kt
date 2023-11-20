package com.example.fitsteps.permissions

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class MainViewModel: ViewModel() {
    val visiblePermissionDialogQueue = mutableStateListOf<String>()

    private val _isContactsPermissionGranted = mutableStateOf(false)
    val isContactsPermissionGranted: MutableState<Boolean>
        get() = _isContactsPermissionGranted

    private val _isPhonePermissionGranted = mutableStateOf(false)
    val isPhonePermissionGranted: MutableState<Boolean>
        get() = _isPhonePermissionGranted

    fun dismissDialog() {
        visiblePermissionDialogQueue.removeFirst()
    }
    fun onPermissionResult(permission: String, isGranted: Boolean) {
        if (permission == android.Manifest.permission.READ_CONTACTS) {
            _isContactsPermissionGranted.value = isGranted
            if (!isGranted) {
                visiblePermissionDialogQueue.add(permission)
            }
        }

        if (permission == android.Manifest.permission.READ_PHONE_STATE) {
            _isPhonePermissionGranted.value = isGranted
            if (!isGranted) {
                visiblePermissionDialogQueue.add(permission)
            }
        }
        // Add handling for other permissions if needed
    }
}