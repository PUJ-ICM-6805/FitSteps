package com.example.fitsteps.screens

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.fitsteps.R
import com.example.fitsteps.navigation.BottomBarScreen
import com.example.fitsteps.ui.theme.Blue
import com.example.fitsteps.ui.theme.DarkBlue
import com.example.fitsteps.ui.theme.LightBlue
import com.example.fitsteps.ui.theme.White
import com.example.fitsteps.ui.theme.customFontFamily

@Composable
fun BarbelBenchScreen(navController: NavHostController) {
    Column (
        modifier = Modifier
            .fillMaxSize()
            .background(White),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween,
    ){
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 20.dp),
        ) {
            Text(
                modifier = Modifier
                    .align(Alignment.Center),
                text = stringResource(id = R.string.plank),
                style = TextStyle(
                    fontFamily = customFontFamily,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 25.sp,
                    fontStyle = FontStyle.Normal,
                    color = DarkBlue,
                )
            )

        }

        Box(
            modifier = Modifier
                .padding(bottom = 2.dp, top = 20.dp)
                .fillMaxWidth() ,
            contentAlignment = Alignment.Center,


            ){
            Box(
                modifier = Modifier

                    .padding(horizontal = 20.dp)
                    .background(color = Color.White, shape = RoundedCornerShape(15.dp))
                    .clip(shape = RoundedCornerShape(8.dp))
                    .width(335.dp)
                    .height(175.dp)
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center

            ) {
                Image(
                    painter = painterResource(id = R.drawable.play),
                    contentDescription = "",
                    modifier = Modifier
                        .width(176.dp)
                        .height(92.dp),
                )
            }
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp)
                .padding(horizontal = 30.dp),
            contentAlignment = Alignment.CenterStart,

            ){
            Column() {
                Text(
                    modifier = Modifier
                        .padding(8.dp), // Ajusta el valor según el espaciado deseado
                    text = "Series",
                    color = DarkBlue,
                    fontSize = 18.sp,
                    style = TextStyle(
                        fontFamily = customFontFamily,
                        fontWeight = FontWeight.SemiBold,
                        fontStyle = FontStyle.Normal,
                    ),
                )
                Box(
                    modifier = Modifier
                        .fillMaxWidth(),
                    contentAlignment = Alignment.CenterStart,
                ){
                    SetsBarbel()
                }

            }

        }
        Row(){
            Text(
                text = "30",
                color = Blue,
                fontSize = 70.sp,
                fontFamily = customFontFamily,
                fontWeight = FontWeight.Medium,
                modifier = Modifier
                    .height(90.dp)
                    .padding(bottom = 0.dp) // Espaciado entre los textos
            )
            Text(
                text = "s",
                color = Blue,
                fontSize = 70.sp,
                fontFamily = customFontFamily,
                fontWeight = FontWeight.Normal,
                modifier = Modifier
                    .height(90.dp)
                    .padding(bottom = 0.dp) // Espaciado entre los textos
            )
        }


        Text(
            text = "Descansa",
            fontFamily = customFontFamily,
            fontWeight = FontWeight.SemiBold,
            color = Blue,
            fontSize = 25.sp
        )
        DataRoutineBarbel(navController = navController)



    }

}

