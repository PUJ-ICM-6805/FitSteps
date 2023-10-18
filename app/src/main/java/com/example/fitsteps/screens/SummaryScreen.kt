package com.example.fitsteps.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import com.example.fitsteps.ui.theme.White
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.fitsteps.R
import com.example.fitsteps.authentication.DatabaseUtils
import com.example.fitsteps.authentication.User
import com.example.fitsteps.ui.theme.Blue
import com.example.fitsteps.ui.theme.DarkBlue
import com.example.fitsteps.ui.theme.LightBlue
import com.example.fitsteps.ui.theme.Red
import com.example.fitsteps.ui.theme.customFontFamily


@Composable
fun SummaryScreen(navController: NavHostController, rootNavController: NavHostController) {
    val usuario = remember { mutableStateOf(User()) } //obligatorio

    LaunchedEffect(userid) {
        userid?.let { uid ->
            val userData = DatabaseUtils().getUserDataByUID(uid)
            if (userData != null) {
                usuario.value = userData
            }
        }
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
            Box (
                contentAlignment = Alignment.BottomStart
            ) {
                Text(
                    text = stringResource(id = R.string.hello),
                    modifier = Modifier.padding(horizontal = 15.dp, vertical = 0.dp),
                    style = TextStyle(
                        fontFamily = customFontFamily,
                        fontWeight = FontWeight.Light,
                        fontSize = 30.sp,
                        fontStyle = FontStyle.Normal,
                        color = DarkBlue
                    )
                )
            }
        }
        item {
            Row(
               modifier = Modifier
                   .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = usuario.value.user_name, //TODO: Change for the user name
                    modifier = Modifier.padding(horizontal = 15.dp, vertical = 0.dp),
                    style = TextStyle(
                        fontFamily = customFontFamily,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 35.sp,
                        fontStyle = FontStyle.Normal,
                        color = DarkBlue,
                    )
                )
                DropDownMenu()
            }
        }
        item {
            WeeklySummary()
        }
        item {
            StatsCard()
        }
        item {
            CardDayActivity()
        }
        item {
            SocialInfoCard()
        }
        item {
            Spacer(modifier = Modifier.height(80.dp))
        }
    }
}

@Composable
fun WeeklySummary() {
    Card(
        modifier = Modifier
            .fillMaxSize()
            .padding(15.dp),
        shape = RoundedCornerShape(15.dp),
        backgroundColor = Color.White,
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Row() {
                Text(
                    text = stringResource(id = R.string.this_week),
                    modifier = Modifier.padding(horizontal = 15.dp, vertical = 15.dp),
                    style = TextStyle(
                        fontFamily = customFontFamily,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 20.sp,
                        fontStyle = FontStyle.Normal,
                        color = DarkBlue,
                    )
                )
                DropDownMenu2()
            }
            WeeklyInfoRows()
            WeeklyInfoRows(
                icon = R.drawable.ic_exercise,
                text = R.string.lifted_weight
            )
            WeeklyInfoRows(
                icon = R.drawable.ic_time,
                text = R.string.average_time
            )
            WeeklyInfoRows(
                icon = R.drawable.ic_calendar,
                text = R.string.total_days
            )
        }
    }
}

@Composable
fun StatsCard() {
    Card (
        modifier = Modifier
            .fillMaxSize()
            .padding(15.dp),
        shape = RoundedCornerShape(15.dp),
        backgroundColor = Color.White,
    ){
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Text(
                text = stringResource(id = R.string.stats),
                modifier = Modifier.padding(top = 15.dp, bottom = 0.dp, start = 15.dp, end = 15.dp),
                style = TextStyle(
                    fontFamily = customFontFamily,
                    fontWeight = FontWeight.Medium,
                    fontSize = 20.sp,
                    fontStyle = FontStyle.Normal,
                    color = DarkBlue,
                )
            )
            IconsRow()
            Image(
                painter = painterResource(id = R.drawable.stats),
                contentDescription = "",
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 15.dp, vertical = 5.dp),
                contentScale = ContentScale.FillWidth,
                alignment = Alignment.BottomCenter,
            )
        }
    }
}

