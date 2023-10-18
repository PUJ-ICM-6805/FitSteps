package com.example.fitsteps.screens.running

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Build
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.fitsteps.R
import com.example.fitsteps.ui.theme.Red
import com.google.accompanist.permissions.*
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.Polyline
import com.google.maps.android.compose.rememberCameraPositionState
import kotlinx.coroutines.delay
import java.util.*
import kotlin.math.*



@Composable
fun DoInLifeCycle(
    lifecycleOwner : LifecycleOwner,
    onCreate: () -> Unit = {},
    onStart: () -> Unit = {},
    onResume: () -> Unit = {},
) {
    DisposableEffect(lifecycleOwner) {
        // Create an observer that triggers our remembered callbacks
        // for sending analytics events
        val observer = LifecycleEventObserver { _, event ->
            when(event) {
                Lifecycle.Event.ON_CREATE -> {
                    Log.d("lifecycle", "ON_CREATE")
                    onCreate()
                }
                Lifecycle.Event.ON_START -> {
                    Log.d("lifecycle", "ON_START")
                    onStart()
                }
                Lifecycle.Event.ON_STOP -> {
                    Log.d("lifecycle", "ON_STOP")
                }
                Lifecycle.Event.ON_RESUME -> {
                    Log.d("lifecycle", "ON_RESUME")
                    onResume()
                }
                Lifecycle.Event.ON_PAUSE -> {
                    Log.d("lifecycle", "ON_PAUSE")
                }
                Lifecycle.Event.ON_DESTROY -> {
                    Log.d("lifecycle", "ON_DESTROY")
                }
                Lifecycle.Event.ON_ANY -> {
                    Log.d("lifecycle", "ON_ANY")
                }
                else -> {
                    Log.i("OBSERVER", "Lifecycle: ${event.name}")
                }
            }
        }

        // Add the observer to the lifecycle
        lifecycleOwner.lifecycle.addObserver(observer)

        // When the effect leaves the Composition, remove the observer
        return@DisposableEffect onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }
}


class RunningMapViewModel: ViewModel() {
    val visiblePermissionDialogQueue = mutableStateListOf<String>()

    fun dismissDialog() {
        visiblePermissionDialogQueue.removeFirst()
    }

    fun onPermissionResult(
        permission: String,
        isGranted: Boolean
    ) {
        if(!isGranted && !visiblePermissionDialogQueue.contains(permission)) {
            visiblePermissionDialogQueue.add(permission)
        }
    }
}


class MySensorEventListener(val onChange: (Int) -> Unit) : SensorEventListener {
    override fun onSensorChanged(event: SensorEvent) {
        if (event.sensor.type == Sensor.TYPE_STEP_COUNTER) {
            val steps = event.values[0].toInt()
            onChange(steps)
            Log.d("steps", steps.toString())

        }
    }
    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
    }
}




