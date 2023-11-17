package com.example.fitsteps.screens.training

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.fitsteps.R
import com.example.fitsteps.firebaseData.firebaseOwnProgramData.Routine
import com.example.fitsteps.firebaseData.firebaseOwnProgramData.TrainingProgramViewModel
import com.example.fitsteps.screens.training.trainingMainScreen.WeekButtonsRow
import com.example.fitsteps.ui.theme.Blue
import com.example.fitsteps.ui.theme.DarkBlue
import com.example.fitsteps.ui.theme.LightBlue
import com.example.fitsteps.ui.theme.customFontFamily

@Composable
fun CreateNewRoutineFrame(
    style: TextStyle = TextStyle(
        fontFamily = customFontFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 35.sp,
        color = Color.White,
        textAlign = TextAlign.Center,
    ),
    trainingProgramViewModel: TrainingProgramViewModel,
    setShow: (Boolean, Boolean) -> Unit,
){
    var input_name by remember {
        mutableStateOf("")
    }
    var selectedDays by remember { mutableStateOf<List<String>>(emptyList()) }

    Dialog(
        onDismissRequest = {},
        properties = DialogProperties(
            dismissOnBackPress = false,
            dismissOnClickOutside = false,
            usePlatformDefaultWidth = false,
        ),
    ) {
        Card(
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Transparent)
                .height(400.dp)
                .padding(horizontal = 20.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color(0xFFF4F4F4),
            ),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 3.dp
            ),
        ) {
            Box(
                modifier = Modifier
                    .wrapContentHeight()
                    .fillMaxWidth(),
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight(),
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(2f),
                    ){
                        Image(
                            painter = painterResource(id = R.drawable.frame_create_routine_foto),
                            contentDescription = "RoutineCompleted",
                            modifier = Modifier
                                .fillMaxSize(),
                            contentScale = ContentScale.Crop,
                            alignment = Alignment.Center,
                        )
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(Color(0x70000000)),
                        )
                        Column(
                            modifier = Modifier
                                .fillMaxSize(),
                            verticalArrangement = Arrangement.Bottom,
                        ) {
                            Text(
                                text = stringResource(id = R.string.create_routine),
                                softWrap = false,
                                style = TextStyle(
                                    fontFamily = customFontFamily,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 20.sp,
                                    color = Color(0xFFF4F4F4),
                                    textAlign = TextAlign.Start,
                                ),
                                modifier = Modifier
                                    .padding(horizontal = 20.dp, vertical = 0.dp),
                            )
                        }
                    }
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight()
                            .weight(4f),
                    ){
                        Column(
                            modifier = Modifier
                                .fillMaxWidth(),
                            verticalArrangement = Arrangement.SpaceEvenly,
                        ) {
                            Text(
                                text = stringResource(id = R.string.name_ex),
                                style = TextStyle(
                                    fontFamily = customFontFamily,
                                    fontWeight = FontWeight.Medium,
                                    fontSize = 12.sp,
                                    color = DarkBlue,
                                    textAlign = TextAlign.Start,
                                ),
                                modifier = Modifier
                                    .padding(horizontal = 20.dp, vertical = 5.dp),
                            )
                            Surface(
                                shape = RoundedCornerShape(10.dp),
                                color = Color.White,
                                elevation = 3.dp,
                                modifier = Modifier
                                    .padding(horizontal = 20.dp, vertical = 0.dp)
                            ){
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth(),
                                    contentAlignment = Alignment.CenterStart,
                                ){
                                    TextField(
                                        value = input_name,
                                        onValueChange = { input_name = it},
                                        placeholder = {
                                            Text(
                                                text = stringResource(id = R.string.example_routine_name),
                                                style = TextStyle(
                                                    fontFamily = customFontFamily,
                                                    fontWeight = FontWeight.Light,
                                                    fontSize = 12.sp,
                                                    color = DarkBlue,
                                                    textAlign = TextAlign.Start,
                                                ),
                                            )
                                        },
                                        textStyle = TextStyle(
                                            fontFamily = customFontFamily,
                                            fontWeight = FontWeight.Normal,
                                            fontSize = 12.sp,
                                            color = DarkBlue,
                                            textAlign = TextAlign.Start,
                                        ),
                                        colors = TextFieldDefaults.textFieldColors(
                                            backgroundColor = Color.White,
                                            focusedIndicatorColor = DarkBlue,
                                            cursorColor = DarkBlue,
                                            unfocusedIndicatorColor = Color.Transparent,
                                            disabledIndicatorColor = Color.Transparent,
                                        ),
                                        modifier = Modifier
                                            .fillMaxWidth(),
                                    )
                                }
                            }
                            Text(
                                text = stringResource(id = R.string.exercise_days),
                                style = TextStyle(
                                    fontFamily = customFontFamily,
                                    fontWeight = FontWeight.Medium,
                                    fontSize = 12.sp,
                                    color = DarkBlue,
                                    textAlign = TextAlign.Start,
                                ),
                                modifier = Modifier
                                    .padding(horizontal = 20.dp, vertical = 5.dp),
                            )
                            WeekButtonsRow(
                                multipleItems = true,
                                onSelectionChanged = { selectedDays = it },
                            )
                        }
                    }
                    Spacer(modifier = Modifier
                        .height(1.dp)
                        .background(LightBlue)
                        .fillMaxWidth())
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .weight(1f),
                    ){
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 0.dp, vertical = 0.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .fillMaxSize()
                                    .background(Color.White)
                                    .clickable { setShow(false, false) },
                                contentAlignment = Alignment.Center,
                            ) {
                                Text(
                                    text = stringResource(id = R.string.cancel),
                                    style = TextStyle(
                                        fontFamily = customFontFamily,
                                        fontWeight = FontWeight.Medium,
                                        fontSize = 14.sp,
                                        color = Blue,
                                        textAlign = TextAlign.Center,
                                    ),
                                )
                            }
                            Spacer(modifier = Modifier
                                .width(1.dp)
                                .background(LightBlue)
                                .fillMaxHeight())
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .weight(1f)
                                    .background(Color.White)
                                    .clickable {
                                        setShow(false, true)
                                        val days = selectedDays.joinToString(separator = " | ")
                                            val routine = Routine(
                                                name = input_name,
                                                days = days,
                                                exercises = arrayListOf(),
                                                )
                                        trainingProgramViewModel.addRoutineToActualProgram(routine = routine,
                                            onSuccess = {
                                                Log.d("Routine", "Routine added to program")
                                                        },
                                            onFailure = {exception->
                                                Log.e("Routine", "Routine not added to program: $exception")
                                                   })
                                               },
                                contentAlignment = Alignment.Center,
                            ) {
                                Text(
                                    text = stringResource(id = R.string.save),
                                    style = TextStyle(
                                        fontFamily = customFontFamily,
                                        fontWeight = FontWeight.Medium,
                                        fontSize = 14.sp,
                                        color = Blue,
                                        textAlign = TextAlign.Center,
                                    ),
                                )
                            }
                        }

                    }
                }
            }
        }
    }
}