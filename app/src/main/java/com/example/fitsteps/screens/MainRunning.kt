package com.example.fitsteps.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.fitsteps.R
import com.example.fitsteps.ui.theme.DarkBlue
import com.example.fitsteps.ui.theme.White

@Composable
fun MainRunning() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(White),
        contentAlignment = Alignment.Center
    ) {
    }
    Column(
        verticalArrangement = Arrangement.SpaceEvenly, modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        val painterMap = painterResource(id = R.drawable.map)
        val description = "Mujer ejercitándose"
        val title = "Mujer sudando haciendo ejercicio"
        Icon(
            painterResource(id = R.drawable.configuration),
            contentDescription = "Running",
            tint = DarkBlue,
            modifier = Modifier
                .align(Alignment.End)
                .padding(top = 25.dp, end = 25.dp)
                .size(30.dp))
        Text(
            text = "Running",
            color = DarkBlue,
            fontWeight = FontWeight.Bold,
            fontSize = 30.sp,
            modifier = Modifier.padding(start = 30.dp)
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .wrapContentWidth(align = Alignment.CenterHorizontally)
        ) {
            ImageCard(
                painter = painterMap,
                contentDescription = description,
                title = "",
                modifier = Modifier
                    .width(333.dp)
                    .height(300.dp),
                type = "Mapa"
            )
            Text(
                text = "INICIAR",
                style = TextStyle(
                    fontSize = 20.sp,
                    fontFamily = FontFamily(Font(R.font.poppinsregular)),
                    fontWeight = FontWeight(500),
                    color = Color(0xFFF4F4F4),
                    textAlign = TextAlign.Center
                ),
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(16.dp, 32.dp, 16.dp, 32.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(Color(0xFFE52037))
                    .size(200.dp, 50.dp)
                    .wrapContentWidth(align = Alignment.CenterHorizontally)
                    .clickable {
                        // Acción al hacer clic en INICIAR
                    }
            )
        }

        Text(
            text = "Rutinas",
            color = DarkBlue,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
                    modifier = Modifier.padding(start=45.dp)
        )
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 30.dp)
        ) {
            items(5000) {
                val painter = painterResource(id = R.drawable.woman)
                val description = "Mujer ejercitándose"
                val title = "Mujer sudando haciendo ejercicio"

                Box() {
                    ImageCard(
                        painter = painter,
                        contentDescription = description,
                        title = "Run/Walk",
                        modifier = Modifier
                            .padding(end = 16.dp)
                            .width(300.dp)
                            .height(150.dp),
                        type = "Rutina"
                    )
                    Row(modifier = Modifier
                        .align(Alignment.BottomStart)){
                        Text(
                            text = "22 min",
                            style = TextStyle(
                                fontSize = 16.sp,
                                fontFamily = FontFamily(Font(R.font.poppinsregular)),
                                fontWeight = FontWeight(500),
                                color = Color.Black,
                                textAlign = TextAlign.Center
                            ),
                            modifier = Modifier
                                .align(Alignment.Bottom)
                                .padding(12.dp)
                                .clip(RoundedCornerShape(10.dp))
                                .background(Color.White)
                                .wrapContentWidth(align = Alignment.CenterHorizontally)
                                .clickable {
                                    // Acción al hacer clic en INICIAR
                                }
                                .padding(end = 8.dp)
                        )
                        Text(
                            text = "Principiante",
                            style = TextStyle(
                                fontSize = 16.sp,
                                fontFamily = FontFamily(Font(R.font.poppinsregular)),
                                fontWeight = FontWeight(500),
                                color = Color.Black,
                                textAlign = TextAlign.Center
                            ),
                            modifier = Modifier
                                .align(Alignment.Bottom)
                                .clip(RoundedCornerShape(10.dp))
                                .padding(12.dp)
                                .clip(RoundedCornerShape(10.dp))
                                .background(Color.White)
                                .wrapContentWidth(align = Alignment.CenterHorizontally)
                                .clickable {
                                    // Acción al hacer clic en INICIAR
                                }
                        )
                    }

                }
            }
        }
        Text(
            text = "Mis rutas",
            color = DarkBlue,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
                    modifier = Modifier.padding(start=45.dp, top=15.dp)
        )
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 30.dp)
        ) {
            items(5000) {
                val painter = painterResource(id = R.drawable.map)
                ImageCard(
                    painter = painter,
                    contentDescription = description,
                    title = "11/08/2023",
                    modifier = Modifier
                        .padding(end = 16.dp)
                        .width(300.dp)
                        .height(175.dp),
                    type = "Ruta"
                )
            }
        }
    }

    /*val painter = painterResource(id = R.drawable.woman)
            val description = "Mujer ejercitándose"
            val title = "Mujer sudando haciendo ejercicio"
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.5f)
                    .padding(16.dp)
            ) {
                ImageCard(
                    painter = painter,
                    contentDescription = description,
                    title = title
                )
            }

        }*/

}

@Composable
fun ImageCard(
    painter: Painter,
    contentDescription: String,
    title: String,
    modifier: Modifier = Modifier,
    type: String
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(15.dp),
        elevation = 5.dp
    ) {
        Box(modifier = Modifier
            .height(200.dp)
            .width(300.dp)) {
            Image(
                painter = painter,
                contentDescription = contentDescription,
                contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()

            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(12.dp),
                contentAlignment = Alignment.BottomStart
            ) {
                if(type.equals("Rutina")){
                    Text(
                        title,
                        style = TextStyle(color = Color.White, fontSize = 22.sp),
                        modifier = Modifier.padding(bottom = 30.dp)
                    )
                } else if (type.equals("Ruta")) {
                    Text(
                        title,
                        style = TextStyle(
                            color = Color(0xFFD3E7EF),
                            fontSize = 16.sp,
                            fontFamily = FontFamily(Font(R.font.poppinslight)),
                            fontWeight = FontWeight(400),
                            fontStyle = FontStyle.Italic
                        )
                    )
                }
            }

        }
    }
}

@Composable
@Preview
fun MainRunningPreview() {
    MainRunning()
}