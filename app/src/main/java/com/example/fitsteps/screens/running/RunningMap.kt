package com.example.fitsteps.screens.running

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Looper
import android.util.Log
import android.widget.Toast
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.fitsteps.R
import com.example.fitsteps.ui.theme.Red
import com.google.accompanist.permissions.*
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationAvailability
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.Polyline
import com.google.maps.android.compose.currentCameraPositionState
import com.google.maps.android.compose.rememberCameraPositionState
import java.util.*

@SuppressLint("PermissionLaunchedDuringComposition")
@OptIn(ExperimentalMaterialApi::class, ExperimentalPermissionsApi::class)
@Composable
fun RunningMap(navController: NavHostController) {
    val state: Boolean = true; //representa el estado de los dos últimos iconos
    var isMenuOpen by remember { mutableStateOf(false) }
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
                    }
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
                    SwipeableCardDemo(state = true)
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


@Composable
fun SwipeableCardDemo(
    modifier: Modifier = Modifier,
    state: Boolean,
    km: String = "0,00",
    time: String = "00:00:00",
    kcal: String = "0",
    onStart: () -> Unit = {},
) {
    var expanded by remember { mutableStateOf(false) }

    Card(
        modifier = modifier
            .padding(top = 5.dp)
            .fillMaxWidth()
            .wrapContentHeight(),
        elevation = 4.dp,
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
                            text = time,
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
                            text = kcal,
                            style = TextStyle(
                                fontSize = 30.sp,
                                fontFamily = FontFamily(Font(R.font.poppinsmedium)),
                                color = Color(0xFFF4F4F4),
                                textAlign = TextAlign.Center
                            )
                        )
                        Text(
                            text = stringResource(id = R.string.kcal),
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
                        .height(100.dp)
                    ,
                    horizontalArrangement = Arrangement.SpaceEvenly,
                ) {
                    if (!state) {
                        Image(
                            painter = painterResource(id = R.drawable.pause),
                            contentDescription = "pause",
                            modifier = Modifier
                                .fillMaxHeight()
                                .clickable {

                                },
                            contentScale = ContentScale.FillHeight
                        )
                    } else {
                        Image(
                            painter = painterResource(id = R.drawable.go),
                            contentDescription = "go",
                            modifier = Modifier.fillMaxHeight(),
                            contentScale = ContentScale.FillHeight
                        )
                        Image(
                            painter = painterResource(id = R.drawable.definitivestop),
                            contentDescription = "pause",
                            modifier = Modifier.fillMaxHeight(),
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
) {
    val context = LocalContext.current
    val route = remember { mutableListOf<LatLng>() }
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
                route.add(currentLocation)
                Log.d("ruta", route.toString())
            }
        }
    }
    startLocationUpdates(locationCallback, fusedLocationClient)


    val bucharest = LatLng(44.43, 26.09)
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
    locationCallback?.let {
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



@Composable
@Preview
fun RunningMapPreview() {
    RunningMap(navController = rememberNavController())
}