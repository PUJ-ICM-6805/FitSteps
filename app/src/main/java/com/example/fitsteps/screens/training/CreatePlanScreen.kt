package com.example.fitsteps.screens.training

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.fitsteps.R
import com.example.fitsteps.firebaseData.firebaseExerciseData.Exercise
import com.example.fitsteps.firebaseData.firebaseOwnProgramData.TrainingProgramViewModel
import com.example.fitsteps.navigation.Screen
import com.example.fitsteps.screens.training.trainingMainScreen.LargeButtons
import com.example.fitsteps.ui.theme.Blue
import com.example.fitsteps.ui.theme.DarkBlue
import com.example.fitsteps.ui.theme.LightBlue
import com.example.fitsteps.ui.theme.Red
import com.example.fitsteps.ui.theme.customFontFamily

@Composable
fun CreateRoutineScreen(
    navController: NavHostController,
    trainingProgramViewModel: TrainingProgramViewModel
) {
    var showCreateRoutineFrame by remember { mutableStateOf(false) }
    if (showCreateRoutineFrame) {
        CreateNewRoutineFrame(
            show = showCreateRoutineFrame,
            setShow = { showFrame, showExercise ->
                showCreateRoutineFrame = showFrame
            },
            trainingProgramViewModel = trainingProgramViewModel,
        )
    }
    LazyColumn(
        modifier = Modifier
            .fillMaxSize(),
    ){
        item {
            Spacer(modifier = Modifier.height(40.dp))
        }
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(20.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.my_plan_ex), // TODO Change for the actual name
                    style = TextStyle(
                        fontSize = 30.sp,
                        fontWeight = FontWeight.SemiBold,
                        fontFamily = customFontFamily,
                        fontStyle = FontStyle.Normal,
                        color = DarkBlue,
                    )
                )
                Text(
                    text = stringResource(id = R.string.description),
                    style = TextStyle(
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Normal,
                        fontFamily = customFontFamily,
                        fontStyle = FontStyle.Normal,
                        color = Blue,
                    )
                )
                Spacer(modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(LightBlue)
                    .padding(vertical = 20.dp)
                )
            }
        }
        item {
            if (trainingProgramViewModel.getSelectedProgramRoutines().isEmpty()) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_calendar_2),
                        contentDescription = "Icon",
                        modifier = Modifier
                            .size(150.dp)
                            .padding(20.dp),
                        tint = Blue,
                    )
                    Text(
                        text = stringResource(id = R.string.description_routine_creation),
                        style = TextStyle(
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Normal,
                            fontFamily = customFontFamily,
                            fontStyle = FontStyle.Normal,
                            color = Blue,
                        ),
                        textAlign = TextAlign.Center,
                    )
                    LargeButtons(
                        text = stringResource(id = R.string.create_routine),
                        onClick = { showCreateRoutineFrame = true },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 20.dp),
                    )
                }
            }else {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                ){
                    Text(
                        text = stringResource(id = R.string.routines),
                        style = TextStyle(
                            fontSize = 20.sp,
                            fontWeight = FontWeight.SemiBold,
                            fontFamily = customFontFamily,
                            fontStyle = FontStyle.Normal,
                            color = DarkBlue,
                        ),
                    )
                    Text(
                        text = stringResource(id = R.string.add),
                        style = TextStyle(
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Normal,
                            fontFamily = customFontFamily,
                            fontStyle = FontStyle.Normal,
                            color = Red,
                        ),
                        modifier = Modifier
                            .clickable { showCreateRoutineFrame = true }
                            .padding(end = 20.dp),
                    )
                }
            }
        }
        items(trainingProgramViewModel.getSelectedProgramRoutines().size){item->
            val auxTrainingProgram = trainingProgramViewModel.getSelectedProgramRoutines()[item]
            RoutineCardExercises(days =auxTrainingProgram.days ,
                name =auxTrainingProgram.name ,
                exercises = auxTrainingProgram.exercises,
                time = auxTrainingProgram.time,
                kcal = auxTrainingProgram.kcal,
                navController =navController)
        }
        item {
            Spacer(modifier = Modifier.height(80.dp))
        }
    }
}

@Composable
fun RoutineCardExercises(
    days: String,
    name: String,
    exercises : List<Exercise>,
    time : String,
    kcal : String,
    navController: NavHostController,
){
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(20.dp)
            .clickable { navController.navigate(Screen.RoutineScreen.route) },
        colors = CardDefaults.cardColors(
            containerColor = Color.White,
        ),
    ){
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
        ) {
            Text(
                text = days,
                style = TextStyle(
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium,
                    fontFamily = customFontFamily,
                    fontStyle = FontStyle.Normal,
                    color = Red,
                )
            )
            Text(
                text = name,
                style = TextStyle(
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    fontFamily = customFontFamily,
                    fontStyle = FontStyle.Normal,
                    color = DarkBlue,
                )
            )
            Text(
                text = "${exercises.size} ejercicios | $time min | $kcal kcal",
                style = TextStyle(
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Light,
                    fontFamily = customFontFamily,
                    fontStyle = FontStyle.Italic,
                    color = DarkBlue,
                )
            )
        }
    }
}
