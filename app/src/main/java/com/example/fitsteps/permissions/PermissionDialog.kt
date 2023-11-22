package com.example.fitsteps.permissions

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.AlertDialog
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun PermissionDialog(
    permissionTextProvider: PermissionTextProvider,
    isPermanentlyDeclined: Boolean,
    onDismiss: () -> Unit,
    onOkClick: () -> Unit,
    onGoToAppSettingsClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        buttons = {
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                Divider()
                Text(
                    text = if (isPermanentlyDeclined) {
                        "Grant permission"
                    } else {
                        "OK"
                    },
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            if (isPermanentlyDeclined) {
                                onGoToAppSettingsClick()
                            } else {
                                onOkClick()
                            }
                            // Dismiss the dialog after the button is clicked
                            onDismiss()
                        }
                        .padding(16.dp)
                )
            }
        },
        title = {
            Text(text = "Permission required")
        },
        text = {
            Text(text = permissionTextProvider.getDescription(
                isPermanentlyDeclined = isPermanentlyDeclined
            ))
        },
        modifier = modifier
    )
}

interface PermissionTextProvider{
    fun getDescription(isPermanentlyDeclined: Boolean) : String
}

class ContactsPermissionTextProvider : PermissionTextProvider {
    override fun getDescription(isPermanentlyDeclined: Boolean): String {
        return if (isPermanentlyDeclined) {
            "It looks like you have declined the contacts permission multiple times. " +
                    "Please grant access to your contacts in the app settings."
        } else {
            "Please grant access to your contacts in order to see your friends progress."
        }
    }
}

class LocationPermissionTextProvider : PermissionTextProvider {
    override fun getDescription(isPermanentlyDeclined: Boolean): String {
        return if (isPermanentlyDeclined) {
            "It looks like you have declined the location permission multiple times. " +
                    "Please grant access to your location in the app settings."
        } else {
            "Please grant access to your location in order to use the Running feature."
        }
    }
}

class CameraPermissionTextProvider : PermissionTextProvider {
    override fun getDescription(isPermanentlyDeclined: Boolean): String {
        return if (isPermanentlyDeclined) {
            "It looks like you have declined the camera permission multiple times. " +
                    "Please grant access to your camera in the app settings."
        } else {
            "Please grant access to your camera in order to take pictures of your progress."
        }
    }
}

class PhonePermissionTextProvider : PermissionTextProvider {
    override fun getDescription(isPermanentlyDeclined: Boolean): String {
        return if (isPermanentlyDeclined) {
            "It looks like you have declined the phone permission multiple times. " +
                    "Please grant access to your phone in the app settings."
        } else {
            "Please grant access to your phone in order to share your progress with your friends."
        }
    }
}