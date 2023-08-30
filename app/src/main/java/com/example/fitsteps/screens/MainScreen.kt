package com.example.fitsteps.screens

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.fitsteps.R
import com.example.fitsteps.navigation.AUTHENTICATION_ROUTE
import com.example.fitsteps.navigation.BOTTOM_NAVIGATION_ROUTE
import com.example.fitsteps.navigation.HOME_ROUTE
import com.example.fitsteps.navigation.REGISTER_NAVIGATION_ROUTE
import com.example.fitsteps.navigation.REGISTER_ROUTE
import com.example.fitsteps.ui.theme.DarkBlue
import com.example.fitsteps.ui.theme.Red
import com.example.fitsteps.ui.theme.customFontFamily

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MainScreen(navController: NavHostController) {
    Box() {
        Image(
            painter = painterResource(id = R.drawable.pantallaprincipal),
            contentDescription = "",
            modifier = Modifier
                .fillMaxSize(),
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xEEFFFFFF)),
        )
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .weight(3f),
            ) {
                Spacer(modifier = Modifier.height(50.dp))
                Image(
                    painter = painterResource(id = R.drawable.fitstepsfoto),
                    contentDescription = "",
                    modifier = Modifier
                        .width(176.dp)
                        .height(92.dp),
                )
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .weight(4f),
            ) {
                LoginButton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 40.dp, end = 40.dp, bottom = 20.dp)
                        .height(70.dp),
                    onClicked = {
                        navController.navigate(AUTHENTICATION_ROUTE)
                    }
                )
                LoginButton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(40.dp, 20.dp)
                        .height(70.dp),
                    backgroundColor = Red,
                    text = stringResource(id = R.string.register),
                    onClicked = {
                        navController.navigate(REGISTER_NAVIGATION_ROUTE)
                    }
                )
                Text(
                    text = stringResource(id = R.string.or),
                    color = Color(0xFF5C5C5C),
                    fontSize = 18.sp,
                    modifier = Modifier
                        .padding(0.dp, 10.dp),
                    style = TextStyle(
                        fontFamily = customFontFamily,
                        fontWeight = FontWeight.Normal,
                        fontStyle = FontStyle.Normal,
                    )
                )
                GoogleButton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(40.dp, 10.dp)
                        .height(70.dp)
                )
                Spacer(modifier = Modifier.height(100.dp))
            }
        }
    }
}

@Composable
fun LoginButton(
    modifier: Modifier = Modifier,
    text: String = stringResource(id = R.string.login),
    shape: Shape = RoundedCornerShape(20.dp),
    borderColor: Color = Color.LightGray,
    backgroundColor: Color = DarkBlue,
    colorText: Color = MaterialTheme.colorScheme.onSecondary,
    onClicked: () -> Unit = {},
    style: TextStyle = TextStyle(
        fontFamily = customFontFamily,
        fontWeight = FontWeight.Medium,
        fontStyle = FontStyle.Normal,
    )
) {
    var clicked by remember { mutableStateOf(false) }
    Surface(
        modifier = modifier
            .height(70.dp)
            .clickable {
                clicked = !clicked
                onClicked()
            },
        shape = shape,
        border = BorderStroke(width = 1.dp, color = borderColor),
        color = backgroundColor,
        elevation = 2.dp
    ) {
        Box(
            modifier = Modifier
                .width(IntrinsicSize.Max)
                .height(IntrinsicSize.Max),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = text,
                color = colorText,
                fontSize = 20.sp,
                style = style,
                modifier = Modifier
                    .padding(horizontal = 8.dp, vertical = 5.dp),
            )
        }
    }
}
@Composable
fun GoogleButton(
    modifier: Modifier = Modifier,
    text: String = stringResource(id = R.string.google),
    icon: Int = R.drawable.ic_google_logo,
    shape: Shape = RoundedCornerShape(20.dp),
    borderColor: Color = Color.LightGray,
    backgroundColor: Color = MaterialTheme.colorScheme.surface,
    //onClicked: () -> Unit
) {
    var clicked by remember { mutableStateOf(false) }
    Surface(
        modifier = modifier.clickable {
            clicked = !clicked
        },
        shape = shape,
        border = BorderStroke(width = 1.dp, color = borderColor),
        color = backgroundColor
    ) {
        Row(
            modifier = Modifier
                .padding(
                    start = 70.dp,
                    end = 70.dp,
                    top = 12.dp,
                    bottom = 12.dp
                ),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(
                painter = painterResource(id = icon),
                contentDescription = "Google Button",
                tint = Color.Unspecified
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = text,
                fontWeight = FontWeight.Medium,
                color = DarkBlue,
                fontSize = 20.sp,
                style = TextStyle(
                    fontFamily = customFontFamily,
                    fontWeight = FontWeight.Medium,
                    fontStyle = FontStyle.Normal,
                )
            )

        }
    }
}


@Preview
@Composable
fun MainScreenPreview() {
    MainScreen(navController = rememberNavController())
}
