package com.example.fitsteps.screens.training.exercise

import android.net.Uri
import android.util.Log
import android.widget.MediaController
import android.widget.VideoView
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
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.fitsteps.R
import com.example.fitsteps.ui.theme.Blue
import com.example.fitsteps.ui.theme.LightBlue
import com.example.fitsteps.ui.theme.Red
import com.example.fitsteps.ui.theme.White
import com.example.fitsteps.ui.theme.customFontFamily
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavHostController
import com.example.fitsteps.firebaseData.firebaseExerciseData.Exercise
import com.example.fitsteps.firebaseData.firebaseOwnProgramData.ExerciseRecord
import com.example.fitsteps.firebaseData.firebaseOwnProgramData.ExerciseSet
import com.example.fitsteps.firebaseData.firebaseOwnProgramData.Record
import com.example.fitsteps.firebaseData.firebaseOwnProgramData.TrainingProgramViewModel
import com.example.fitsteps.navigation.Screen
import com.example.fitsteps.ui.theme.DarkBlue


@Composable
fun DetailsPerExerciseScreen(
    navController: NavHostController,
    setShow: (Boolean) -> Unit = {}, exercise: Exercise,
    trainingProgramViewModel: TrainingProgramViewModel
){

    var setNumber by remember {
        mutableIntStateOf(0)
    }
    var restNumber by remember {
        mutableIntStateOf(0)
    }
    val sets : MutableList<ExerciseSet> = mutableListOf()


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
                .wrapContentHeight()
                .padding(horizontal = 20.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color(0xFFF4F4F4),
            ),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 3.dp
            ),
        ) {
            LazyColumn(
                modifier = Modifier
                    .height(620.dp)
                    .fillMaxWidth()
                    .background(White, RoundedCornerShape(10.dp))
                    .padding(horizontal = 20.dp, vertical = 10.dp)
            ) {
                item{
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 10.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = stringResource(id = R.string.backlc),
                            modifier = Modifier
                                .padding(horizontal = 10.dp, vertical = 0.dp)
                                .clickable {
                                    setShow(false)
                                },
                            style = TextStyle(
                                fontFamily = customFontFamily,
                                fontWeight = FontWeight.Light,
                                fontSize = 10.sp,
                                fontStyle = FontStyle.Normal,
                                color = Blue,
                            )
                        )
                        Text(
                            text = stringResource(id = R.string.save),
                            modifier = Modifier
                                .padding(horizontal = 10.dp, vertical = 0.dp)
                                .clickable {
                                    val newExerciseRecord = ExerciseRecord(exercise)
                                    val record = Record(rest = restNumber, sets = ArrayList(sets))
                                    newExerciseRecord.addRecord(record)
                                    trainingProgramViewModel.addExerciseToActualRoutine(
                                        newExerciseRecord,
                                        onSuccess = {
                                            Log.d(
                                                "Exercise added",
                                                "Exercise $exercise added to routine"
                                            )
                                            Log.d(
                                                "Record added"
                                                , "Record $record added to exercise $exercise"
                                            )
                                        },
                                        onFailure = {
                                            Log.e(
                                                "Exercise not added",
                                                "Exercise $exercise not added to routine $it"
                                            )
                                        })
                                    setShow(false)
                                    navController.navigate(Screen.RoutineScreen.route)
                                },
                            style = TextStyle(
                                fontFamily = customFontFamily,
                                fontWeight = FontWeight.Light,
                                fontSize = 10.sp,
                                fontStyle = FontStyle.Normal,
                                color = Red,
                            )
                        )

                    }
                }
                item {
                    Box {
                        Text(
                            text = exercise.name,
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 0.dp),
                            style = TextStyle(
                                fontFamily = customFontFamily,
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 20.sp,
                                fontStyle = FontStyle.Normal,
                                color = DarkBlue,
                            )
                        )
                    }
                }
                item{
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(150.dp)
                            .background(Color.White, RoundedCornerShape(16.dp))
                    ) {
                        VideoPlayer(
                            videoUri= Uri.parse(exercise.video)
                        )
                    }
                }
                item{
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        TextAndFrame(
                            text = "Número de Series",false,""
                        )
                        Box(
                            modifier = Modifier
                                .size(180.dp, 30.dp)
                                .background(Blue, RoundedCornerShape(5.dp)),
                            contentAlignment = Alignment.Center
                        ){
                            NumberButton(setNumber, onNumberChange = {setNumber = it})
                        }
                    }
                }
                item{
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        TextAndFrame(
                            text = "Descanso entre series",true,"Segundos"
                        )
                        Box(
                            modifier = Modifier
                                .size(180.dp, 30.dp)
                                .background(Blue, RoundedCornerShape(5.dp)),
                            contentAlignment = Alignment.Center
                        ){
                            NumberButton(restNumber, onNumberChange = {restNumber = it},true)
                        }
                    }
                }
                item{
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp),
                    ) {
                        Text(
                            text = stringResource(id = R.string.series),
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 0.dp),
                            style = TextStyle(
                                fontFamily = customFontFamily,
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 10.sp,
                                fontStyle = FontStyle.Normal,
                                color = DarkBlue,
                            )
                        )
                        Text(
                            text = stringResource(id = R.string.carga),
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 0.dp),
                            style = TextStyle(
                                fontFamily = customFontFamily,
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 10.sp,
                                fontStyle = FontStyle.Normal,
                                color = DarkBlue,
                            )
                        )
                        Text(
                            text = stringResource(id = R.string.repetitions),
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 0.dp),
                            style = TextStyle(
                                fontFamily = customFontFamily,
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 10.sp,
                                fontStyle = FontStyle.Normal,
                                color = DarkBlue,
                            )
                        )
                    }
                }
                items((1..setNumber).toList()){ currentSetNumber->
                    if(currentSetNumber>0) {
                        DetailsPerSet(
                            setNumber = currentSetNumber,
                            set = ExerciseSet(),
                            onSetChange = {
                                if(currentSetNumber>sets.size) {
                                    Log.d("Set added", "Set $it added")
                                    sets.add(it)
                                }
                                else {
                                    Log.d("Set changed", "Set $it changed")
                                    sets[currentSetNumber - 1] = it}
                           }
                        )
                    }
                }
                item{
                    Spacer(modifier = Modifier.height(10.dp))
                }
                item{
                    WeightSelectionButtons()
                }
            }
        }
    }
}
@Composable
fun TextAndFrame(text: String, hasSubText: Boolean , subText: String){
        Box(
            modifier = Modifier
                .size(150.dp, 34.dp),
            contentAlignment = Alignment.BottomStart
        ){
            Column {
                Text(
                    text = text,
                    modifier = Modifier
                        .padding(horizontal = 5.dp, vertical = 0.dp)
                        .offset(y = (-7).dp),
                    style = TextStyle(
                        fontFamily = customFontFamily,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 11.sp,
                        fontStyle = FontStyle.Normal,
                        color = DarkBlue,
                    )
                )
                if(hasSubText) {
                    Text(
                        text = subText,
                        modifier = Modifier
                            .padding(horizontal = 5.dp, vertical = 0.dp)
                            .offset(y = (-12).dp),
                        style = TextStyle(
                            fontFamily = customFontFamily,
                            fontWeight = FontWeight.Light,
                            fontSize = 10.sp,
                            fontStyle = FontStyle.Italic,
                            color = DarkBlue,
                        )
                    )
                }
            }
        }
}

