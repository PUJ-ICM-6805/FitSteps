package com.example.fitsteps.screens

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
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Surface
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.fitsteps.R
import com.example.fitsteps.navigation.CUSTOM_ROUTINE_ROUTE
import com.example.fitsteps.ui.theme.Blue
import com.example.fitsteps.ui.theme.DarkBlue
import com.example.fitsteps.ui.theme.LightBlue
import com.example.fitsteps.ui.theme.Red
import com.example.fitsteps.ui.theme.White
import com.example.fitsteps.ui.theme.customFontFamily

@Composable
fun ExerciseScreen(navController: NavHostController, rootNavController:NavHostController) {
    var showCreateRoutineFrame by remember { mutableStateOf(false) }
    if (showCreateRoutineFrame) {
        CreateNewRoutineFrame(
            show = showCreateRoutineFrame,
            setShow = { showCreateRoutineFrame = it }
        )
    }
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
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
                HamburgersDropList(navController = navController, rootNavController = rootNavController)
            }
        }
        item {
            Spacer(modifier = Modifier.height(40.dp))
        }
        item {
            Text(
                text = stringResource(id = R.string.training),
                modifier = Modifier.padding(horizontal = 15.dp, vertical = 0.dp),
                style = TextStyle(
                    fontFamily = customFontFamily,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 30.sp,
                    fontStyle = FontStyle.Normal,
                    color = DarkBlue,
                )
            )
        }
        item {

            Text(
                text = stringResource(id = R.string.my_training),
                modifier = Modifier.padding(horizontal = 15.dp, vertical = 5.dp),
                style = TextStyle(
                    fontFamily = customFontFamily,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 22.sp,
                    fontStyle = FontStyle.Normal,
                    color = DarkBlue,
                )
            )
        }
        item {
            WeekButtonsRow()
        }
        item {
            Text(
                text = stringResource(id = R.string.my_collection),
                modifier = Modifier.padding(horizontal = 15.dp, vertical = 5.dp),
                style = TextStyle(
                    fontFamily = customFontFamily,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 22.sp,
                    fontStyle = FontStyle.Normal,
                    color = DarkBlue,
                )
            )
        }
        item {
            LazyRow(
                modifier = Modifier
                    .padding(15.dp)
            ) {
                item {
                    RoutineCard()
                }
                item {
                    RoutineCard()
                }
            }
        }
        item {
            LargeButtons(
                text = stringResource(id = R.string.add_new_routine),
                onClick = { showCreateRoutineFrame = true },
                modifier = Modifier
                    .padding(horizontal = 15.dp, vertical = 20.dp)
                    .fillMaxWidth(),
                needIcon = true,
                icon = R.drawable.ic_add,
                iconColor = Color(0xFFF4F4F4),
            )
        }
        item {
            LargeButtons(
                text = stringResource(id = R.string.add_personalized_routine),
                onClick = {
                    navController.navigate(CUSTOM_ROUTINE_ROUTE)
                },
                color = androidx.compose.material.MaterialTheme.colors.background,
                borderColor = Red,
                textColor = Red,
                modifier = Modifier
                    .padding(horizontal = 15.dp, vertical = 5.dp)
                    .fillMaxWidth(),
            )
        }
    }
}

