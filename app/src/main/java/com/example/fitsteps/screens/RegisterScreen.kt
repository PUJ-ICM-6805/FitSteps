package com.example.fitsteps.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.fitsteps.R
import com.example.fitsteps.navigation.BOTTOM_NAVIGATION_ROUTE
import com.example.fitsteps.navigation.MAIN_SCREEN_ROUTE
import com.example.fitsteps.navigation.RegisterNavGraph
import com.example.fitsteps.navigation.Screen
import com.example.fitsteps.ui.theme.Blue
import com.example.fitsteps.ui.theme.DarkBlue
import com.example.fitsteps.ui.theme.Red
import com.example.fitsteps.ui.theme.White

@Composable
fun RegisterScreen(
    navController: NavHostController = rememberNavController(),
    mainNavController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ){
        Box(
            modifier = Modifier
                .weight(1f),
            contentAlignment = Alignment.BottomStart
        ) {
            Image(
                painter = painterResource(id = R.drawable.pesasregistro),
                contentDescription = "",
                modifier = Modifier
                    .fillMaxSize(),
                contentScale = ContentScale.FillWidth,
                alignment = Alignment.BottomCenter,
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(20.dp)
                    .background(
                        Color(0xFFF4F4F4),
                        shape = RoundedCornerShape(15.dp, 15.dp, 0.dp, 0.dp)
                    )
                    .border(
                        5.dp,
                        Color(0xFFF4F4F4),
                        shape = RoundedCornerShape(15.dp, 15.dp, 0.dp, 0.dp)
                    ),
            )
            Text(
                text = stringResource(id = R.string.register_process),
                color = Color(0xFFF0F0F0),
                fontSize = 36.sp,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.SemiBold,
                fontStyle = FontStyle.Italic,
                modifier = Modifier
                    .padding(start = 25.dp, bottom = 25.dp)
            )

        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(20.dp)
                .background(Color(0xFFF4F4F4))
                .weight(2f)
                .border(
                    5.dp,
                    Color(0xFFF4F4F4)
                ),
        ) {
            Column (
                modifier = Modifier
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                Box(
                    modifier = Modifier
                        .weight(5f)
                ) {
                    RegisterNavGraph(
                        navController = navController,
                        mainNavController = mainNavController
                    )
                }
                Box(
                    modifier = Modifier
                        .weight(1f)
                    ) {
                    Row(
                        modifier = Modifier
                            .padding(horizontal = 10.dp, vertical = 30.dp),
                    ) {
                        Text(
                            modifier = Modifier
                                .padding(start = 5.dp, end = 5.dp),
                            text = stringResource(id = R.string.having_account),
                            color = Color(0xFF5C5C5C),
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Normal,
                            style = MaterialTheme.typography.bodyLarge,
                        )
                        Text(
                            modifier = Modifier
                                .padding(start = 5.dp, end = 5.dp),
                            text = stringResource(id = R.string.login_link),
                            color = DarkBlue,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            style = MaterialTheme.typography.bodyLarge,
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun RegisterScreen1(navController: NavHostController, mainNavController: NavHostController) {
    Column(
        verticalArrangement = Arrangement.Top,
        modifier = Modifier
            .fillMaxSize()
    ) {
        RoundedLinearProgressIndicator(
            progress = 0.0f,
            modifier = Modifier.padding(start = 20.dp, bottom = 40.dp, end = 20.dp, top = 5.dp)
        )
        Text(
            modifier = Modifier
                .align(Alignment.Start)
                .padding(start = 50.dp, bottom = 10.dp),
            text = stringResource(id = R.string.name),
            color = DarkBlue,
            fontSize = 20.sp,
            fontWeight = FontWeight.SemiBold,
            style = MaterialTheme.typography.bodyLarge,
        )
        TextFields(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 40.dp, end = 40.dp, bottom = 30.dp)
                .height(70.dp),
            text = "",
        )
        Text(
            modifier = Modifier
                .align(Alignment.Start)
                .padding(start = 50.dp, bottom = 10.dp),
            text = stringResource(id = R.string.email),
            color = DarkBlue,
            fontSize = 20.sp,
            fontWeight = FontWeight.SemiBold,
            style = MaterialTheme.typography.bodyLarge,
        )
        TextFields(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 40.dp, end = 40.dp, bottom = 50.dp)
                .height(70.dp),
            text = "",
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            LoginButton(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 40.dp, end = 10.dp)
                    .height(70.dp),
                onClicked = {
                    mainNavController.navigate(
                        MAIN_SCREEN_ROUTE,
                        builder = {
                            popUpTo(route = MAIN_SCREEN_ROUTE) {
                                inclusive = true
                            }
                            launchSingleTop = true
                        }
                    )
                },
                text = stringResource(id = R.string.back),
                backgroundColor = Color(0xFFE1E1E1),
                colorText = DarkBlue,
                borderColor = Color.Transparent,
            )
            LoginButton(
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 40.dp, start = 10.dp)
                    .height(70.dp),
                onClicked = {
                    navController.navigate(
                        Screen.RegisterScreen2.route,
                    )
                },
                text = stringResource(id = R.string.forward),
                backgroundColor = Red,
                colorText = Color.White,
                borderColor = Color.Transparent,
            )
        }
    }
}

@Composable
fun RoundedLinearProgressIndicator(
    modifier: Modifier = Modifier,
    progress: Float,
    progressColor: Color = Red,
    backgroundColor: Color = Color(0xFFE1E1E1),
    clipShape: Shape = RoundedCornerShape(16.dp)
) {
    Box(
        modifier = modifier
            .background(backgroundColor, shape = clipShape)
            .height(8.dp)
            .fillMaxWidth(),
        contentAlignment = Alignment.CenterStart

    ) {
        Box(
            modifier = Modifier
                .background(progressColor, shape = clipShape)
                .fillMaxHeight()
                .fillMaxWidth(progress)
        )
    }
}

@Composable
@Preview
fun RegisterScreenPreview() {
    RegisterScreen(navController = rememberNavController(), mainNavController = rememberNavController())
}