@Composable
fun NumberButton(
  number: Int = 0,
  onNumberChange : (Int) -> Unit = {},
  isSecs: Boolean = false,
){
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically,
        ){
            Button(
             modifier =Modifier.weight(0.3f),
             colors = ButtonDefaults.buttonColors(
                 backgroundColor = Blue,
                 contentColor = LightBlue),
                 onClick = {
                     if(number>0){
                        if(isSecs){
                            onNumberChange(number-10)
                        }else
                            onNumberChange(number-1)
                     }
                }
            ){
                Text(
                    text = "-",
                    modifier = Modifier.offset(y= (-5).dp),
                    style = TextStyle(
                        fontFamily = customFontFamily,
                        fontWeight = FontWeight.Light,
                        fontSize = 15.sp,
                        fontStyle = FontStyle.Normal,
                        color = LightBlue,
                        ),
                    )
            }
            Text(
                modifier =Modifier.weight(0.3f),
                textAlign = TextAlign.Center,
                text = "$number",
                style = TextStyle(
                    fontFamily = customFontFamily,
                    fontWeight = FontWeight.Light,
                    fontSize = 15.sp,
                    fontStyle = FontStyle.Normal,
                    color = LightBlue,
                ),
                )
            Button(
                modifier =Modifier.weight(0.3f),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Blue,
                    contentColor = LightBlue),
                onClick = {
                        if(isSecs){
                            onNumberChange(number+10)
                        }else
                            onNumberChange(number+1)
                          },
                )
            {
                Text(
                    text = "+",
                    modifier = Modifier.offset(y= (-5).dp),
                    style = TextStyle(
                        fontFamily = customFontFamily,
                        fontWeight = FontWeight.Light,
                        fontSize = 15.sp,
                        fontStyle = FontStyle.Normal,
                        color = LightBlue,
                    ),
                    )
            }
        }

}