@Composable
fun DataRoutineBarbel(navController: NavHostController){
    Column (
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(top = 25.dp)
            .background(Color.White,shape = RoundedCornerShape(topStart = 35.dp, topEnd = 35.dp))
            .clip(RoundedCornerShape(16.dp))

    ){
        Box(
            modifier = Modifier
                .fillMaxWidth(),
            contentAlignment = Alignment.Center,
        ){
            Image(
                painter = painterResource(id = R.drawable.rectangle),
                contentDescription = "",
                modifier = Modifier
                    .width(35.dp)
                    .height(30.dp),
            )

        }
        Column(
            modifier = Modifier
                .padding(top = 20.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally // Centrar el contenido verticalmente
        ){
            Text(
                modifier = Modifier
                    .align(Alignment.Start)
                    .padding(start = 25.dp),
                text = stringResource(id = R.string.nextexercise),
                style = TextStyle(
                    fontFamily = customFontFamily,
                    fontWeight = FontWeight.Normal,
                    fontSize = 22.sp,
                    fontStyle = FontStyle.Normal,
                    color = Blue,
                )
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                contentAlignment = Alignment.CenterStart,

                ){
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        modifier = Modifier
                            .padding(8.dp), // Ajusta el valor según el espaciado deseado
                        text = stringResource(id = R.string.plank),
                        color = DarkBlue,
                        fontSize = 24.sp,
                        style = TextStyle(
                            fontFamily = customFontFamily,
                            fontWeight = FontWeight.SemiBold,
                            fontStyle = FontStyle.Normal,
                        ),
                    )
                    Text(
                        modifier = Modifier
                            .padding(8.dp), // Ajusta el valor según el espaciado deseado
                        text = stringResource(id = R.string.series_example),
                        color = Blue,
                        fontSize = 16.sp,
                        style = TextStyle(
                            fontFamily = customFontFamily,
                            fontWeight = FontWeight.Medium,
                            fontStyle = FontStyle.Normal,
                        ),
                    )
                }

            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 15.dp)
                    .padding(horizontal = 20.dp),
                contentAlignment = Alignment.CenterStart,

                ){
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .padding(bottom = 25.dp),
                ) {
                    Box(
                        modifier = Modifier
                            .padding(horizontal = 15.dp)
                            .background(color = LightBlue, shape = RoundedCornerShape(15.dp))
                            .clickable { navController.popBackStack() }
                    ){
                        Text(
                            modifier = Modifier
                                .padding(horizontal = 15.dp), // Ajusta el valor según el espaciado deseado
                            text = "<",
                            color = Blue,
                            fontSize = 30.sp,
                            style = TextStyle(
                                fontFamily = customFontFamily,
                                fontWeight = FontWeight.Medium,
                                fontStyle = FontStyle.Normal,
                            ),
                        )
                    }
                    Box(
                        modifier = Modifier
                            .graphicsLayer {
                                shape = RoundedCornerShape(10.dp)
                                clip = true
                            }
                            .height(70.dp)
                            .padding(top = 10.dp, bottom = 10.dp)
                            .background(color = Blue, shape = RoundedCornerShape(15.dp))
                    ){
                        Text(
                            modifier = Modifier
                                .padding(top = 10.dp)
                                .padding(horizontal = 15.dp), // Ajusta el valor según el espaciado deseado
                            text = "Terminar Rutina",
                            color = Color.White,
                            fontSize = 20.sp,
                            style = TextStyle(
                                fontFamily = customFontFamily,
                                fontWeight = FontWeight.Medium,
                                fontStyle = FontStyle.Normal,
                            ),
                        )
                    }
                    Box(
                        modifier = Modifier
                            .padding(horizontal = 15.dp)
                            .background(color = LightBlue, shape = RoundedCornerShape(15.dp))
                            .clickable { navController.navigate(BottomBarScreen.Exercise.route) }
                    ){
                        Text(
                            modifier = Modifier
                                .padding(horizontal = 15.dp), // Ajusta el valor según el espaciado deseado
                            text = ">",
                            color = Blue,
                            fontSize = 30.sp,
                            style = TextStyle(
                                fontFamily = customFontFamily,
                                fontWeight = FontWeight.Medium,
                                fontStyle = FontStyle.Normal,
                            ),
                        )
                    }
                }

            }

        }


    }

}

@Composable
fun SetsBarbel(){
    Row(
        modifier = Modifier
            .clip(shape = RoundedCornerShape(8.dp))
            .fillMaxWidth()
            .height(35.dp),
    ) {
        Box(
            modifier = Modifier
                .graphicsLayer {
                    shape = RoundedCornerShape(10.dp)
                    clip = true
                }
                .fillMaxSize()
                .background(Blue)
                .weight(1f)
                .clip(shape = RoundedCornerShape(8.dp)),
            contentAlignment = Alignment.Center

        ) {
            Text(
                text = "5kg | 10",
                style = TextStyle(
                    fontFamily = customFontFamily,
                    fontWeight = FontWeight.Medium,
                    fontSize = 15.sp,
                    fontStyle = FontStyle.Normal,
                    color = White,
                )
            )
        }
        Spacer(modifier = Modifier.width(20.dp)) // Agregar un espacio entre los cuadros
        Box(
            modifier = Modifier
                .graphicsLayer {
                    shape = RoundedCornerShape(10.dp)
                    clip = true
                }
                .fillMaxSize()
                .weight(1f)
                .background(color = Blue)
                .clip(shape = RoundedCornerShape(8.dp)),
            contentAlignment = Alignment.Center

        ) {
            Text(
                text = "10kg | 15",
                style = TextStyle(
                    fontFamily = customFontFamily,
                    fontWeight = FontWeight.Medium,
                    fontSize = 15.sp,
                    fontStyle = FontStyle.Normal,
                    color = White,
                )
            )

        }
        Spacer(modifier = Modifier.width(20.dp)) // Agregar un espacio entre los cuadros
        Box(
            modifier = Modifier
                .graphicsLayer {
                    shape = RoundedCornerShape(10.dp)
                    clip = true
                }
                .fillMaxSize()
                .weight(1f)
                .background(color = Blue)
                .clip(shape = RoundedCornerShape(8.dp)),
            contentAlignment = Alignment.Center

        ) {
            Text(
                text = "20kg | 6",
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
fun BarbelBenchScreenPreview() {
    BarbelBenchScreen(navController = rememberNavController())
}