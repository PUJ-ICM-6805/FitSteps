package com.example.fitsteps.screens.training.routine

import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.fitsteps.R
import com.example.fitsteps.firebaseData.firebaseOwnProgramData.ExerciseRecord
import com.example.fitsteps.firebaseData.firebaseOwnProgramData.ExerciseSet
import com.example.fitsteps.firebaseData.firebaseOwnProgramData.TrainingProgramViewModel
import com.example.fitsteps.screens.training.exercise.VideoPlayer
import com.example.fitsteps.ui.theme.Blue
import com.example.fitsteps.ui.theme.DarkBlue
import com.example.fitsteps.ui.theme.LightBlue
import com.example.fitsteps.ui.theme.White
import com.example.fitsteps.ui.theme.customFontFamily
import kotlinx.coroutines.delay

class NavigationViewModel: ViewModel() {
    var currentRecordIndex by mutableIntStateOf(0)
}

@Composable
fun RoutineExecutionScreen(
    navController: NavHostController,
    trainingProgramViewModel: TrainingProgramViewModel
) {

    val selectedRoutineState = trainingProgramViewModel.selectedRoutine.observeAsState()
    val selectedRoutine = selectedRoutineState.value
    val currentExerciseIndex = remember { mutableIntStateOf(0) }
    val navigationViewModel: NavigationViewModel =  viewModel()

    if (selectedRoutine != null) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 40.dp)
                .background(White),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween,
        ) {
            item {
                ExerciseDetails(selectedRoutine.exercises[currentExerciseIndex.intValue], navigationViewModel)
            }
            item {
                Log.d("SHARED VALUE", navigationViewModel.currentRecordIndex.toString())
                DataRoutine(
                    onClickR = {
                        if(navigationViewModel.currentRecordIndex <= selectedRoutine.exercises[currentExerciseIndex.intValue].records.last().sets.size){
                            navigationViewModel.currentRecordIndex++
                            Log.d("SHARED VALUER", navigationViewModel.currentRecordIndex.toString())
                        }
                        if(navigationViewModel.currentRecordIndex == selectedRoutine.exercises[currentExerciseIndex.intValue].records.last().sets.size+1){
                            if(currentExerciseIndex.intValue < selectedRoutine.exercises.size-1){
                                currentExerciseIndex.intValue++
                                navigationViewModel.currentRecordIndex = 0
                            }
                        }
                    },
                    onClickL = {
                        Log.d("SHARED VALUE", navigationViewModel.currentRecordIndex.toString())
                        if(navigationViewModel.currentRecordIndex > 0){
                            navigationViewModel.currentRecordIndex--
                            Log.d("SHARED VALUEL", navigationViewModel.currentRecordIndex.toString())
                        }
                        if(navigationViewModel.currentRecordIndex == 0){
                            if(currentExerciseIndex.intValue > 0){
                                currentExerciseIndex.intValue--
                                navigationViewModel.currentRecordIndex = selectedRoutine.exercises[currentExerciseIndex.intValue].records.last().sets.size
                            }
                        }
                    },
                    exerciseRecord = if (currentExerciseIndex.intValue==selectedRoutine.exercises.size-1)
                        selectedRoutine.exercises[currentExerciseIndex.intValue-1] else selectedRoutine.exercises[currentExerciseIndex.intValue+1],
                    isLast = currentExerciseIndex.intValue == selectedRoutine.exercises.size-1,
                    navController = navController,
                )
            }
        }
    }
}

