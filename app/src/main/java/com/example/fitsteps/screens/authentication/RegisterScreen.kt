package com.example.fitsteps.screens.authentication

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.RadioButton
import androidx.compose.material.RadioButtonDefaults
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.fitsteps.R
import com.example.fitsteps.authentication.LoginViewModel
import com.example.fitsteps.authentication.NewUserViewModel
import com.example.fitsteps.authentication.User
import com.example.fitsteps.navigation.MAIN_SCREEN_ROUTE
import com.example.fitsteps.navigation.RegisterNavGraph
import com.example.fitsteps.navigation.Screen
import com.example.fitsteps.screens.LoginButton
import com.example.fitsteps.ui.theme.Blue
import com.example.fitsteps.ui.theme.DarkBlue
import com.example.fitsteps.ui.theme.LightBlue
import com.example.fitsteps.ui.theme.Red
import com.example.fitsteps.ui.theme.customFontFamily

@Composable
fun RegisterScreen(
    navController: NavHostController = rememberNavController(),
    mainNavController: NavHostController,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ){
        Box(
            modifier = Modifier
                .weight(2f),
            contentAlignment = Alignment.BottomStart
        ) {
            Image(
                painter = painterResource(id = R.drawable.pesasregistro),
                contentDescription = "",
                modifier = Modifier
                    .fillMaxSize(),
                contentScale = ContentScale.Crop,
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
                modifier = Modifier
                    .padding(start = 25.dp, bottom = 25.dp),
                style = TextStyle(
                    fontFamily = customFontFamily,
                    fontWeight = FontWeight.SemiBold,
                    fontStyle = FontStyle.Italic,
                )
            )

        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(20.dp)
                .background(Color(0xFFF4F4F4))
                .weight(5f)
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
                            style = TextStyle(
                                fontFamily = customFontFamily,
                                fontWeight = FontWeight.Normal,
                                fontStyle = FontStyle.Normal,
                            ),
                        )
                        Text(
                            modifier = Modifier
                                .padding(start = 5.dp, end = 5.dp),
                            text = stringResource(id = R.string.login_link),
                            color = DarkBlue,
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
}

@Composable
fun RegisterScreen1(
    navController: NavHostController,
    mainNavController: NavHostController,
    viewModel: NewUserViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    var name by remember {
        mutableStateOf("")
    }
    var email by remember {
        mutableStateOf("")
    }
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
            style = TextStyle(
                fontFamily = customFontFamily,
                fontWeight = FontWeight.SemiBold,
                fontStyle = FontStyle.Normal,
            ),
        )
        TextFields(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 40.dp, end = 40.dp, bottom = 30.dp)
                .height(70.dp),
            onTextChange = {
               name = it
            },
        )
        Text(
            modifier = Modifier
                .align(Alignment.Start)
                .padding(start = 50.dp, bottom = 10.dp),
            text = stringResource(id = R.string.email),
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
                .padding(start = 40.dp, end = 40.dp, bottom = 50.dp)
                .height(70.dp),
            onTextChange = {
                email = it
            }
        )
        ForwardBackButtons(
            onClickedBack = {
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
            onClickedForward = {
                viewModel.email = email
                viewModel.name = name
                Log.d("email", "email: ${viewModel.email}")
                if(viewModel.email != "" && viewModel.name != "") {
                    navController.navigate(
                        Screen.RegisterScreen2.route,
                    )
                }
            }
        )
    }
}

