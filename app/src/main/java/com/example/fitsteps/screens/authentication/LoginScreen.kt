package com.example.fitsteps.screens.authentication

import android.widget.Toast
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.fitsteps.R
import com.example.fitsteps.authentication.LoginViewModel
import com.example.fitsteps.navigation.AUTHENTICATION_ROUTE
import com.example.fitsteps.navigation.BOTTOM_NAVIGATION_ROUTE
import com.example.fitsteps.navigation.MAIN_SCREEN_ROUTE
import com.example.fitsteps.navigation.REGISTER_NAVIGATION_ROUTE
import com.example.fitsteps.navigation.REGISTER_ROUTE
import com.example.fitsteps.screens.LoginButton
import com.example.fitsteps.ui.theme.Blue
import com.example.fitsteps.ui.theme.DarkBlue
import com.example.fitsteps.ui.theme.customFontFamily
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase

@Composable
fun LoginScreen(
    navController: NavHostController,
    viewModel: LoginViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val context = LocalContext.current

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
                    onTextChange = {
                        email = it
                    },
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
                PasswordField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 40.dp, end = 40.dp, bottom = 10.dp)
                        .height(70.dp),
                    onPasswordChange = {
                       password = it
                    },
                )
                Text(
                    modifier = Modifier
                        .align(Alignment.End)
                        .padding(end = 50.dp, bottom = 40.dp)
                        .clickable {
                            if (email.isEmpty()) {
                                Toast.makeText(
                                    context,
                                    "Please enter your email",
                                    Toast.LENGTH_SHORT
                                ).show()
                            } else {
                                FirebaseAuth.getInstance().sendPasswordResetEmail(email)
                                    .addOnSuccessListener { task ->
                                        Toast.makeText(
                                            context,
                                            "An email has been sent to $email",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                    .addOnFailureListener { exception ->
                                        Toast.makeText(
                                            context,
                                            exception.message,
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                            }
                        },
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
                        if (email.isNotEmpty() && password.isNotEmpty()) {
                            viewModel.signInWithEmailAndPassword(email, password,
                                home = {
                                    navController.navigate(
                                        BOTTOM_NAVIGATION_ROUTE,
                                        builder = {
                                            popUpTo(route = MAIN_SCREEN_ROUTE) {
                                                inclusive = true
                                            }
                                            launchSingleTop = true
                                        }
                                    )
                                },
                                error = {
                                    Toast.makeText(
                                        context,
                                        it,
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            )
                        } else {
                            Toast.makeText(
                                context,
                                "Por favor ingrese sus datos",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                )
                Row(
                    modifier = Modifier
                        .padding(top = 40.dp)
                        .clickable {
                            navController.navigate(
                                route = REGISTER_NAVIGATION_ROUTE,
                                builder = {
                                    popUpTo(route = AUTHENTICATION_ROUTE) {
                                        inclusive = true
                                    }
                                    launchSingleTop = true
                                }
                            )
                        }
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


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun TextFields(
    modifier: Modifier = Modifier,
    shape: Shape = RoundedCornerShape(20.dp),
    backgroundColor: Color = Color.White,
    text: String = "",
    label: String = "",
    onTextChange: (String) -> Unit,
    keyboardOptions: KeyboardOptions = KeyboardOptions(
        keyboardType = KeyboardType.Text,
        imeAction = ImeAction.Done
    ),
) {
    var textData by remember {
        mutableStateOf(label)
    }
    val keyboardController = LocalSoftwareKeyboardController.current
    Surface(
        modifier = modifier,
        shape = shape,
        color = backgroundColor,
    ) {
        TextField(
            value = textData,
            onValueChange = {
                textData = it
                onTextChange(it)
            },
            singleLine = true,
            textStyle = TextStyle(
                fontFamily = customFontFamily,
                fontWeight = FontWeight.Medium,
                fontStyle = FontStyle.Normal,
                color = DarkBlue
            ),
            label = { Text(text) },
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = backgroundColor,
                focusedIndicatorColor = DarkBlue,
                cursorColor = DarkBlue,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
            ),
            keyboardOptions = keyboardOptions,
            keyboardActions = KeyboardActions(
                onDone = {
                    keyboardController?.hide()
                }
            )
        )
    }
}

@Composable
@Preview
fun LoginScreenPreview() {
    LoginScreen(navController = rememberNavController())
}