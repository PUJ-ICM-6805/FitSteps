package com.example.fitsteps

import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import android.Manifest
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import android.util.Log
import androidx.activity.compose.setContent
import androidx.core.content.ContextCompat
import androidx.navigation.compose.rememberNavController
import com.example.fitsteps.navigation.SetupNavGraph
import com.example.fitsteps.ui.theme.FitStepsTheme
import com.google.firebase.auth.FirebaseAuth
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    private lateinit var mAuth: FirebaseAuth
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var locationPermissionsGranted by remember {
                mutableStateOf(
                    areLocationPermissionsAlreadyGranted()
                )
            }
            var shouldShowPermissionRationale by remember {
                mutableStateOf(
                    shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_COARSE_LOCATION)
                )
            }

            var shouldDirectUserToApplicationSettings by remember {
                mutableStateOf(false)
            }

            var currentPermissionsStatus by remember {
                mutableStateOf(
                    decideCurrentPermissionStatus(
                        locationPermissionsGranted,
                        shouldShowPermissionRationale
                    )
                )
            }

            val locationPermissions = arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION,
            )

            val locationPermissionLauncher = rememberLauncherForActivityResult(
                contract = ActivityResultContracts.RequestMultiplePermissions(),
                onResult = { permissions ->
                    locationPermissionsGranted =
                        permissions.values.reduce { acc, isPermissionGranted ->
                            acc && isPermissionGranted
                        }

                    if (!locationPermissionsGranted) {
                        shouldShowPermissionRationale =
                            shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_COARSE_LOCATION)
                    }
                    shouldDirectUserToApplicationSettings =
                        !shouldShowPermissionRationale && !locationPermissionsGranted
                    currentPermissionsStatus = decideCurrentPermissionStatus(
                        locationPermissionsGranted,
                        shouldShowPermissionRationale
                    )
                })

            val lifecycleOwner = LocalLifecycleOwner.current
            DisposableEffect(key1 = lifecycleOwner, effect = {
                val observer = LifecycleEventObserver { _, event ->
                    if (event == Lifecycle.Event.ON_RESUME &&
                        !locationPermissionsGranted &&
                        !shouldShowPermissionRationale
                    ) {
                        locationPermissionLauncher.launch(locationPermissions)
                    }
                    if(event == Lifecycle.Event.ON_RESUME){
                        mAuth = FirebaseAuth.getInstance()
                        val add = HashMap<String, Any>()
                        if(mAuth.currentUser != null){
                            val userid = mAuth.currentUser!!.uid
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
                    if(event == Lifecycle.Event.ON_CREATE){
                        //ponemos en true el estado del usuario
                        mAuth = FirebaseAuth.getInstance()
                        val add = HashMap<String, Any>()
                        if(mAuth.currentUser != null){
                            val userid = mAuth.currentUser!!.uid
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
                    if(event == Lifecycle.Event.ON_STOP){
                        //ponemos en false el estado del usuario
                        mAuth = FirebaseAuth.getInstance()
                        val add = HashMap<String, Any>()
                        if(mAuth.currentUser != null){
                            val userid = mAuth.currentUser!!.uid
                            add["online"] = false
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
                lifecycleOwner.lifecycle.addObserver(observer)
                onDispose {
                    lifecycleOwner.lifecycle.removeObserver(observer)
                }
            }
            )

            val scope = rememberCoroutineScope()
            val snackbarHostState = remember { SnackbarHostState() }

            FitStepsTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Scaffold(snackbarHost = {
                        SnackbarHost(hostState = snackbarHostState)
                    }) { contentPadding ->
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                modifier = Modifier
                                    .padding(contentPadding)
                                    .fillMaxWidth(),
                                text = "Location Permissions",
                                textAlign = TextAlign.Center
                            )
                            Spacer(modifier = Modifier.padding(20.dp))
                            Text(
                                modifier = Modifier
                                    .padding(contentPadding)
                                    .fillMaxWidth(),
                                text = "Current Permission Status: $currentPermissionsStatus",
                                textAlign = TextAlign.Center,
                                fontWeight = FontWeight.Bold
                            )
                        }
                        if (shouldShowPermissionRationale) {
                            LaunchedEffect(Unit) {
                                scope.launch {
                                    val userAction = snackbarHostState.showSnackbar(
                                        message = "Please authorize location permissions",
                                        actionLabel = "Approve",
                                        duration = SnackbarDuration.Indefinite,
                                        withDismissAction = true
                                    )
                                    when (userAction) {
                                        SnackbarResult.ActionPerformed -> {
                                            shouldShowPermissionRationale = false
                                            locationPermissionLauncher.launch(locationPermissions)
                                        }

                                        SnackbarResult.Dismissed -> {
                                            shouldShowPermissionRationale = false
                                        }
                                    }
                                }
                            }
                        }
                        if (shouldDirectUserToApplicationSettings) {
                            openApplicationSettings()
                        }
                    }
                }
                val navController = rememberNavController()
                SetupNavGraph(navController)
            }
        }
    }

    private fun areLocationPermissionsAlreadyGranted(): Boolean {
        return ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
    }

    private fun openApplicationSettings() {
        Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.fromParts("package", packageName, null)).also {
            startActivity(it)
        }
    }

    private fun decideCurrentPermissionStatus(locationPermissionsGranted: Boolean,
                                              shouldShowPermissionRationale: Boolean): String {
        return if (locationPermissionsGranted) "Granted"
        else if (shouldShowPermissionRationale) "Rejected"
        else "Denied"
    }
}