@Composable
fun DetailsPerSet(
    setNumber: Int,
    set: ExerciseSet,
    onSetChange: (ExerciseSet) -> Unit,
) {
    var weightText by remember { mutableStateOf(set.weight.toString()) }
    var repsText by remember { mutableStateOf(set.reps.toString()) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(vertical = 5.dp)
            .background(Color.White, RoundedCornerShape(10.dp))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 5.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            Text(
                text = "$setNumber",
                modifier = Modifier.padding(start = 10.dp).weight(1f),
                style = TextStyle(
                    fontFamily = customFontFamily,
                    fontWeight = FontWeight.Medium,
                    fontSize = 16.sp,
                    fontStyle = FontStyle.Normal,
                    color = DarkBlue,
                )
            )
            BasicTextField(
                value = if(weightText=="0.0") "" else weightText,
                onValueChange = {
                    weightText = it
                    if (it.isFloat()) {
                        set.weight = it.toFloat()
                        onSetChange(set)
                        Log.d("Weight added", weightText)
                    }
                },
                textStyle = TextStyle(
                    fontFamily = customFontFamily,
                    fontWeight = FontWeight.Medium,
                    fontSize = 16.sp,
                    fontStyle = FontStyle.Normal,
                    color = DarkBlue,
                ),
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Decimal),
                singleLine = true,
                modifier = Modifier.padding(start = 10.dp).weight(1f),
            )
            BasicTextField(
                value = if(repsText=="0") "" else repsText,
                onValueChange = {
                    repsText = it
                    if (it.isInt()) {
                        set.reps = it.toInt()
                        onSetChange(set)
                        Log.d("Reps added", repsText)
                    }
                },
                textStyle = TextStyle(
                    fontFamily = customFontFamily,
                    fontWeight = FontWeight.Medium,
                    fontSize = 16.sp,
                    fontStyle = FontStyle.Normal,
                    color = DarkBlue,
                ),
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                singleLine = true,
                modifier = Modifier.padding(start = 10.dp).weight(1f),
            )
        }
    }
}

private fun String.isFloat(): Boolean {
    return try {
        this.toFloat()
        true
    } catch (e: NumberFormatException) {
        false
    }
}
private fun String.isInt(): Boolean {
    return try {
        this.toInt()
        true
    } catch (e: NumberFormatException) {
        false
    }
}

@Composable
fun WeightSelectionButtons(){
    var selectedWeight by remember {
        mutableStateOf(false)
    }
    Row(
        modifier = Modifier
            .clip(shape = RoundedCornerShape(8.dp))
            .fillMaxWidth()
            .wrapContentHeight(),
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f)
                .background(
                    if (selectedWeight) Blue else LightBlue
                )
                .clip(shape = RoundedCornerShape(8.dp))
                .clickable { selectedWeight = true },
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = stringResource(id = R.string.kg),
                style = TextStyle(
                    fontFamily = customFontFamily,
                    fontWeight = FontWeight.Medium,
                    fontSize = 15.sp,
                    fontStyle = FontStyle.Normal,
                    color = if (selectedWeight) LightBlue else Blue,
                )
            )
        }
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    if (!selectedWeight) Blue else LightBlue
                )
                .weight(1f)
                .clip(shape = RoundedCornerShape(8.dp))
                .clickable { selectedWeight = false },
            contentAlignment = Alignment.Center

        ) {
            Text(
                text = stringResource(id = R.string.lbs),
                style = TextStyle(
                    fontFamily = customFontFamily,
                    fontWeight = FontWeight.Medium,
                    fontSize = 15.sp,
                    fontStyle = FontStyle.Normal,
                    color = if (!selectedWeight) LightBlue else Blue,
                )
            )

        }
    }
}
@Composable
fun VideoPlayer(
    videoUri: Uri
) {
    AndroidView(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clip(RoundedCornerShape(16.dp)),
        factory = { context ->
            VideoView(context).apply {
                setVideoURI(videoUri)

                val mediaController = MediaController(context)
                mediaController.setAnchorView(this)

                setMediaController(mediaController)

                setOnPreparedListener {
                    start()
                }
            }
        })
}