@Composable
fun RegisterScreen2(
    navController: NavHostController,
    mainNavController: NavHostController,
    viewModel: NewUserViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    var gender by remember {
        mutableStateOf("")
    }
    Column(
        verticalArrangement = Arrangement.Top,
        modifier = Modifier
            .fillMaxSize()
    ) {
        RoundedLinearProgressIndicator(
            progress = 0.25f,
            modifier = Modifier.padding(start = 20.dp, bottom = 40.dp, end = 20.dp, top = 5.dp)
        )
        Text(
            modifier = Modifier
                .align(Alignment.Start)
                .padding(start = 50.dp, bottom = 10.dp),
            text = stringResource(id = R.string.birth_date),
            color = DarkBlue,
            fontSize = 20.sp,
            fontWeight = FontWeight.SemiBold,
            style = TextStyle(
                fontFamily = customFontFamily,
                fontWeight = FontWeight.SemiBold,
                fontStyle = FontStyle.Normal,
            ),
        )
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 40.dp, end = 40.dp, bottom = 30.dp)
                .height(70.dp),
            shape = RoundedCornerShape(20.dp),
            color = Color.White,
        ) {
            Box(
                contentAlignment = Alignment.CenterStart,
                modifier = Modifier
                    .fillMaxSize(),
            ) {
                Text(
                    text = "DD/MM/YYYY",
                    color = Blue,
                    fontSize = 20.sp,
                    modifier = Modifier
                        .padding(start = 20.dp),
                    style = TextStyle(
                        fontFamily = customFontFamily,
                        fontWeight = FontWeight.Light,
                        fontStyle = FontStyle.Italic,
                    ),
                )
            }
        }
        Text(
            modifier = Modifier
                .align(Alignment.Start)
                .padding(start = 50.dp, bottom = 10.dp),
            text = stringResource(id = R.string.gender),
            color = DarkBlue,
            fontSize = 20.sp,
            fontWeight = FontWeight.SemiBold,
            style = TextStyle(
                fontFamily = customFontFamily,
                fontWeight = FontWeight.SemiBold,
                fontStyle = FontStyle.Normal,
            ),
        )
        RadioButtonGroup { gender = it }
        Spacer(modifier = Modifier.height(20.dp))
        ForwardBackButtons(
            onClickedBack = {
                navController.popBackStack()
            },
            onClickedForward = {
                Log.d("email", "email: ${viewModel.email}")
                if(gender != "") {
                    viewModel.gender = gender
                    navController.navigate(
                        Screen.RegisterScreen3.route,
                    )
                }

            }
        )
    }
}

@Composable
fun RegisterScreen3(
    navController: NavHostController,
    mainNavController: NavHostController,
    viewModel: NewUserViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    var weight by remember {
        mutableStateOf("")
    }
    var height by remember {
        mutableStateOf("")
    }
    Column(
        verticalArrangement = Arrangement.Top,
        modifier = Modifier
            .fillMaxSize()
    ) {
        RoundedLinearProgressIndicator(
            progress = 0.5f,
            modifier = Modifier.padding(start = 20.dp, bottom = 40.dp, end = 20.dp, top = 5.dp)
        )
        Text(
            modifier = Modifier
                .align(Alignment.Start)
                .padding(start = 50.dp, bottom = 10.dp),
            text = stringResource(id = R.string.weight),
            color = DarkBlue,
            fontSize = 20.sp,
            style = TextStyle(
                fontFamily = customFontFamily,
                fontWeight = FontWeight.SemiBold,
                fontStyle = FontStyle.Normal,
            ),
        )
        TextFields(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 40.dp, end = 40.dp, bottom = 30.dp)
                .height(70.dp),
            onTextChange = {
               weight = it
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done
            ),
        )
        Text(
            modifier = Modifier
                .align(Alignment.Start)
                .padding(start = 50.dp, bottom = 10.dp),
            text = stringResource(id = R.string.height),
            color = DarkBlue,
            fontSize = 20.sp,
            style = TextStyle(
                fontFamily = customFontFamily,
                fontWeight = FontWeight.SemiBold,
                fontStyle = FontStyle.Normal,
            ),
        )
        TextFields(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 40.dp, end = 40.dp, bottom = 50.dp)
                .height(70.dp),
            onTextChange = {
               height = it
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Decimal,
                imeAction = ImeAction.Done
            ),
        )
        ForwardBackButtons(
            onClickedBack = {
                navController.popBackStack()
            },
            onClickedForward = {
                if(weight != "" && height != "") {
                    viewModel.height = height.toFloat()
                    viewModel.weight = weight.toFloat()
                    navController.navigate(
                        Screen.RegisterScreen4.route,
                    )
                }
            }
        )
    }
}

@Composable
fun RegisterScreen4(
    navController: NavHostController,
    mainNavController: NavHostController,
    viewModel: NewUserViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    var experience by remember {
        mutableStateOf("")
    }
    Column(
        verticalArrangement = Arrangement.Top,
        modifier = Modifier
            .fillMaxSize()
    ) {
        RoundedLinearProgressIndicator(
            progress = 0.75f,
            modifier = Modifier.padding(start = 20.dp, bottom = 40.dp, end = 20.dp, top = 5.dp)
        )
        Text(
            modifier = Modifier
                .align(Alignment.Start)
                .padding(start = 50.dp, bottom = 5.dp),
            text = stringResource(id = R.string.experience),
            color = DarkBlue,
            fontSize = 20.sp,
            style = TextStyle(
                fontFamily = customFontFamily,
                fontWeight = FontWeight.SemiBold,
                fontStyle = FontStyle.Normal,
            ),
        )
        ButtonsList {experience = it}
        Spacer(modifier = Modifier.height(20.dp))
        ForwardBackButtons(
            onClickedBack = {
                navController.popBackStack()
            },
            onClickedForward = {
                if(experience != "") {
                    viewModel.experience = experience
                    navController.navigate(
                        Screen.RegisterScreen5.route,
                    )
                }
            }
        )
    }
}

