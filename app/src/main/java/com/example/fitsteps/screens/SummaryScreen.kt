package com.example.fitsteps.screens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Surface
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.fitsteps.R
import com.example.fitsteps.authentication.DatabaseUtils
import com.example.fitsteps.authentication.User
import com.example.fitsteps.firebaseData.firebaseOwnProgramData.Routine
import com.example.fitsteps.firebaseData.firebaseOwnProgramData.TrainingProgram
import com.example.fitsteps.firebaseData.firebaseOwnProgramData.TrainingProgramViewModel
import com.example.fitsteps.navigation.Screen
import com.example.fitsteps.screens.social.UserContactsViewModel
import com.example.fitsteps.ui.theme.Blue
import com.example.fitsteps.ui.theme.DarkBlue
import com.example.fitsteps.ui.theme.LightBlue
import com.example.fitsteps.ui.theme.Red
import com.example.fitsteps.ui.theme.White
import com.example.fitsteps.ui.theme.customFontFamily
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


@Composable
fun SummaryScreen(
    navController: NavHostController,
    rootNavController: NavHostController,
    trainingProgramViewModel: TrainingProgramViewModel,
    usuarios: UserContactsViewModel = remember {
        UserContactsViewModel()
    },
) {

    val ownTrainingProgramsListState =
        trainingProgramViewModel.ownProgramList.observeAsState(initial = emptyList())
    val ownTrainingProgramsList = ownTrainingProgramsListState.value

    val userid = Firebase.auth.currentUser?.uid
    val userEmail = Firebase.auth.currentUser?.email
    val usuario = remember { mutableStateOf(User()) } //obligatorio
    val onlineRef = FirebaseFirestore.getInstance().collection("users").document(userid.toString())
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
                HamburgersDropList(
                    navController = navController,
                    rootNavController = rootNavController
                )
            }
        }
        item {
            Spacer(modifier = Modifier.height(40.dp))
        }
        item {
            Box(
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
                    text = usuario.value.user_name,
                    modifier = Modifier.padding(horizontal = 15.dp, vertical = 0.dp),
                    style = TextStyle(
                        fontFamily = customFontFamily,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 35.sp,
                        fontStyle = FontStyle.Normal,
                        color = DarkBlue,
                    )
                )
                //DropDownMenu()
            }
        }
        /*item {
            WeeklySummary()
        }*/
        /*item {
            StatsCard()
        }*/
        item {
            if (ownTrainingProgramsList.isNotEmpty()) {
                CardDayActivity(ownTrainingProgramsList, trainingProgramViewModel, navController)
            }
        }
        item {
            //usamos usuarios para llenar la información de SocialInfoCard()
            //SocialInfoCard()
            SocialInfoCard(usuarios)
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
                        painter = when (item) {
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

fun getActualDay(): String {
    val formatDay = SimpleDateFormat("EEEE", Locale("es", "ES"))
    val actualDay = Date()
    return formatDay.format(actualDay)
}

@Composable
fun CardDayActivity(
    trainingProgramList: List<TrainingProgram>,
    trainingProgramViewModel: TrainingProgramViewModel,
    navController: NavHostController
) {
    Log.d("TrainingProgramList", trainingProgramList.toString())
    val actualDay = getActualDay()
    var routineName by remember { mutableStateOf("") }
    var programImage by remember { mutableStateOf("") }
    var routineEx by remember { mutableStateOf(Routine()) }
    Log.d("Actual day", actualDay)
    outer@ for (trainingProgram in trainingProgramList) {
        for (routine in trainingProgram.routines) {
            routineEx = routine
            val normalizedDays = routine.days.lowercase().replace(" ", "")
            if (normalizedDays.contains(actualDay)) {
                routineName = routine.name
                programImage = trainingProgram.image
                break@outer
            }
        }
    }
    Log.d("RoutineName", routineName)
    Log.d("RoutineImage", programImage)
    if (routineName == "") {
        routineName = "Descanso"
        programImage =
            "https://firebasestorage.googleapis.com/v0/b/fitsteps-eb0ef.appspot.com/o/images%2Fsleep-training1.png?alt=media&token=32ee7de2-585e-41e4-aac7-e0b86f057245"
    }
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .padding(15.dp)
            .clickable {
                if (routineName != "Descanso" && routineEx.name == routineName) {
                    trainingProgramViewModel.setSelectedRoutine(routineEx)
                    navController.navigate(Screen.RoutineScreen.route)
                }
            },
        shape = RoundedCornerShape(20.dp),
        backgroundColor = Color.White,
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center,
        ) {
            Image(
                painter = rememberAsyncImagePainter(model = programImage),
                contentDescription = "",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize(),
            )
            Column(
                modifier = Modifier.wrapContentSize(),
            ) {
                Text(
                    text = stringResource(id = R.string.today),
                    modifier = Modifier
                        .padding(horizontal = 15.dp, vertical = 0.dp)
                        .background(
                            Color.White,
                            RoundedCornerShape(
                                topStart = 5.dp,
                                bottomStart = 0.dp,
                                topEnd = 5.dp,
                                bottomEnd = 0.dp
                            )
                        )
                        .padding(horizontal = 10.dp),
                    style = TextStyle(
                        fontFamily = customFontFamily,
                        fontWeight = FontWeight.Light,
                        fontSize = 22.sp,
                        fontStyle = FontStyle.Normal,
                        color = Color.Black,
                    )
                )
                Text(
                    text = routineName,
                    modifier = Modifier
                        .padding(horizontal = 15.dp, vertical = 0.dp)
                        .background(
                            Color.White,
                            RoundedCornerShape(
                                topStart = 0.dp,
                                bottomStart = 5.dp,
                                topEnd = 5.dp,
                                bottomEnd = 5.dp
                            )
                        )
                        .padding(horizontal = 10.dp),
                    style = TextStyle(
                        fontFamily = customFontFamily,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 20.sp,
                        fontStyle = FontStyle.Normal,
                        color = Color.Black,
                    )
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 15.dp, top = 10.dp),
                ) {
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
) {
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
fun SocialInfoCard(usuarios: UserContactsViewModel) {
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
            Spacer(
                modifier = Modifier
                    .height(2.dp)
                    .fillMaxWidth()
                    .background(LightBlue)
            )

            // Iterate over the list of users and create BadgesRow for each user
            if (usuarios.usersByContacts.isNotEmpty()) {
                for (user in usuarios.usersByContacts) {
                    if (user.active) {
                        Log.d("Usuario", user.toString())
                        BadgesRow(
                            text = "Está en linea, comunícate con tu gymbro", // Use the actual description from the user
                            name = user.user_name, // Use the actual user name
                            img = user.avatar // Use the actual profile picture from the user
                        )

                        // Add a spacer between each BadgesRow
                        Spacer(
                            modifier = Modifier
                                .height(2.dp)
                                .fillMaxWidth()
                                .background(LightBlue)
                        )
                    }
                }
                //si todos los usuarios están inactivos
                if (usuarios.usersByContacts.all { !it.active }) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(15.dp),
                    ) {
                        Column {
                            Text(
                                text = "No hay nadie conectado en este momento 😔",
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
                                text = "Deberías entonces entrenar solo, no te preocupes, puedes hacerlo",
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
            } else {
                Text(
                    text = "No tienes contactos aún 🫠",
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
    }
}


@Composable
fun BadgesRow(
    text: String,
    img: String,
    name: String,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(15.dp),
    ) {
        Image(
            painter = userProfileAvatar(img),
            contentDescription = "",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(80.dp)
                .clip(CircleShape)
                .fillMaxSize(),
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

    var textfieldSize by remember { mutableStateOf(Size.Zero) }

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
    ) {
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
                    Icon(
                        icon, "contentDescription",
                        Modifier.clickable { expanded = !expanded },
                        tint = Color.White
                    )
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

    var textfieldSize by remember { mutableStateOf(Size.Zero) }

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
    ) {
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
                    Icon(
                        icon, "contentDescription",
                        Modifier.clickable { expanded = !expanded },
                        tint = Color.White
                    )
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

