package com.example.fitsteps.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldColors
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
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
import com.example.fitsteps.navigation.AUTHENTICATION_ROUTE
import com.example.fitsteps.navigation.BOTTOM_NAVIGATION_ROUTE
import com.example.fitsteps.navigation.MAIN_SCREEN_ROUTE
import com.example.fitsteps.navigation.Screen
import com.example.fitsteps.ui.theme.Blue
import com.example.fitsteps.ui.theme.DarkBlue
import com.example.fitsteps.ui.theme.White
import com.example.fitsteps.ui.theme.customFontFamily

@Composable
fun LoginScreen(navController: NavHostController) {
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
                painter = painterResource(id = R.drawable.pesaslogin),
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
                text = stringResource(id = R.string.login_screen),
                color = Color(0xFFF0F0F0),
                fontSize = 36.sp,
                modifier = Modifier
                    .padding(start = 25.dp, bottom = 25.dp),
                style = TextStyle(
                    fontFamily = customFontFamily,
                    fontWeight = FontWeight.SemiBold,
                    fontStyle = FontStyle.Italic,
                ),
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
                Text(
                    modifier = Modifier
                        .align(Alignment.Start)
                        .padding(start = 50.dp, bottom = 10.dp),
                    text = stringResource(id = R.string.user),
                    color = DarkBlue,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold,
                    style = TextStyle(
                        fontFamily = customFontFamily,
                        fontWeight = FontWeight.SemiBold,
                        fontStyle = FontStyle.Normal,
                    ),
                )
                TextFields(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 40.dp, end = 40.dp, bottom = 40.dp)
                        .height(70.dp),
                    text = "",
                )
                Text(
                    modifier = Modifier
                        .align(Alignment.Start)
                        .padding(start = 50.dp, bottom = 10.dp),
                    text = stringResource(id = R.string.password),
                    color = DarkBlue,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold,
                    style = TextStyle(
                        fontFamily = customFontFamily,
                        fontWeight = FontWeight.SemiBold,
                        fontStyle = FontStyle.Normal,
                    ),
                )
                TextFields(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 40.dp, end = 40.dp, bottom = 10.dp)
                        .height(70.dp),
                    text = "",
                )
                Text(
                    modifier = Modifier
                        .align(Alignment.End)
                        .padding(end = 50.dp, bottom = 40.dp),
                    text = stringResource(id = R.string.forgot_password),
                    color = Blue,
                    fontSize = 16.sp,
                    style = TextStyle(
                        fontFamily = customFontFamily,
                        fontWeight = FontWeight.Medium,
                        fontStyle = FontStyle.Normal,
                    ),
                )
                LoginButton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 40.dp, end = 40.dp, bottom = 20.dp)
                        .height(70.dp),
                    onClicked = {
                        navController.navigate(
                            BOTTOM_NAVIGATION_ROUTE,
                            builder = {
                                popUpTo(route = MAIN_SCREEN_ROUTE) {
                                    inclusive = true
                                }
                                launchSingleTop = true
                            }
                        )
                    }
                )
                Row(
                    modifier = Modifier
                        .padding(top = 40.dp)
                ) {
                    Text(
                        modifier = Modifier
                            .padding(start = 5.dp, end = 5.dp),
                        text = stringResource(id = R.string.not_having_account),
                        color = Color(0xFF5C5C5C),
                        fontSize = 16.sp,
                        style = TextStyle(
                            fontFamily = customFontFamily,
                            fontWeight = FontWeight.Normal,
                            fontStyle = FontStyle.Normal,
                        ),
                    )
                    Text(
                        modifier = Modifier
                            .padding(start = 5.dp, end = 5.dp),
                        text = stringResource(id = R.string.register_link),
                        color = Color(0xFFE71D36),
                        fontSize = 16.sp,
                        style = TextStyle(
                            fontFamily = customFontFamily,
                            fontWeight = FontWeight.Bold,
                            fontStyle = FontStyle.Normal,
                        ),
                    )
                }
            }
        }
    }
}

@Composable
fun TextFields(
    modifier: Modifier = Modifier,
    shape: Shape = RoundedCornerShape(20.dp),
    backgroundColor: Color = Color.White,
    text: String = stringResource(id = R.string.user),
    label: String = "",
) {
    Surface(
        modifier = modifier,
        shape = shape,
        color = backgroundColor,
    ) {
        TextField(
            value = "",
            onValueChange = { },
            label = { Text(text) },
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = backgroundColor,
                focusedIndicatorColor = DarkBlue,
                cursorColor = DarkBlue,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,

            ),
        )
    }
}



@Composable
@Preview
fun LoginScreenPreview() {
    LoginScreen(navController = rememberNavController())
}