@Composable
fun IconsRow() {
    val selectedValue = remember { mutableStateOf("") }
    val items = listOf(
        "running", "weight", "time", "days"
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
                    ),
                shape = RoundedCornerShape(5.dp),
                color = if (selectedValue.value == item) Red else LightBlue,
            ) {

                    Icon(
                        painter = when(item) {
                            "running" -> {
                                painterResource(id = R.drawable.ic_running)
                            }
                            "weight" -> {
                                painterResource(id = R.drawable.ic_exercise)
                            }
                            "time" -> {
                                painterResource(id = R.drawable.ic_time)
                            }
                            else -> {
                                painterResource(id = R.drawable.ic_calendar)
                        }
                    },
                    contentDescription = "",
                    tint = if (selectedValue.value == item) Color.White else Blue,
                    modifier = Modifier
                        .padding(horizontal = 15.dp, vertical = 5.dp)
                        .size(35.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun CardDayActivity() {
    Card(
        modifier = Modifier
            .fillMaxSize()
            .padding(15.dp),
        shape = RoundedCornerShape(20.dp),
        backgroundColor = Color.White,
    ){
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.BottomStart,
        ) {
            Image(
                painter = painterResource(id = R.drawable.exercise_default),
                contentDescription = "",
                modifier = Modifier
                    .fillMaxSize(),
                contentScale = ContentScale.Crop,
                alignment = Alignment.BottomCenter,
            )
            Column {
                Text(
                    text = stringResource(id = R.string.today),
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
                    text = "Día de pierna", //TODO: Change for the actual activity
                    modifier = Modifier.padding(horizontal = 15.dp, vertical = 0.dp),
                    style = TextStyle(
                        fontFamily = customFontFamily,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 20.sp,
                        fontStyle = FontStyle.Normal,
                        color = Color.White,
                    )
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 15.dp, bottom = 10.dp),
                ) {
                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = White,
                        modifier = Modifier
                            .padding(end = 8.dp),
                    ) {
                        Box(
                            contentAlignment = Alignment.Center,
                        ) {
                            Text(
                                text = "Día 1", //TODO: Change for the actual day
                                style = TextStyle(
                                    fontFamily = customFontFamily,
                                    fontWeight = FontWeight.Light,
                                    fontSize = 16.sp,
                                    fontStyle = FontStyle.Normal,
                                    color = DarkBlue,
                                ),
                                modifier = Modifier
                                    .padding(horizontal = 8.dp, vertical = 5.dp),
                            )
                        }
                    }
                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = White,
                    ) {
                        Box(
                            contentAlignment = Alignment.Center,
                        ) {
                            Text(
                                text = "Principiante", //TODO: Change for the actual category
                                style = TextStyle(
                                    fontFamily = customFontFamily,
                                    fontWeight = FontWeight.Light,
                                    fontSize = 16.sp,
                                    fontStyle = FontStyle.Normal,
                                    color = DarkBlue,
                                ),
                                modifier = Modifier
                                    .padding(horizontal = 8.dp, vertical = 5.dp),
                            )
                        }
                    }
                }

            }
        }
    }
}

@Composable
fun WeeklyInfoRows(
    number: String = "0",
    icon: Int = R.drawable.ic_running,
    text: Int = R.string.km,
){
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 0.dp, vertical = 10.dp),
    ) {
        Icon(
            painter = painterResource(id = icon),
            contentDescription = "",
            tint = Red,
            modifier = Modifier
                .padding(horizontal = 20.dp)
                .size(40.dp)
        )
        Surface(
            color = White,
            modifier = Modifier
                .width(50.dp)
                .height(40.dp),
            shape = RoundedCornerShape(10.dp),
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = number,
                    style = TextStyle(
                        fontFamily = customFontFamily,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 16.sp,
                        fontStyle = FontStyle.Normal,
                        color = DarkBlue,
                    ),
                )
            }
        }
        Text(
            text = stringResource(id = text),
            modifier = Modifier.padding(horizontal = 15.dp, vertical = 0.dp),
            style = TextStyle(
                fontFamily = customFontFamily,
                fontWeight = FontWeight.Light,
                fontSize = 16.sp,
                fontStyle = FontStyle.Normal,
                color = DarkBlue,
            )
        )

    }
}