@RequiresApi(Build.VERSION_CODES.Q)
@SuppressLint("PermissionLaunchedDuringComposition")
@OptIn(ExperimentalMaterialApi::class, ExperimentalPermissionsApi::class)
@Composable
fun RunningMap(
    navController: NavHostController,
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
) {
    var sensorManager = remember { mutableStateOf<SensorManager?>(null) }
    val viewModel = viewModel<RunningMapViewModel>()
    var totalSteps by remember { mutableStateOf(0) }
    var previousSteps by remember { mutableStateOf(0) }
    var currentSteps by remember { mutableStateOf(0) }
    var running = remember { mutableStateOf(false) }
    var paused = remember { mutableStateOf(true) }
    var finished by remember { mutableStateOf(false) }
    var route = remember { mutableListOf<LatLng?>(null) }
    var stepsReaded by remember { mutableStateOf(false) }
    var totalDistance by remember { mutableStateOf(0.0) }
    var time by remember { mutableStateOf(0L) }
    var isMapLoaded by remember {
        mutableStateOf(false)
    }
    var showToast by remember {
        mutableStateOf(true)
    }
    val context = LocalContext.current
    val permissions = arrayOf(
        Manifest.permission.ACCESS_COARSE_LOCATION,
        Manifest.permission.ACCESS_FINE_LOCATION
    )
    val activityPermissionResultLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = {isGranted ->
            viewModel.onPermissionResult(
                permission = Manifest.permission.ACTIVITY_RECOGNITION,
                isGranted = isGranted
            )
        }
    )
    DoInLifeCycle(
        lifecycleOwner = lifecycleOwner,
        onCreate = {
            sensorManager.value = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
            //previousSteps = loadData(context)
            currentSteps = 0
            //saveData(previousSteps, context)
            //Log.d("previousSteps", previousSteps.toString())
        },
        onResume = {
            activityPermissionResultLauncher.launch(Manifest.permission.ACTIVITY_RECOGNITION)
            if (sensorManager.value != null) {
                val stepSensor = sensorManager.value!!.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)
                if (stepSensor == null) {
                    Toast.makeText(context, "No step counter sensor available", Toast.LENGTH_SHORT).show()
                } else {
                    val sensorEventListener = MySensorEventListener(
                        onChange = {
                            totalSteps = it
                            if(!stepsReaded){
                                previousSteps = totalSteps
                                stepsReaded = true
                            }
                            if(paused.value) {
                                previousSteps = totalSteps
                            }
                            currentSteps += totalSteps - previousSteps
                        }
                    )
                    sensorManager.value!!.registerListener(sensorEventListener, stepSensor, SensorManager.SENSOR_DELAY_UI)
                }
            } else {
                Toast.makeText(context, "SensorManager is null", Toast.LENGTH_SHORT).show()
            }
        },
    )
    if (permissions.all {
        ContextCompat.checkSelfPermission(
            context,
            it
        ) == PackageManager.PERMISSION_GRANTED
    }) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                GoogleMapView(
                    modifier = Modifier.fillMaxSize(),
                    onMapLoaded = {
                        isMapLoaded = true
                    },
                    finished = finished,
                    distanceListener = {
                        totalDistance = it
                        Log.d("distance", it.toString())
                    },
                    paused = paused,
                )
                if(!isMapLoaded) {
                    AnimatedVisibility(
                        modifier = Modifier.matchParentSize(),
                        visible = !isMapLoaded,
                        enter = EnterTransition.None,
                        exit = fadeOut()
                    ){
                        CircularProgressIndicator(
                            modifier = Modifier
                                .background(MaterialTheme.colorScheme.background)
                                .wrapContentSize()
                        )
                    }
                }
                Box(
                    modifier = Modifier.align(Alignment.BottomCenter)
                ) {
                    SwipeableCardDemo(
                        state = paused.value,
                        onStart = {
                            running.value = it
                            paused.value = !it
                            previousSteps = totalSteps
                        },
                        onFinish = {
                            finished = it
                            running.value = !it
                            paused.value = it
                            previousSteps = totalSteps
                            currentSteps = 0
                            //saveData(previousSteps, context)
                        },
                        onPause = {
                            paused.value = it
                            previousSteps = totalSteps
                        },
                        paused = paused,
                        running = running,
                        km = String.format("%.2f", totalDistance),
                        steps = currentSteps.toString(),
                    )
                }
            }
        }

    } else {
        navController.popBackStack("summary", false)
        if(showToast) {
            showToast = false
            Toast.makeText(context, "Location not available", Toast.LENGTH_LONG).show()
        }
        return
    }
}

private fun saveData(previousSteps: Int, context: Context) {
    val sharedPreferences = context.getSharedPreferences("steps", Context.MODE_PRIVATE)
    val editor = sharedPreferences.edit()
    editor.putInt("previousSteps", previousSteps)
    editor.apply()
}

private fun loadData(context: Context) : Int {
    val sharedPreferences = context.getSharedPreferences("steps", Context.MODE_PRIVATE)
    return sharedPreferences.getInt("previousSteps", 0)
}