@Composable
fun RegisterScreen5(
    navController: NavHostController,
    mainNavController: NavHostController,
    viewModel: NewUserViewModel = androidx.lifecycle.viewmodel.compose.viewModel(),
    loginViewModel: LoginViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    var passwd by remember {
        mutableStateOf("")
    }
    var confirmPasswd by remember {
        mutableStateOf("")
    }
    Column(
        verticalArrangement = Arrangement.Top,
        modifier = Modifier
            .fillMaxSize()
    ) {
        RoundedLinearProgressIndicator(
            progress = 1.0f,
            modifier = Modifier.padding(start = 20.dp, bottom = 40.dp, end = 20.dp, top = 5.dp)
        )
        Text(
            modifier = Modifier
                .align(Alignment.Start)
                .padding(start = 50.dp, bottom = 10.dp),
            text = stringResource(id = R.string.password),
            color = DarkBlue,
            fontSize = 20.sp,
            style = TextStyle(
                fontFamily = customFontFamily,
                fontWeight = FontWeight.SemiBold,
                fontStyle = FontStyle.Normal,
            ),
        )
        PasswordField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 40.dp, end = 40.dp, bottom = 50.dp)
                .height(70.dp),
            onPasswordChange = {
               passwd = it
            },
        )
        Text(
            modifier = Modifier
                .align(Alignment.Start)
                .padding(start = 50.dp, bottom = 10.dp),
            text = stringResource(id = R.string.confirm_password),
            color = DarkBlue,
            fontSize = 20.sp,
            style = TextStyle(
                fontFamily = customFontFamily,
                fontWeight = FontWeight.SemiBold,
                fontStyle = FontStyle.Normal,
            ),
        )
        PasswordField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 40.dp, end = 40.dp, bottom = 50.dp)
                .height(70.dp),
            onPasswordChange = {
               confirmPasswd = it
            },
        )
        ForwardBackButtons(
            onClickedBack = {
                navController.popBackStack()
            },
            onClickedForward = {
                Log.d("Data view model", "email = ${viewModel.email}")
                Log.d("Data view model", "passwd = ${viewModel.password}")
                Log.d("Data view model", "name = ${viewModel.name}")
                Log.d("Data view model", "weight = ${viewModel.weight}")
                if(passwd != "" && passwd == confirmPasswd) {
                    viewModel.password = passwd
                    loginViewModel.createUserWithEmailAndPassword(
                        viewModel.email,
                        viewModel.password,
                        User(
                            name = viewModel.name,
                            birthDate = "",
                            gender = viewModel.gender,
                            weight = viewModel.weight,
                            height = viewModel.height,
                            experience = viewModel.experience,
                            avatarUrl = "",
                            userId = "",
                        )
                    ) { mainNavController.popBackStack() }
                }
            },
            backButtonText = stringResource(id = R.string.back),
            forwardButtonText = stringResource(id = R.string.create_profile),
            backButtonWeight = 3f,
            forwardButtonWeight = 4f,
        )
    }
}

@Composable
fun ButtonsList(
    onChange: (String) -> Unit
) {
    val selectedValue = remember { mutableStateOf("") }
    val items = listOf(
        stringResource(id = R.string.novice),
        stringResource(id = R.string.intermediate),
        stringResource(id = R.string.advanced),
        )
    Column(Modifier.padding(horizontal = 40.dp)) {
        items.forEach { item ->
            Surface(
                modifier = Modifier
                    .selectable(
                        selected = (selectedValue.value == item),
                        onClick = {
                            selectedValue.value = item
                            onChange(item)
                        },
                        role = Role.RadioButton
                    )
                    .padding(vertical = 10.dp),
                shape = RoundedCornerShape(20.dp),
                color = if (selectedValue.value == item) DarkBlue else Color.White,
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(70.dp),
                    contentAlignment = Alignment.Center,
                ) {
                    Text(
                        text = item,
                        modifier = Modifier.fillMaxWidth(),
                        style = TextStyle(
                            fontFamily = customFontFamily,
                            fontWeight = FontWeight.Medium,
                            fontStyle = FontStyle.Normal,
                        ),
                        color = if (selectedValue.value == item) Color.White else DarkBlue,
                        textAlign = TextAlign.Center,
                    )
                }
            }
        }
    }
}