@Composable
fun SocialInfoCard() {
    Card(
        modifier = Modifier
            .fillMaxSize()
            .padding(15.dp),
        shape = RoundedCornerShape(20.dp),
        backgroundColor = Color.White,
    ) {
        Column(
            modifier = Modifier
        ) {
            Text(
                text = stringResource(id = R.string.social),
                modifier = Modifier.padding(top = 15.dp, bottom = 0.dp, start = 15.dp, end = 15.dp),
                style = TextStyle(
                    fontFamily = customFontFamily,
                    fontWeight = FontWeight.Medium,
                    fontSize = 20.sp,
                    fontStyle = FontStyle.Normal,
                    color = DarkBlue,
                )
            )
            Spacer(modifier = Modifier
                .height(2.dp)
                .fillMaxWidth()
                .background(LightBlue))
            BadgesRow(
                text = "Alcanzó nuevo récord", //TODO: Change for the actual description
                name = "Sara", //TODO: Change for the user name
                img = R.drawable.profile_example_one,
            )
            Spacer(modifier = Modifier
                .height(2.dp)
                .fillMaxWidth()
                .background(LightBlue))
            BadgesRow(
                text = "Superó su mejor tiempo", //TODO: Change for the actual description
                name = "Felipe", //TODO: Change for the user name
                img = R.drawable.profile_example_two,
            )

        }
    }
}

@Composable
fun BadgesRow(
    icon: Int = R.drawable.ic_badge,
    text: String = "",
    img: Int = R.drawable.profile_example_one,
    name: String = "",
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(15.dp),
    ) {
        Icon(
            painter = painterResource(icon),
            contentDescription = "",
            tint = Red,
            modifier = Modifier
                .padding(end = 10.dp)
                .size(50.dp)
        )
        Image(
            painter = painterResource(img),
            contentDescription = "",
            modifier = Modifier
                .size(50.dp)
                .clip(CircleShape),
        )
        Column {
            Text(
                text = name,
                modifier = Modifier.padding(horizontal = 15.dp, vertical = 0.dp),
                style = TextStyle(
                    fontFamily = customFontFamily,
                    fontWeight = FontWeight.Medium,
                    fontSize = 20.sp,
                    fontStyle = FontStyle.Normal,
                    color = DarkBlue,
                )
            )
            Text(
                text = text,
                modifier = Modifier.padding(horizontal = 15.dp, vertical = 0.dp),
                style = TextStyle(
                    fontFamily = customFontFamily,
                    fontWeight = FontWeight.Light,
                    fontSize = 14.sp,
                    fontStyle = FontStyle.Normal,
                    color = DarkBlue,
                )
            )
        }

    }
}

