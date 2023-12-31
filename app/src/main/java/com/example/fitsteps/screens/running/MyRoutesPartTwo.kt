package com.example.fitsteps.screens.running

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.fitsteps.R
import com.example.fitsteps.firebaseData.firebaseRunningData.RunningViewModel
import com.example.fitsteps.screens.HamburgersDropList
import com.example.fitsteps.ui.theme.Blue
import com.example.fitsteps.ui.theme.DarkBlue
import com.example.fitsteps.ui.theme.LightBlue
import com.example.fitsteps.ui.theme.White
import com.example.fitsteps.ui.theme.customFontFamily
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun MyRoutesPartTwo(
    navController: NavHostController,
    rootNavController: NavHostController,
    runningViewModel: RunningViewModel = RunningViewModel(),
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
) {
    val inputFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    val outputFormat = SimpleDateFormat("MMMM d, yyyy", Locale("es", "ES"))
    var routeDate = remember { mutableStateOf("") }
    DoInLifeCycle(
        lifecycleOwner = lifecycleOwner,
        onCreate = {
            var date = inputFormat.parse(runningViewModel.actualRoute.date)
            var formattedDate = outputFormat.format(date)
            date = inputFormat.parse(runningViewModel.actualRoute.date)!!
            formattedDate = outputFormat.format(date)
            routeDate.value = formattedDate.substring(0, 1).toUpperCase() + formattedDate.substring(1)
        }
    )
    //val date = inputFormat.parse(runningViewModel.ActualRoute.date)
    //var formattedDate = outputFormat.format(date)

    LazyColumn(
    ) {
        item {
            Spacer(modifier = Modifier.height(20.dp))
        }
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(30.dp),
                contentAlignment = Alignment.CenterStart,
            ) {
                HamburgersDropList(
                    navController = navController,
                    rootNavController = rootNavController
                )
            }
        }
        item {
            Spacer(modifier = Modifier.height(40.dp))
        }
        item {
            Box(
                contentAlignment = Alignment.BottomStart, modifier = Modifier
                    .padding(start = 20.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.myroutes),
                    modifier = Modifier.padding(vertical = 0.dp),
                    style = TextStyle(
                        fontFamily = customFontFamily,
                        fontSize = 30.sp,
                        fontStyle = FontStyle.Normal,
                        color = DarkBlue
                    )
                )
            }
        }
        item {
            Box(
                contentAlignment = Alignment.BottomStart, modifier = Modifier
                    .padding(start = 20.dp)
            ) {
                Text(
                    text = runningViewModel.actualRoute.date,
                    modifier = Modifier.padding(vertical = 0.dp),
                    style = TextStyle(
                        fontFamily = FontFamily(Font(R.font.poppinsregular)),
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 30.sp,
                        fontStyle = FontStyle.Normal,
                        color = DarkBlue
                    )
                )
            }
        }
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center,
            ) {
                Navigation4(navController = navController)

            }
        }
        item {
            Spacer(modifier = Modifier.height(10.dp))
        }
        item {
            androidx.compose.material.Card(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(15.dp),
                shape = RoundedCornerShape(15.dp),
                backgroundColor = Color.White,
            )
            {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    Row() {
                        Box(
                            contentAlignment = Alignment.Center, modifier = Modifier
                                .fillMaxWidth()
                        ) {
                            Text(
                                text = "${routeDate.value}, ${runningViewModel.actualRoute.hour}",
                                modifier = Modifier
                                    .padding(horizontal = 15.dp, vertical = 15.dp),
                                style = TextStyle(
                                    fontFamily = FontFamily(Font(R.font.poppinsregular)),
                                    fontWeight = FontWeight.Thin,
                                    fontSize = 16.sp,
                                    fontStyle = FontStyle.Normal,
                                    color = Color(0xFF51778A)
                                )
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(8.dp))

                    Box(
                        contentAlignment = Alignment.TopCenter,
                        modifier = Modifier
                            .fillMaxSize()
                    ) {
                        Text(
                            text = "${runningViewModel.actualRoute.distance} km",
                            style = TextStyle(
                                fontSize = 45.sp,
                                fontFamily = FontFamily(Font(R.font.poppinssemibold)),
                                fontWeight = FontWeight(500),
                                color = DarkBlue,
                            ),
                            modifier = Modifier
                                .align(Alignment.TopCenter)
                        )
                    }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.reloj), // Reemplaza con tu recurso de icono
                            contentDescription = "Reloj",
                            modifier = Modifier
                                .padding(0.05.dp)
                                .width(20.dp)
                                .height(22.6087.dp)
                                .align(CenterVertically), // Ajusta el tamaño del icono según tus necesidades
                            tint = Color.Unspecified // Ajusta el color del icono
                        )
                        Column(
                            horizontalAlignment = Alignment.Start,
                            modifier = Modifier
                                .padding(start = 16.dp, end = 32.dp)
                                .weight(1f) // Use weight to distribute sstribute space evenly
                        ) {
                            Text(
                                text = stringResource(id = R.string.duration),
                                style = TextStyle(
                                    fontSize = 11.sp,
                                    fontFamily = FontFamily(Font(R.font.poppinsmedium)),
                                    fontWeight = FontWeight(500),
                                    color = Color(0xFF51778A),
                                    textAlign = TextAlign.Start
                                )
                            )
                            Text(
                                text = runningViewModel.actualRoute.time,
                                style = TextStyle(
                                    fontSize = 14.sp,
                                    fontFamily = FontFamily(Font(R.font.poppinsmedium)),
                                    fontWeight = FontWeight(500),
                                    color = DarkBlue,
                                    textAlign = TextAlign.Start
                                )
                            )
                        }
                        Icon(
                            painter = painterResource(id = R.drawable.fire), // Reemplaza con tu recurso de icono
                            contentDescription = "Fire",
                            modifier = Modifier
                                .padding(0.05.dp)
                                .width(20.dp)
                                .height(22.6087.dp)
                                .align(CenterVertically), // Ajusta el tamaño del icono según tus necesidades
                            tint = Color.Unspecified // Ajusta el color del icono
                        )

                        Column(
                            horizontalAlignment = Alignment.Start,
                            modifier = Modifier
                                .padding(start = 16.dp, end = 32.dp)
                                .weight(1f) // Use weight to distribute s
                        ) {
                            Text(
                                text = stringResource(id = R.string.calories),
                                style = TextStyle(
                                    fontSize = 11.sp,
                                    fontFamily = FontFamily(Font(R.font.poppinsmedium)),
                                    fontWeight = FontWeight(500),
                                    color = Color(0xFF51778A),
                                    textAlign = TextAlign.Start
                                )
                            )
                            Text(
                                text = ((if(calcularVelocidad(
                                                runningViewModel.actualRoute.distance.toDouble(),
                                                runningViewModel.actualRoute.time)!! < 6f
                                            ) 0.73 else 1.03) *
                                        runningViewModel.user.weight *
                                        runningViewModel.actualRoute.distance.toDouble()
                                        ).toInt().toString(),
                                style = TextStyle(
                                    fontSize = 16.sp,
                                    fontFamily = FontFamily(Font(R.font.poppinsmedium)),
                                    fontWeight = FontWeight(500),
                                    color = DarkBlue,
                                    textAlign = TextAlign.Start
                                )
                            )
                        }
                    }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.steps), // Reemplaza con tu recurso de icono
                            contentDescription = "Pasos",
                            modifier = Modifier
                                .padding(0.05.dp)
                                .width(20.dp)
                                .height(22.6087.dp)
                                .align(CenterVertically), // Ajusta el tamaño del icono según tus necesidades
                            tint = Color.Unspecified // Ajusta el color del icono
                        )
                        Column(
                            horizontalAlignment = Alignment.Start,
                            modifier = Modifier
                                .padding(start = 16.dp, end = 32.dp)
                                .weight(1f) // Use weight to distribute space evenly
                        ) {
                            Text(
                                text = stringResource(id = R.string.pasos),
                                style = TextStyle(
                                    fontSize = 11.sp,
                                    fontFamily = FontFamily(Font(R.font.poppinsmedium)),
                                    fontWeight = FontWeight(500),
                                    color = Color(0xFF51778A),
                                    textAlign = TextAlign.Start
                                )
                            )
                            Text(
                                text = runningViewModel.actualRoute.steps.toString(),
                                style = TextStyle(
                                    fontSize = 16.sp,
                                    fontFamily = FontFamily(Font(R.font.poppinsmedium)),
                                    fontWeight = FontWeight(500),
                                    color = DarkBlue,
                                    textAlign = TextAlign.Start
                                )
                            )
                        }
                        Icon(
                            painter = painterResource(id = R.drawable.clocktwo), // Reemplaza con tu recurso de icono
                            contentDescription = "Reloj",
                            modifier = Modifier
                                .padding(0.05.dp)
                                .width(20.dp)
                                .height(22.6087.dp)
                                .align(CenterVertically), // Ajusta el tamaño del icono según tus necesidades
                            tint = Color.Unspecified // Ajusta el color del icono
                        )
                        Column(
                            horizontalAlignment = Alignment.Start,
                            modifier = Modifier
                                .padding(start = 16.dp, end = 32.dp)
                                .weight(1f) // Use weight to distribute senly
                        ) {
                            Text(
                                text = stringResource(id = R.string.speed),
                                style = TextStyle(
                                    fontSize = 11.sp,
                                    fontFamily = FontFamily(Font(R.font.poppinsmedium)),
                                    fontWeight = FontWeight(500),
                                    color = Color(0xFF51778A),
                                    textAlign = TextAlign.Start
                                )
                            )
                            Text(
                                text = String.format("%.2f", (calcularVelocidad(
                                    runningViewModel.actualRoute.distance.toDouble(),
                                    runningViewModel.actualRoute.time)!!)),
                                style = TextStyle(
                                    fontSize = 14.sp,
                                    fontFamily = FontFamily(Font(R.font.poppinsmedium)),
                                    fontWeight = FontWeight(500),
                                    color = DarkBlue,
                                    textAlign = TextAlign.Start
                                )
                            )
                        }
                    }


                    /*Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {

                        Icon(
                            painter = painterResource(id = R.drawable.heart), // Reemplaza con tu recurso de icono
                            contentDescription = "Corazon",
                            modifier = Modifier
                                .padding(0.05.dp)
                                .width(20.dp)
                                .height(22.6087.dp)
                                .align(CenterVertically), // Ajusta el tamaño del icono según tus necesidades
                            tint = Color.Unspecified // Ajusta el color del icono
                        )
                        Column(
                            horizontalAlignment = Alignment.Start,
                            modifier = Modifier
                                .padding(start = 16.dp, end = 32.dp)
                                .weight(1f) // Use weight to distribute space evenly
                        ) {
                            Text(
                                text = stringResource(id = R.string.frecuencia),
                                style = TextStyle(
                                    fontSize = 11.sp,
                                    fontFamily = FontFamily(Font(R.font.poppinsmedium)),
                                    fontWeight = FontWeight(500),
                                    color = Color(0xFF51778A),
                                    textAlign = TextAlign.Start
                                )
                            )
                            Text(
                                text = "176 bpm",
                                style = TextStyle(
                                    fontSize = 14.sp,
                                    fontFamily = FontFamily(Font(R.font.poppinsmedium)),
                                    fontWeight = FontWeight(500),
                                    color = DarkBlue,
                                    textAlign = TextAlign.Start
                                )
                            )
                        }
                    }*/
                    Spacer(modifier = Modifier.height(20.dp))
                }

            }
        }
        item {
            Spacer(modifier = Modifier.height(80.dp))
        }
    }


}