@Composable
fun ExerciseDetails(exerciseRecord: ExerciseRecord, navigationViewModel: NavigationViewModel) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            modifier = Modifier,
            text = exerciseRecord.exercise.name,
            style = TextStyle(
                fontFamily = customFontFamily,
                fontWeight = FontWeight.SemiBold,
                fontSize = 25.sp,
                fontStyle = FontStyle.Normal,
                color = DarkBlue,
            )
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
                .height(175.dp)
                .background(Color.White, RoundedCornerShape(16.dp))
        ) {
            //Force the recomposition of the video player when the video changes
            key(exerciseRecord.exercise.video) {
                VideoPlayer(
                    videoUri = Uri.parse(exerciseRecord.exercise.video),
                )
            }
        }
        Text(
            modifier = Modifier
                .align(Alignment.Start)
                .padding(start = 30.dp, top = 20.dp),
            text = "Series",
            color = DarkBlue,
            fontSize = 18.sp,
            style = TextStyle(
                fontFamily = customFontFamily,
                fontWeight = FontWeight.SemiBold,
                fontStyle = FontStyle.Normal,
            ),
        )
        SetsRecord(
            sets= exerciseRecord.records.last().sets,
            navigationViewModel = navigationViewModel
            )
        if(navigationViewModel.currentRecordIndex==0){
            Text(
                text = stringResource(R.string.do_exercise),
                modifier = Modifier.padding(top = 100.dp),
                color = Blue,
                fontSize = 30.sp,
                fontFamily = customFontFamily,
                fontWeight = FontWeight.SemiBold,
            )
        }else {
            //Get the last record registered
            key(navigationViewModel.currentRecordIndex) {
                key(exerciseRecord.records.last().rest) {
                    RestTimer(exerciseRecord.records.last().rest * 1000L)
                }
            }
        }
    }

}