@Composable
fun DropDownMenu() {
    val suggestions = listOf(
        stringResource(id = R.string.casual),
        stringResource(id = R.string.calisthenics),
        stringResource(id = R.string.powerlifter),
    )
    var expanded by remember { mutableStateOf(false) }
    var selectedText by remember { mutableStateOf("") }

    var textfieldSize by remember { mutableStateOf(Size.Zero)}

    val icon = if (expanded)
        painterResource(id = R.drawable.ic_arrow_up)
    else
        painterResource(id = R.drawable.ic_arrow_down)

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 0.dp, start = 10.dp, end = 15.dp, bottom = 0.dp)
            .background(DarkBlue, RoundedCornerShape(30.dp))
            .wrapContentHeight(),
        shape = RoundedCornerShape(10.dp),
    ){
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            contentAlignment = Alignment.CenterStart,
        ) {
            TextField(
                value = selectedText,
                onValueChange = { selectedText = it },
                readOnly = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .onGloballyPositioned { coordinates ->
                        //This value is used to assign to the DropDown the same width
                        textfieldSize = coordinates.size.toSize()
                    }
                    .background(DarkBlue, RoundedCornerShape(30.dp)),
                trailingIcon = {
                    Icon(icon,"contentDescription",
                        Modifier.clickable { expanded = !expanded },
                        tint = Color.White)
                },
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = DarkBlue,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,
                    textColor = Color.White,
                    trailingIconColor = Color.White,
                ),
                textStyle = TextStyle(
                    fontFamily = customFontFamily,
                    fontWeight = FontWeight.Normal,
                    fontSize = 16.sp,
                    fontStyle = FontStyle.Italic,
                    color = Color.White,
                ),
            )
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier
                    .width(with(LocalDensity.current) { textfieldSize.width.toDp() })
                    .background(Blue)
                    .border(10.dp, Blue)
            ) {
                suggestions.forEach { label ->
                    DropdownMenuItem(
                        onClick = {
                            selectedText = label
                            expanded = false
                        },
                        modifier = Modifier
                            .background(Blue)
                            .border(1.dp, Color.White),
                    ) {
                        Text(
                            text = label,
                            style = TextStyle(
                                fontFamily = customFontFamily,
                                fontWeight = FontWeight.Light,
                                fontSize = 12.sp,
                                fontStyle = FontStyle.Normal,
                                color = Color.White,
                            ),
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun DropDownMenu2() {
    val suggestions = listOf(
        stringResource(id = R.string.last_week),
        stringResource(id = R.string.two_weeks_ago),
        stringResource(id = R.string.three_weeks_ago),
        stringResource(id = R.string.four_weeks_ago),
        stringResource(id = R.string.five_weeks_ago),
    )
    var expanded by remember { mutableStateOf(false) }
    var selectedText by remember { mutableStateOf("") }

    var textfieldSize by remember { mutableStateOf(Size.Zero)}

    val icon = if (expanded)
        painterResource(id = R.drawable.ic_arrow_up)
    else
        painterResource(id = R.drawable.ic_arrow_down)

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 10.dp, start = 10.dp, end = 15.dp, bottom = 10.dp)
            .background(DarkBlue, RoundedCornerShape(30.dp))
            .height(50.dp),
        shape = RoundedCornerShape(10.dp),
    ){
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            contentAlignment = Alignment.CenterStart,
        ) {
            TextField(
                value = selectedText,
                onValueChange = { selectedText = it },
                readOnly = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .onGloballyPositioned { coordinates ->
                        //This value is used to assign to the DropDown the same width
                        textfieldSize = coordinates.size.toSize()
                    }
                    .background(DarkBlue, RoundedCornerShape(30.dp)),
                trailingIcon = {
                    Icon(icon,"contentDescription",
                        Modifier.clickable { expanded = !expanded },
                        tint = Color.White)
                },
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = DarkBlue,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,
                    textColor = Color.White,
                    trailingIconColor = Color.White,
                ),
                textStyle = TextStyle(
                    fontFamily = customFontFamily,
                    fontWeight = FontWeight.Normal,
                    fontSize = 10.sp,
                    fontStyle = FontStyle.Italic,
                    color = Color.White,
                ),
            )
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier
                    .width(with(LocalDensity.current) { textfieldSize.width.toDp() })
                    .background(Blue)
                    .border(10.dp, Blue)
            ) {
                suggestions.forEach { label ->
                    DropdownMenuItem(
                        onClick = {
                            selectedText = label
                            expanded = false
                        },
                        modifier = Modifier
                            .background(Blue)
                            .border(1.dp, Color.White),
                    ) {
                        Text(
                            text = label,
                            style = TextStyle(
                                fontFamily = customFontFamily,
                                fontWeight = FontWeight.Light,
                                fontSize = 12.sp,
                                fontStyle = FontStyle.Normal,
                                color = Color.White,
                            ),
                        )
                    }
                }
            }
        }
    }

}

@Composable
@Preview
fun SummaryScreenPreview() {
    SummaryScreen(navController = rememberNavController(), rootNavController = rememberNavController())
}