@Composable
fun PasswordField(
    modifier: Modifier = Modifier,
    shape: Shape = RoundedCornerShape(20.dp),
    backgroundColor: Color = Color.White,
    onPasswordChange: (String) -> Unit // Nueva función de callback
) {
    var password by rememberSaveable { mutableStateOf("") }
    var passwordVisible by rememberSaveable { mutableStateOf(false) }
    Surface(
        shape = shape,
        color = backgroundColor,
        modifier = modifier
    ) {
        TextField(
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = backgroundColor,
                focusedIndicatorColor = DarkBlue,
                cursorColor = DarkBlue,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                textColor = Blue,
            ),
            value = password,
            onValueChange = {
                password = it
                onPasswordChange(it) // Llama al callback cuando cambia la contraseña
            },
            singleLine = true,
            placeholder = { Text("************") },
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            trailingIcon = {
                val image = if (passwordVisible)
                    Icons.Filled.Visibility
                else Icons.Filled.VisibilityOff

                // Please provide localized description for accessibility services
                val description = if (passwordVisible) "Hide password" else "Show password"

                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(
                        imageVector = image,
                        description,
                        tint = LightBlue,
                    )
                }
            }
        )
    }
}

@Composable
fun ForwardBackButtons(
    onClickedBack: () -> Unit = {},
    onClickedForward: () -> Unit = {},
    backButtonText: String = stringResource(id = R.string.back),
    forwardButtonText: String = stringResource(id = R.string.forward),
    backButtonWeight: Float = 1f,
    forwardButtonWeight: Float = 1f,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        LoginButton(
            modifier = Modifier
                .weight(backButtonWeight)
                .padding(start = 40.dp, end = 10.dp)
                .height(70.dp),
            onClicked = {
                onClickedBack()
            },
            text = backButtonText,
            backgroundColor = Color(0xFFE1E1E1),
            colorText = DarkBlue,
            borderColor = Color.Transparent,
            style = TextStyle(
                fontFamily = customFontFamily,
                fontWeight = FontWeight.Normal,
                fontStyle = FontStyle.Normal,
            )
        )
        LoginButton(
            modifier = Modifier
                .weight(forwardButtonWeight)
                .padding(end = 40.dp, start = 10.dp)
                .height(70.dp),
            onClicked = {
                onClickedForward()
            },
            text = forwardButtonText,
            backgroundColor = Red,
            colorText = Color.White,
            borderColor = Color.Transparent,
            style = TextStyle(
                fontFamily = customFontFamily,
                fontWeight = FontWeight.Medium,
                fontStyle = FontStyle.Normal,
            )
        )
    }
}
@Composable
fun RadioButtonGroup(
    onChange: (String) -> Unit
) {
    val selectedValue = remember { mutableStateOf("") }

    val isSelectedItem: (String) -> Boolean = { selectedValue.value == it }
    val onChangeState: (String) -> Unit = {
        selectedValue.value = it
        onChange(it)
    }

    val items = listOf(
        stringResource(id = R.string.men),
        stringResource(id = R.string.women),
        stringResource(id = R.string.not_ask))
    Column(Modifier.padding(horizontal = 40.dp)) {
        items.forEach { item ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .selectable(
                        selected = isSelectedItem(item),
                        onClick = { onChangeState(item) },
                        role = Role.RadioButton
                    )
                    .padding(8.dp)
            ) {
                RadioButton(
                    selected = isSelectedItem(item),
                    onClick = null,
                    colors = RadioButtonDefaults.colors(
                        selectedColor = DarkBlue,
                        unselectedColor = DarkBlue,
                        disabledColor = Color.White
                    ),
                )
                Text(
                    text = item,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 10.dp),
                    color = DarkBlue,
                    fontSize = 18.sp,
                    style = TextStyle(
                        fontFamily = customFontFamily,
                        fontWeight = FontWeight.Light,
                        fontStyle = FontStyle.Normal,
                    )
                )
            }
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