fun calcularVelocidad(distanciaKm: Double, tiempoStr: String): Double? {
    // Paso 1: Convierte el tiempo a horas
    val tiempoPartes = tiempoStr.split(":")
    if (tiempoPartes.size != 3) {
        return 0.0
    }
    val horas = tiempoPartes[0].toDouble()
    val minutos = tiempoPartes[1].toDouble()
    val segundos = tiempoPartes[2].toDouble()
    val tiempoEnHoras = horas + minutos / 60 + segundos / 3600
    if (tiempoEnHoras > 0) {
        Log.d("Tiempo en horas", tiempoEnHoras.toString())
        Log.d("Distancia en km", distanciaKm.toString())
        return distanciaKm / tiempoEnHoras
    } else {
        return 0.0
    }
}


@Composable
fun Navigation4(navController: NavController) {
    Row(
        modifier = Modifier
            .clip(shape = RoundedCornerShape(8.dp))
            .background(LightBlue)
            .width(350.dp)
            .height(35.dp),
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f)
                .clip(shape = RoundedCornerShape(8.dp))
                .clickable {
                    navController.popBackStack()
                },
            contentAlignment = Alignment.Center

        ) {
            Text(
                text = stringResource(id = R.string.myroutes),
                style = TextStyle(
                    fontFamily = customFontFamily,
                    fontWeight = FontWeight.Medium,
                    fontSize = 15.sp,
                    fontStyle = FontStyle.Normal,
                    color = Blue,
                )
            )
        }
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Blue)
                .weight(1f)
                .clip(shape = RoundedCornerShape(8.dp)),
            contentAlignment = Alignment.Center

        ) {
            Text(
                text = stringResource(id = R.string.details),
                style = TextStyle(
                    fontFamily = customFontFamily,
                    fontWeight = FontWeight.Medium,
                    fontSize = 15.sp,
                    fontStyle = FontStyle.Normal,
                    color = White,
                )
            )

        }
    }
}

@Composable
@Preview
fun MyRoutesPartTwoPreview() {
    MyRoutesPartTwo(
        navController = rememberNavController(),
        rootNavController = rememberNavController()
    )
}