@Composable
fun SwipeableCardDemo(
    modifier: Modifier = Modifier,
    state: Boolean,
    km: String = "0,00",
    steps: String = "0",
    onStart: (Boolean) -> Unit,
    onPause: (Boolean) -> Unit,
    onFinish: (Boolean) -> Unit,
    time: (Long) -> Unit = {},
    running: MutableState<Boolean>,
    paused: MutableState<Boolean>,
) {
    var expanded by remember { mutableStateOf(false) }
    var elapsedTime by remember { mutableStateOf(0L) }
    LaunchedEffect(key1 = elapsedTime, key2 = paused.value) {
        while (!paused.value) {
            delay(1000L)
            elapsedTime++
        }
    }
    Card(
        modifier = modifier
            .padding(top = 5.dp)
            .fillMaxWidth()
            .wrapContentHeight(),
        shape = RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp),
    ) {
        Column(
            modifier = Modifier
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(Color(0xFF000000), Color(0xFF2D3142))
                    )
                )
        ) {
            Column(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(top = 16.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.swipeup),
                    contentDescription = "",
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .offset(y = 5.dp)
                        .width(35.dp)
                        .height(3.99999.dp)
                        .clickable {
                            expanded = !expanded
                        },
                    tint = Color(0xFFF4F4F4)
                )

                Text(
                    text = "$km km",
                    style = TextStyle(
                        fontSize = 70.sp,
                        fontFamily = FontFamily(Font(R.font.poppinsmedium)),
                        color = Color(0xFFF4F4F4),
                        textAlign = TextAlign.Center
                    ),
                )
            }
            if(expanded) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .padding(end = 20.dp)
                    ) {
                        Text(
                            text = formatElapsedTime(elapsedTime),
                            style = TextStyle(
                                fontSize = 30.sp,
                                fontFamily = FontFamily(Font(R.font.poppinsmedium)),
                                color = Color(0xFFF4F4F4),
                                textAlign = TextAlign.Center
                            )
                        )
                        Text(
                            text = stringResource(R.string.duration),
                            style = TextStyle(
                                fontSize = 16.sp,
                                fontFamily = FontFamily(Font(R.font.poppinsmedium)),
                                color = Color(0xFFF4F4F4),
                                textAlign = TextAlign.Center
                            )
                        )
                    }

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        Text(
                            text = steps,
                            style = TextStyle(
                                fontSize = 30.sp,
                                fontFamily = FontFamily(Font(R.font.poppinsmedium)),
                                color = Color(0xFFF4F4F4),
                                textAlign = TextAlign.Center
                            )
                        )
                        Text(
                            text = stringResource(id = R.string.steps),
                            style = TextStyle(
                                fontSize = 16.sp,
                                fontFamily = FontFamily(Font(R.font.poppinsmedium)),
                                color = Color(0xFFF4F4F4),
                                textAlign = TextAlign.Center
                            )
                        )
                    }
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                ) {
                    if (!state) {
                        Image(
                            painter = painterResource(id = R.drawable.pause),
                            contentDescription = "pause",
                            modifier = Modifier
                                .fillMaxHeight()
                                .clickable {
                                    onPause(true)
                                },
                            contentScale = ContentScale.FillHeight
                        )
                    } else {
                        Image(
                            painter = painterResource(id = R.drawable.go),
                            contentDescription = "go",
                            modifier = Modifier
                                .fillMaxHeight()
                                .clickable {
                                    onStart(true)
                                },
                            contentScale = ContentScale.FillHeight
                        )
                        Image(
                            painter = painterResource(id = R.drawable.definitivestop),
                            contentDescription = "pause",
                            modifier = Modifier
                                .fillMaxHeight()
                                .clickable {
                                    onFinish(true)
                                }
                            ,
                            contentScale = ContentScale.FillHeight
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun GoogleMapView(
    modifier: Modifier = Modifier,
    onMapLoaded: () -> Unit = {},
    content: @Composable () -> Unit = {},
    paused: MutableState<Boolean>,
    finished: Boolean,
    distanceListener: (Double) -> Unit = {},
) {
    val context = LocalContext.current
    val route = remember { mutableListOf<LatLng>() }
    var totalDistance by remember { mutableStateOf(0.0) }
    var currentLocation by remember {
        mutableStateOf(LatLng(0.toDouble(), 0.toDouble()))
    }
    var cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(currentLocation, 17f)
    }
    var fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
    var locationCallback = object : LocationCallback() {
        override fun onLocationResult(p0: LocationResult) {
            for (lo in p0.locations) {
                // Update UI with location data
                currentLocation = LatLng(lo.latitude, lo.longitude)
                cameraPositionState.move(CameraUpdateFactory.newLatLng(currentLocation))
                if(!paused.value) {
                    route.add(currentLocation)
                    Log.d("ruta", route.toString())
                    if (route.size >= 2) {
                        totalDistance += haversineDistance(route[route.size - 2], currentLocation)
                        distanceListener(totalDistance)
                    }
                    distanceListener(totalDistance)
                }
            }
        }
    }
    startLocationUpdates(locationCallback, fusedLocationClient)
    GoogleMap (
        modifier = modifier,
        onMapLoaded = onMapLoaded,
        cameraPositionState = cameraPositionState,
        properties = MapProperties(
            mapStyleOptions = MapStyleOptions.loadRawResourceStyle(context, R.raw.map_style),
            isMyLocationEnabled = true,
        ),
        uiSettings = MapUiSettings(
            compassEnabled = true,
        )
    ) {
        Marker(
            state = MarkerState(position = currentLocation),
            title = "You",
            alpha = 0.0f,
        )
        Polyline(points = route.toList(), color = Red, width = 10f)
        content()
    }
}

@SuppressLint("MissingPermission")
private fun startLocationUpdates(locationCallback : LocationCallback, fusedLocationClient: FusedLocationProviderClient?) {
    locationCallback.let {
        val locationRequest = LocationRequest.create().apply {
            interval = 10000
            fastestInterval = 5000
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }
        fusedLocationClient?.requestLocationUpdates(
            locationRequest,
            it,
            Looper.getMainLooper()
        )
    }
}

private fun formatElapsedTime(time: Long): String {
    val seconds = time % 60
    val minutes = (time / 60) % 60
    val hours = (time / 60 / 60)
    return String.format("%02d:%02d:%02d", hours, minutes, seconds)
}

fun haversineDistance(coord1: LatLng, coord2: LatLng): Double {
    val earthRadius = 6371.0 // Radio de la Tierra en kilómetros

    val dLat = Math.toRadians(coord2.latitude - coord1.latitude)
    val dLon = Math.toRadians(coord2.longitude - coord1.longitude)

    val lat1 = Math.toRadians(coord1.latitude)
    val lat2 = Math.toRadians(coord2.latitude)

    val a = sin(dLat / 2).pow(2) + sin(dLon / 2).pow(2) * cos(lat1) * cos(lat2)
    val c = 2 * atan2(sqrt(a), sqrt(1 - a))

    return earthRadius * c
}


@RequiresApi(Build.VERSION_CODES.Q)
@Composable
@Preview
fun RunningMapPreview() {
    RunningMap(navController = rememberNavController())
}