@Composable
fun SetsRecord(
    sets: ArrayList<ExerciseSet>,
    navigationViewModel: NavigationViewModel,
) {
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .height(40.dp)
            .padding(start = 20.dp),
    ) {
        items(sets.size) {set ->
            Button(
                onClick = {
                        //TODO: EDIT DATA
                },
                modifier = Modifier
                    .fillMaxSize()
                    .padding(end = 10.dp),
                colors = androidx.compose.material3.ButtonDefaults.buttonColors(
                    containerColor = if ( set<navigationViewModel.currentRecordIndex) Blue else LightBlue,
                    contentColor = if ( set<navigationViewModel.currentRecordIndex) White else Blue
                ),
                shape = RoundedCornerShape(10.dp),
            ) {
                Text(
                    text = "${sets[set].weight}kg | ${sets[set].reps}",
                    style = TextStyle(
                        fontFamily = customFontFamily,
                        fontWeight = FontWeight.Medium,
                        fontSize = 15.sp,
                        fontStyle = FontStyle.Normal,
                    )
                )
            }
        }
    }
}
@Composable
fun RestTimer(
    totalTime: Long,
    initialValue: Float = 1f,
) {
    var value by remember {
        mutableFloatStateOf(initialValue)
    }
    var currentTime by remember {
        mutableLongStateOf(totalTime)
    }
    val isTimerRunning by remember {
        mutableStateOf(true)
    }
    LaunchedEffect(key1 = currentTime, key2 = isTimerRunning) {
        if(currentTime > 0 && isTimerRunning && totalTime > 0 ) {
            delay(1000L)
            currentTime -= 1000L
            value = currentTime / totalTime.toFloat()
        }
    }
    Column(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        if (currentTime == 0L) {
            Text(
                text = stringResource(R.string.do_exercise),
                modifier = Modifier.padding(top = 100.dp),
                color = Blue,
                fontSize = 30.sp,
                fontFamily = customFontFamily,
                fontWeight = FontWeight.SemiBold,
            )
        } else {
            Text(
                text = (currentTime / 1000L).toString(),
                color = Blue,
                fontSize = 70.sp,
                fontFamily = customFontFamily,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.padding(top = 20.dp),
            )
            Text(
                text = stringResource(R.string.rest),
                fontFamily = customFontFamily,
                fontWeight = FontWeight.SemiBold,
                color = Blue,
                fontSize = 25.sp,
                modifier = Modifier.offset(y = (-35).dp),
            )
        }
    }
}
@Composable
fun DataRoutine(
    onClickR: ()-> Unit, enabledR : Boolean = true,
    onClickL: () -> Unit, enabledL : Boolean = true,
    exerciseRecord: ExerciseRecord,
    isLast : Boolean = false,
    navController: NavHostController
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .background(Color.White, shape = RoundedCornerShape(topStart = 35.dp, topEnd = 35.dp))
            .padding(top = 20.dp)
            .clip(RoundedCornerShape(16.dp))
    ) {
        /*Box(
            modifier = Modifier
                .fillMaxWidth(),
            contentAlignment = Alignment.Center,
        ){
            Image(
                painter = painterResource(id = R.drawable.rectangle),
                contentDescription = "",
                modifier = Modifier
                    .width(35.dp)
                    .height(30.dp),
            )
        }*  //TODO: Hacerlo deslizable
         */
        Text(
            modifier = Modifier
                .align(Alignment.Start)
                .padding(start = 25.dp),
            text = stringResource(id = R.string.nextexercise),
            style = TextStyle(
                fontFamily = customFontFamily,
                fontWeight = FontWeight.Normal,
                fontSize = 22.sp,
                fontStyle = FontStyle.Normal,
                color = Blue,
            )
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp)
                .padding(horizontal = 20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier
                    .padding(8.dp)
                    .weight(0.7f),
                text = if (!isLast) exerciseRecord.exercise.name else "¡Fin de la Rutina!",
                color = DarkBlue,
                fontSize = 24.sp,
                style = TextStyle(
                    fontFamily = customFontFamily,
                    fontWeight = FontWeight.SemiBold,
                    fontStyle = FontStyle.Normal,
                ),
            )
            Text(
                modifier = Modifier
                    .padding(8.dp)
                    .weight(0.3f),
                text = if(!isLast) exerciseRecord.records.last().sets.size.toString()+" series" else "",
                color = Blue,
                fontSize = 16.sp,
                style = TextStyle(
                    fontFamily = customFontFamily,
                    fontWeight = FontWeight.Medium,
                    fontStyle = FontStyle.Normal,
                ),
            )
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp, horizontal = 20.dp),
        ) {
            Box(
                modifier = Modifier
                    .padding(horizontal = 15.dp)
                    .background(color = LightBlue, shape = RoundedCornerShape(15.dp))
                    .clickable(enabled = enabledL, onClick = onClickL)
            ) {
                Text(
                    modifier = Modifier
                        .padding(horizontal = 15.dp),
                    text = "<",
                    color = Blue,
                    fontSize = 30.sp,
                    style = TextStyle(
                        fontFamily = customFontFamily,
                        fontWeight = FontWeight.Medium,
                        fontStyle = FontStyle.Normal,
                    ),
                )
            }
            Box(
                modifier = Modifier
                    .graphicsLayer {
                        shape = RoundedCornerShape(10.dp)
                        clip = true
                    }
                    .height(70.dp)
                    .padding(top = 10.dp, bottom = 10.dp)
                    .background(color = Blue, shape = RoundedCornerShape(15.dp))
            ) {
                Text(
                    modifier = Modifier
                        .padding(top = 10.dp)
                        .padding(horizontal = 15.dp)
                        .clickable {
                            if(isLast){
                                navController.navigate("home")
                            }else {
                                Toast.makeText(//TODO: Cambiar por un cuadro más bonito
                                    navController.context,
                                    "Acaba la rutina para volver al menú principal",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                                   },
                    text = stringResource(R.string.end_routine),
                    color = Color.White,
                    fontSize = 20.sp,
                    style = TextStyle(
                        fontFamily = customFontFamily,
                        fontWeight = FontWeight.Medium,
                        fontStyle = FontStyle.Normal,
                    ),
                )
            }
            Box(
                modifier = Modifier
                    .padding(horizontal = 15.dp)
                    .background(color = LightBlue, shape = RoundedCornerShape(15.dp))
                    .clickable(enabled = enabledR, onClick = onClickR)
            ) {
                Text(
                    modifier = Modifier
                        .padding(horizontal = 15.dp),
                    text = ">",
                    color = Blue,
                    fontSize = 30.sp,
                    style = TextStyle(
                        fontFamily = customFontFamily,
                        fontWeight = FontWeight.Medium,
                        fontStyle = FontStyle.Normal,
                    ),
                )
            }
        }
    }
}
