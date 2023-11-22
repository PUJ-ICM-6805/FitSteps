package com.example.fitsteps.screens.training.exercise

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.fitsteps.R
import androidx.compose.material3.Text
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.fitsteps.firebaseData.firebaseOwnProgramData.ExerciseRecord
import com.example.fitsteps.firebaseData.firebaseOwnProgramData.TrainingProgramViewModel
import com.example.fitsteps.navigation.Screen
import com.example.fitsteps.screens.training.trainingMainScreen.LargeButtons
import com.example.fitsteps.ui.theme.Blue
import com.example.fitsteps.ui.theme.DarkBlue
import com.example.fitsteps.ui.theme.LightBlue
import com.example.fitsteps.ui.theme.Red
import com.example.fitsteps.ui.theme.White
import com.example.fitsteps.ui.theme.customFontFamily

@Composable
fun RoutinePreviewScreen(
    navController: NavHostController,
    trainingProgramViewModel: TrainingProgramViewModel
){
    val selectedRoutineState = trainingProgramViewModel.selectedRoutine.observeAsState()
    val selectedRoutine = selectedRoutineState.value
    if(selectedRoutine != null) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(White),
        ) {
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
                        text = selectedRoutine.name,
                        style = TextStyle(
                            fontSize = 30.sp,
                            fontWeight = FontWeight.SemiBold,
                            fontFamily = customFontFamily,
                            fontStyle = FontStyle.Normal,
                            color = DarkBlue,
                        )
                    )
                }
            }
            item {
                Box(
                    Modifier.fillMaxWidth()
                ) {
                    RoutineDetails(selectedRoutine.time.toString(),
                        selectedRoutine.exercises.size.toString(),
                        )
                }
            }
            item {
                if(selectedRoutine.exercises.isEmpty()){
                    LargeButtons(
                        text = stringResource(id = R.string.startRoutine),
                        onClick = {},
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(25.dp),
                        color = LightBlue,
                        textColor = Blue
                    )
                }else{
                    LargeButtons(
                        text = stringResource(id = R.string.startRoutine),
                        onClick = {
                            navController.navigate(Screen.RoutineExecutionScreen.route) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(25.dp),
                        color = Red,
                        textColor = Color.White
                    )
                }

            }
            item {
                Divider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp)
                        .height(1.dp)
                        .background(LightBlue)
                )
            }
            item {
                Spacer(modifier = Modifier.height(20.dp))
            }
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
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
                            .clickable { navController.navigate(Screen.AddExerciseScreen.route) }
                            .padding(end = 10.dp),
                    )
                }
            }
            if(selectedRoutine.exercises.isEmpty()){
                item {
                    NoExercisesYet(navController)
                }
            }else{
                items(selectedRoutine.exercises.size) {exercises->
                    Exercise(selectedRoutine.exercises[exercises])
                }
            }
            item{
                Spacer(modifier = Modifier.height(80.dp))
            }
        }
    }
}
@Composable
fun NoExercisesYet(navController: NavHostController){
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Spacer(Modifier.height(30.dp))
        Icon(
            painter = painterResource(id = R.drawable.ic_dumbell_icon),
            contentDescription = "dumbell",
            modifier = Modifier
                .size(100.dp)
                .align(Alignment.CenterHorizontally),
            tint = Blue
        )
        Spacer(Modifier.height(20.dp))
        Text(
            modifier = Modifier
                .padding(horizontal = 30.dp)
                .align(Alignment.CenterHorizontally),
            text = stringResource(id = R.string.noExercisesYet),
            style = TextStyle(
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold,
                fontFamily = customFontFamily,
                fontStyle = FontStyle.Normal,
                color = Blue ,
            )
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 30.dp)
            ,contentAlignment = Alignment.Center
        ){
            Spacer(Modifier.height(10.dp))
                Text(
                    modifier =Modifier
                        .wrapContentSize(Alignment.Center),
                    textAlign = TextAlign.Center,
                    text = stringResource(id = R.string.description_noExercisesYet),
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Normal,
                        fontFamily = customFontFamily,
                        fontStyle = FontStyle.Normal,
                        color = Blue,
                    )
                )
        }
        LargeButtons(
            text = stringResource(id =R.string.addExercise),
            onClick = { navController.navigate(Screen.AddExerciseScreen.route)},
            modifier = Modifier
                .fillMaxWidth()
                .padding(25.dp),

        )

    }
}
@Composable
fun RoutineDetails(minutes: String, numExercises: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement =Arrangement.SpaceEvenly,
    ) {
        Text(
            text =minutes+" "+stringResource(id = R.string.min),
            style = TextStyle(
                fontSize = 22.sp,
                fontWeight = FontWeight.Normal,
                fontFamily = customFontFamily,
                fontStyle = FontStyle.Normal,
                color = Blue,
            )
        )
        Text(
            text = numExercises+" "+stringResource(id = R.string.exercises),
            style = TextStyle(
                fontSize = 22.sp,
                fontWeight = FontWeight.Normal,
                fontFamily = customFontFamily,
                fontStyle = FontStyle.Normal,
                color = Blue,
            )
        )
    }
}
@Composable
fun Exercise(exerciseR: ExerciseRecord,
             isRepsxSeries: Boolean = false,//TODO: Cambiar a true cuando se implemente
             isTime:Boolean=false) {//TODO: Cambiar a true cuando se implemente
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 25.dp)
            .background(Color.White, shape = RoundedCornerShape(10.dp))

    ) {
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.TopEnd,

        ) {
            Text(
                modifier = Modifier
                    .padding(top = 5.dp, end = 10.dp, bottom = 0.dp, start = 0.dp)
                    .clickable {
                        //TODO: Editar ejercicio
                    },
                text = stringResource(id = R.string.edit),
                style = TextStyle(
                    fontFamily = customFontFamily,
                    fontWeight = FontWeight.Light,
                    fontSize = 13.sp,
                    fontStyle = FontStyle.Normal,
                    color = Blue,
                )
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp),
        ) {
            Box(
                Modifier
                    .fillMaxSize()
                    .padding(start = 25.dp, end = 0.dp, top = 0.dp, bottom = 12.dp)
                    //.background(shape = RoundedCornerShape(10.dp))
                    .weight(0.3f)
            ) {
                Image(
                    modifier =Modifier.fillMaxSize(),
                    painter = rememberAsyncImagePainter(
                        model = exerciseR.exercise.image,
                    ),
                    contentDescription = "Exercise Image",
                    contentScale = ContentScale.FillBounds
                )
                Image(
                    contentDescription = "Run Button",
                    modifier = Modifier
                        .align(Alignment.Center)
                        .fillMaxSize(0.3f),
                    painter = painterResource(id = R.drawable.ic_play_whitebg),
                )
            }
            Column(
                Modifier
                    .weight(0.6f)
                    .align(Alignment.CenterVertically)
            ) {
                Text(
                    modifier = Modifier.padding(horizontal = 15.dp, vertical = 0.dp),
                    text = exerciseR.exercise.name,
                    style = TextStyle(
                        fontFamily = customFontFamily,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 20.sp,
                        fontStyle = FontStyle.Normal,
                        color = DarkBlue,
                    )
                )
                Text(
                    modifier = Modifier.padding(horizontal = 15.dp, vertical = 0.dp),
                    text = exerciseR.records.last().sets.size.toString() +" "+ stringResource(id = R.string.series),
                    style = TextStyle(
                        fontFamily = customFontFamily,
                        fontWeight = FontWeight.ExtraLight,
                        fontSize = 15.sp,
                        fontStyle = FontStyle.Normal,
                        color = DarkBlue,
                    )
                )
            }
        }
        Spacer(Modifier.height(12.dp))
    }
}