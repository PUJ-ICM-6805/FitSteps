package com.example.fitsteps.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.gestures.detectVerticalDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material3.CardElevation
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.fitsteps.R
import com.example.fitsteps.ui.theme.DarkBlue
import com.example.fitsteps.ui.theme.FitStepsTheme
import com.example.fitsteps.ui.theme.LightBlue
import com.example.fitsteps.ui.theme.White

@Composable
fun RunningMap() {
    val state: Boolean = true; //representa el estado de los dos últimos iconos
    FitStepsTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            var expanded: Boolean by remember { mutableStateOf(false) }

            // Mapa
            Box(
                modifier = Modifier
                    .fillMaxWidth()

            ) {
                ImageCard(
                    painter = painterResource(id = R.drawable.expanded_map),
                    contentDescription = "hola",
                    title = "",
                    type = "Mapa",
                    modifier = Modifier
                        .fillMaxWidth(1f)
                        .fillMaxHeight(0.5f),
                )
     
            }

            // SwipeableCardDemo
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .background(Color.Transparent)
                    .align(Alignment.BottomCenter)

            ) {
                SwipeableCardDemo(
                    modifier = Modifier
                        .fillMaxWidth(1f)
                        .fillMaxHeight(0.6f),
                    state
                )
            }
        }
    }
}


@Composable
fun SwipeableCardDemo(modifier: Modifier = Modifier, state: Boolean) {
    var expanded by remember { mutableStateOf(false) }

    Card(
        modifier = modifier
            .padding(top = 5.dp)
            .fillMaxWidth()
            .height((if (expanded) 600 else 150).dp)
            .pointerInput(Unit) {
                detectTransformGestures { _, pan, _, _ ->
                    if (pan != null) {
                        expanded = pan.y < 0
                    }
                }
            },
        elevation = 4.dp
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(Color(0xFF000000), Color(0xFF2D3142))
                    )
                )
        ) {
            Box(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(top = 16.dp)
                    .clickable { expanded = !expanded }
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.swipeup),
                    contentDescription = "hola",
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                        .offset(y = 5.dp) // Ajusta el valor de "y" según tu preferencia
                        .width(35.dp)
                        .height(3.99999.dp),
                    tint = Color(0xFFF4F4F4)
                )

                Text(
                    text = "0,00 Km",
                    style = TextStyle(
                        fontSize = 70.sp,
                        fontFamily = FontFamily(Font(R.font.poppinssemibold)),
                        fontWeight = FontWeight(500),
                        color = Color(0xFFF4F4F4),
                        textAlign = TextAlign.Center
                    ),
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(top = 30.dp)
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .padding(end = 32.dp)
                        .weight(1f) // Use weight to distribute space evenly
                ) {
                    Text(
                        text = "00:00:02",
                        style = TextStyle(
                            fontSize = 30.sp,
                            fontFamily = FontFamily(Font(R.font.poppinsmedium)),
                            fontWeight = FontWeight(500),
                            color = Color(0xFFF4F4F4),
                            textAlign = TextAlign.Center
                        )
                    )
                    Text(
                        text = "Duración",
                        style = TextStyle(
                            fontSize = 16.sp,
                            fontFamily = FontFamily(Font(R.font.poppinsmedium)),
                            fontWeight = FontWeight(500),
                            color = Color(0xFFF4F4F4),
                            textAlign = TextAlign.Center
                        )
                    )
                }

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .weight(1f) // Use weight to distribute space evenly
                ) {
                    Text(
                        text = "0",
                        style = TextStyle(
                            fontSize = 30.sp,
                            fontFamily = FontFamily(Font(R.font.poppinsmedium)),
                            fontWeight = FontWeight(500),
                            color = Color(0xFFF4F4F4),
                            textAlign = TextAlign.Center
                        )
                    )
                    Text(
                        text = "kcal",
                        style = TextStyle(
                            fontSize = 16.sp,
                            fontFamily = FontFamily(Font(R.font.poppinsmedium)),
                            fontWeight = FontWeight(500),
                            color = Color(0xFFF4F4F4),
                            textAlign = TextAlign.Center
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
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .padding(end = 32.dp)
                        .weight(1f) // Use weight to distribute space evenly
                ) {
                    Text(
                        text = "--",
                        style = TextStyle(
                            fontSize = 30.sp,
                            fontFamily = FontFamily(Font(R.font.poppinsmedium)),
                            fontWeight = FontWeight(500),
                            color = Color(0xFFF4F4F4),
                            textAlign = TextAlign.Center
                        )
                    )
                    Text(
                        text = "Ritmo",
                        style = TextStyle(
                            fontSize = 16.sp,
                            fontFamily = FontFamily(Font(R.font.poppinsmedium)),
                            fontWeight = FontWeight(500),
                            color = Color(0xFFF4F4F4),
                            textAlign = TextAlign.Center
                        )
                    )
                }

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .weight(1f) // Use weight to distribute space evenly
                ) {
                    Text(
                        text = "--",
                        style = TextStyle(
                            fontSize = 30.sp,
                            fontFamily = FontFamily(Font(R.font.poppinsmedium)),
                            fontWeight = FontWeight(500),
                            color = Color(0xFFF4F4F4),
                            textAlign = TextAlign.Center
                        )
                    )
                    Text(
                        text = "Ritmo cardiaco",
                        style = TextStyle(
                            fontSize = 16.sp,
                            fontFamily = FontFamily(Font(R.font.poppinsmedium)),
                            fontWeight = FontWeight(500),
                            color = Color(0xFFF4F4F4),
                            textAlign = TextAlign.Center
                        )
                    )
                }
            }
            if (!state) {
                Icon(
                    painter = painterResource(id = R.drawable.pause),
                    contentDescription = "hola",
                    modifier = Modifier
                        .fillMaxSize(0.5f)
                        .align(Alignment.CenterHorizontally),

                    )
            } else {
                Row(
                    modifier = Modifier
                        .padding(10.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                ) {
                    val items = listOf(
                        "go", "stop"
                    )
                    items.forEach { item ->
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .weight(1f),
                            contentAlignment = Alignment.Center,
                        ) {
                            Icon(
                                painter = when (item) {
                                    "go" -> painterResource(id = R.drawable.go)
                                    else -> painterResource(id = R.drawable.definitivestop)
                                },
                                contentDescription = "", // Provide a meaningful description
                                modifier = Modifier
                                    .padding(horizontal = 2.dp, vertical = 5.dp)
                                    .size(160.dp),
                                tint = Color.Unspecified,
                            )
                        }
                    }
                }
            }
        }

    }
}

@Composable
@Preview
fun RunningMapPreview() {
    RunningMap()
}