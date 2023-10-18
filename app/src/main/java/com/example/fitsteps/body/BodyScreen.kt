package com.example.fitsteps.body

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
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.fitsteps.R
import com.example.fitsteps.navigation.Screen
import com.example.fitsteps.screens.HamburgersDropList
import com.example.fitsteps.ui.theme.Blue
import com.example.fitsteps.ui.theme.DarkBlue
import com.example.fitsteps.ui.theme.LightBlue
import com.example.fitsteps.ui.theme.Red
import com.example.fitsteps.ui.theme.White
import com.example.fitsteps.ui.theme.customFontFamily

@Composable
fun BodyScreen(navController: NavHostController, rootNavController: NavHostController) {
    var showBodyFrame by remember {
        mutableStateOf(false)
    }
    if (showBodyFrame) {
        BodyScreenFrame(
            onClick = { showFrame ->
                showBodyFrame = showFrame
            }
        )
    }
    Column (
        modifier = Modifier
            .fillMaxSize()
            .background(White),
    ){
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.TopEnd
        ) {
            Column {
                Spacer(modifier = Modifier.height(20.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(30.dp),
                    contentAlignment = Alignment.CenterStart,
                ) {
                    HamburgersDropList(navController = navController, rootNavController = rootNavController)
                }
                Spacer(modifier = Modifier.height(40.dp))
            }
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
        ) {
            Text(
                modifier = Modifier
                    .align(Alignment.TopStart),
                text = stringResource(id = R.string.body),
                style = TextStyle(
                    fontFamily = customFontFamily,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 30.sp,
                    fontStyle = FontStyle.Normal,
                    color = DarkBlue,
                )
            )
            Text(
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .clickable {
                         showBodyFrame = true
                    },
                text = stringResource(id = R.string.add),
                style = TextStyle(
                    fontFamily = customFontFamily,
                    fontWeight = FontWeight.Normal,
                    fontSize = 14.sp,
                    fontStyle = FontStyle.Normal,
                    color = Red,
                )
            )
        }
        Box(
            modifier = Modifier
                .fillMaxWidth(),
            contentAlignment = Alignment.Center,
        ){
            Navigation(navController = navController)
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp, bottom = 0.dp, start = 20.dp, end = 20.dp),
            contentAlignment = Alignment.TopStart,
        ){
            Text(
                text = stringResource(id = R.string.weight2),
                style = TextStyle(
                    fontFamily = customFontFamily,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 23.sp,
                    fontStyle = FontStyle.Normal,
                    color = DarkBlue,
                )
            )
        }
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.TopStart,
        ){
            WeightCard()
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp, bottom = 0.dp, start = 20.dp, end = 20.dp),
            contentAlignment = Alignment.TopStart,
        ){
            Text(
                text = stringResource(id = R.string.diagnostic),
                style = TextStyle(
                    fontFamily = customFontFamily,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 23.sp,
                    fontStyle = FontStyle.Normal,
                    color = DarkBlue,
                )
            )
        }
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.TopStart,
        ){
            DiagnosticCard()
        }
    }

}
@Composable
fun Navigation(navController: NavController){
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
                .background(Blue)
                .weight(1f)
                .clip(shape = RoundedCornerShape(8.dp)),
            contentAlignment = Alignment.Center

        ) {
            Text(
                text = stringResource(id = R.string.body),
                style = TextStyle(
                    fontFamily = customFontFamily,
                    fontWeight = FontWeight.Medium,
                    fontSize = 15.sp,
                    fontStyle = FontStyle.Normal,
                    color = White,
                )
            )
        }
        Box(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f)
                .clip(shape = RoundedCornerShape(8.dp))
                .clickable {
                    navController.navigate(Screen.BodyScreen2.route)
                },
            contentAlignment = Alignment.Center

        ) {
            Text(
                text = stringResource(id = R.string.measures),
                style = TextStyle(
                    fontFamily = customFontFamily,
                    fontWeight = FontWeight.Medium,
                    fontSize = 15.sp,
                    fontStyle = FontStyle.Normal,
                    color = Blue,
                )
            )

        }
    }

}
@Composable
fun WeightCard(){
    Surface(
        shape = RoundedCornerShape(16.dp),
        color = Color.White,
        modifier = Modifier
            .fillMaxWidth()
            .height(280.dp)
            .padding(horizontal = 20.dp),
    ) {
        Column(
            modifier= Modifier
                .fillMaxSize()
                .padding(horizontal = 10.dp),
        ){

            Row(
                modifier = Modifier
                    .fillMaxWidth(0.5f)
                    .padding(top = 15.dp, bottom = 0.dp, start = 0.dp, end = 0.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ){
                Text(
                    modifier = Modifier.padding(start = 2.dp),
                    text = stringResource(id =R.string.initial),
                    style = TextStyle(
                        fontFamily = customFontFamily,
                        fontWeight = FontWeight.Normal,
                        fontSize = 12.sp,
                        fontStyle = FontStyle.Normal,
                        color = Red,
                    )
                )
                Text(
                    modifier = Modifier.padding(end = 10.dp),
                    text = stringResource(id =R.string.current),
                    style = TextStyle(
                        fontFamily = customFontFamily,
                        fontWeight = FontWeight.Normal,
                        fontSize = 12.sp,
                        fontStyle = FontStyle.Normal,
                        color = Blue,
                    )
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth(0.5f)
                    .offset(x = 10.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ){
                Text(
                    modifier =Modifier.padding(start = 2.dp),
                    text = "63,2 Kg",
                    style = TextStyle(
                        fontFamily = customFontFamily,
                        fontWeight = FontWeight.Normal,
                        fontSize = 14.sp,
                        fontStyle = FontStyle.Normal,
                        color = DarkBlue,
                    )
                )
                Text(
                    modifier =Modifier.padding(end = 10.dp),
                    text = "60,0 Kg",
                    style = TextStyle(
                        fontFamily = customFontFamily,
                        fontWeight = FontWeight.Normal,
                        fontSize = 14.sp,
                        fontStyle = FontStyle.Normal,
                        color = DarkBlue,
                    )
                )
            }
            Image(
                    modifier = Modifier
                        .fillMaxSize(),
                    painter = painterResource(id = R.drawable.grafica),
                    contentDescription = "Weight chart image"
            )
        }

    }

}
@Composable
fun DiagnosticCard(){
    Surface(
        shape = RoundedCornerShape(16.dp),
        color = Color.White,
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp)
            .padding(20.dp),
    ) {
        Column(
            modifier= Modifier
                .fillMaxSize()
                .padding(10.dp),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Text(
                    text = stringResource(id =R.string.BMI),
                    style = TextStyle(
                        fontFamily = customFontFamily,
                        fontWeight = FontWeight.Light,
                        fontSize = 12.sp,
                        fontStyle = FontStyle.Normal,
                        color = Blue,
                        )
                )
                Text(
                    text = stringResource(id =R.string.bodyfat),
                    style = TextStyle(
                        fontFamily = customFontFamily,
                        fontWeight = FontWeight.Light,
                        fontSize = 12.sp,
                        fontStyle = FontStyle.Normal,
                        color = Blue,
                    )

                )
                Text(
                    text = stringResource(id =R.string.idealWeight),
                    style = TextStyle(
                        fontFamily = customFontFamily,
                        fontWeight = FontWeight.Light,
                        fontSize = 12.sp,
                        fontStyle = FontStyle.Normal,
                        color = Blue,
                    )
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ){
                Text(
                    text ="22,84",
                    style = TextStyle(
                        fontFamily = customFontFamily,
                        fontWeight = FontWeight.Medium,
                        fontSize = 18.sp,
                        fontStyle = FontStyle.Normal,
                        color = Blue,
                    )
                )
                Text(
                    text ="13,83",
                    style = TextStyle(
                        fontFamily = customFontFamily,
                        fontWeight = FontWeight.Medium,
                        fontSize = 18.sp,
                        fontStyle = FontStyle.Normal,
                        color = Blue,
                    )
                )
                Text(
                    text ="53-72 Kg",
                    style = TextStyle(
                        fontFamily = customFontFamily,
                        fontWeight = FontWeight.Medium,
                        fontSize = 18.sp,
                        fontStyle = FontStyle.Normal,
                        color = Blue,
                    )
                )

            }
        }

    }

}
@Composable
@Preview
fun BodyScreenPreview() {
    BodyScreen(navController = rememberNavController(), rootNavController = rememberNavController())
}