@Composable
fun RoutineCard() {
    Card(
        modifier = Modifier
            .height(190.dp)
            .width(300.dp)
            .padding(end = 20.dp),
        shape = RoundedCornerShape(20.dp),
        backgroundColor = Color.White,
    ){
        Box(
            modifier = Modifier
                .fillMaxSize(),
        ) {
            Image(
                painter = painterResource(id = R.drawable.woman),
                contentDescription = "",
                contentScale = ContentScale.Crop,
                alignment = Alignment.BottomCenter,
            )
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxSize(),
            ) {
                Column(
                    modifier = Modifier
                        .weight(3f)
                        .fillMaxHeight(),
                    verticalArrangement = Arrangement.Bottom,
                ) {
                    Text(
                        text = "Full body", // TODO: Change for the actual routine type
                        modifier = Modifier.padding(horizontal = 15.dp, vertical = 0.dp),
                        style = TextStyle(
                            fontFamily = customFontFamily,
                            fontWeight = FontWeight.Light,
                            fontSize = 22.sp,
                            fontStyle = FontStyle.Normal,
                            color = Color.White,
                        )
                    )
                    Text(
                        text = "Pérdida de grasa", //TODO: Change for the actual activity
                        modifier = Modifier.padding(start = 15.dp),
                        style = TextStyle(
                            fontFamily = customFontFamily,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 20.sp,
                            fontStyle = FontStyle.Normal,
                            color = Color.White,
                        )
                    )
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight()
                            .padding(0.dp),
                    ) {
                        Text(
                            text = stringResource(id = R.string.progress),
                            modifier = Modifier.padding(horizontal = 15.dp, vertical = 0.dp),
                            style = TextStyle(
                                fontFamily = customFontFamily,
                                fontWeight = FontWeight.Medium,
                                fontSize = 12.sp,
                                fontStyle = FontStyle.Normal,
                                color = Color(0xFFF4F4F4),
                            )
                        )
                        Text(
                            text = "5%", //TODO: Change for the actual progress
                            modifier = Modifier.padding(start = 15.dp),
                            style = TextStyle(
                                fontFamily = customFontFamily,
                                fontWeight = FontWeight.Light,
                                fontSize = 12.sp,
                                fontStyle = FontStyle.Italic,
                                color = Color(0xFFF4F4F4),
                            )
                        )
                    }
                    RoundedLinearProgressIndicator(
                        modifier = Modifier
                            .padding(top = 0.dp, bottom = 10.dp, start = 15.dp)
                            .height(10.dp),
                        progress = 0.1f,
                        progressColor = Blue,
                        backgroundColor = LightBlue,
                    )
                }
                Box(
                    contentAlignment = Alignment.TopEnd,
                    modifier = Modifier
                        .weight(1f),
                ) {
                    Text(
                        text = stringResource(id = R.string.edit),
                        modifier = Modifier.padding(horizontal = 15.dp, vertical = 10.dp),
                        style = TextStyle(
                            fontFamily = customFontFamily,
                            fontWeight = FontWeight.Light,
                            fontSize = 12.sp,
                            fontStyle = FontStyle.Normal,
                            color = Color.White,
                        )
                    )
                }
            }
        }
    }
}
@Composable
fun WeekButtonsRow() {
    val selectedValue = remember { mutableStateOf("") }
    val items = listOf(
        "monday", "tuesday", "wednesday", "thursday", "friday", "saturday", "sunday"
    )
    Row(
        modifier = Modifier
            .padding(10.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {

        items.forEach { item ->
            Box(
                modifier = Modifier
                    .wrapContentSize()
                    .weight(1f),
                contentAlignment = Alignment.Center,
            ) {
                androidx.compose.material3.Surface(
                    modifier = Modifier
                        .selectable(
                            selected = (selectedValue.value == item),
                            onClick = { selectedValue.value = item },
                            role = Role.RadioButton
                        )
                        .wrapContentWidth()
                        .height(45.dp),
                    shape = RoundedCornerShape(5.dp),
                    color = if (selectedValue.value == item) Blue else LightBlue,
                ) {
                    Text(
                        text = when(item) {
                            "monday" -> {
                                stringResource(id = R.string.monday_letter)
                            }
                            "tuesday" -> {
                                stringResource(id = R.string.tuesday_letter)
                            }
                            "wednesday" -> {
                                stringResource(id = R.string.wednesday_letter)
                            }
                            "thursday" -> {
                                stringResource(id = R.string.thursday_letter)
                            }
                            "friday" -> {
                                stringResource(id = R.string.friday_letter)
                            }
                            "saturday" -> {
                                stringResource(id = R.string.saturday_letter)
                            }
                            else -> {
                                stringResource(id = R.string.sunday_letter)
                            }
                        },
                        color = if (selectedValue.value == item) LightBlue else Blue,
                        modifier = Modifier
                            .padding(horizontal = 10.dp, vertical = 10.dp)
                            .width(20.dp)
                            .height(45.dp),
                        style = TextStyle(
                            fontFamily = customFontFamily,
                            fontWeight = FontWeight.Medium,
                            fontSize = 16.sp,
                            fontStyle = FontStyle.Normal,
                            textAlign = TextAlign.Center,
                        )
                    )
                }
            }
        }
    }
}

@Composable
fun LargeButtons(
    modifier: Modifier = Modifier,
    text: String,
    icon: Int = R.drawable.ic_add,
    needIcon: Boolean = false,
    iconColor: Color = Color.White,
    onClick: () -> Unit,
    style: TextStyle = TextStyle(
        fontFamily = customFontFamily,
        fontWeight = FontWeight.Medium,
        fontStyle = FontStyle.Normal,
        textAlign = TextAlign.Center,
        fontSize = 20.sp,
    ),
    color: Color = Red,
    borderColor: Color = Color.Transparent,
    textColor: Color = Color.White,
    shape: Shape = RoundedCornerShape(20.dp),
) {
    var clicked by remember { mutableStateOf(false) }
    Surface(
        modifier = modifier
            .height(70.dp)
            .clickable {
                clicked = !clicked
                onClick()
            },
        shape = shape,
        elevation = 2.dp,
        color = color,
        border = BorderStroke(
            width = 1.dp,
            color = borderColor
        ),
    ) {
        Box(
            modifier = Modifier
                .width(IntrinsicSize.Max)
                .height(IntrinsicSize.Max),
            contentAlignment = Alignment.Center
        ) {
            if(needIcon) {
                Icon(
                    painter = painterResource(id = icon),
                    contentDescription = "",
                    modifier = Modifier
                        .padding(horizontal = 15.dp, vertical = 10.dp)
                        .size(20.dp)
                        .align(Alignment.CenterStart),
                    tint = textColor,
                )
            }
            Text(
                text = text,
                color = textColor,
                style = style,
                modifier = Modifier
                    .padding(horizontal = 8.dp, vertical = 5.dp),
            )
        }
    }
}

@Composable
@Preview
fun ExerciseScreenPreview() {
    ExerciseScreen(navController = rememberNavController(), rootNavController = rememberNavController())
}