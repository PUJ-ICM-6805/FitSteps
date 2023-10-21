package com.example.fitsteps.screens.exercise

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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.fitsteps.R
import com.example.fitsteps.ui.theme.Blue
import com.example.fitsteps.ui.theme.LightBlue
import com.example.fitsteps.ui.theme.Red
import com.example.fitsteps.ui.theme.White
import com.example.fitsteps.ui.theme.customFontFamily
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.fitsteps.navigation.Screen
import com.example.fitsteps.ui.theme.DarkBlue


@Composable
fun DetailsPerExerciseScreen(
    navController: NavHostController,
    setShow: (Boolean) -> Unit = {}, exerciseList: List<Exercise>
){
    Log.d("EX SIZE", exerciseList.size.toString())
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
            Column(
                modifier = Modifier
                    .height(620.dp)
                    .fillMaxWidth()
                    .background(White, RoundedCornerShape(10.dp))
                    .padding(horizontal = 20.dp, vertical = 10.dp)
            ) {
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
                                setShow(false)
                                navController.navigate(Screen.RoutineScreen2.route)
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
                Box {
                    androidx.compose.material3.Text(
                        text = exerciseList[0].name,
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
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp)
                        .background(Color.White, RoundedCornerShape(16.dp))
                ) {
                    VideoPlayer(
                        videoUri= Uri.parse(exerciseList[0].video)
                    )
                   /* Image(
                        contentDescription = "Run Button",
                        modifier = Modifier
                            .align(Alignment.Center)
                            .fillMaxSize(0.25f),
                        painter = painterResource(id = R.drawable.ic_play_whitebg),
                    )*/
                }
                TextAndFrame(
                    text = "Repeticiones"
                )
                TextAndFrame(
                    text = "Número de Series"
                )
                TextAndFrame(
                    text = "Descanso entre series"
                )
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
                DetailsPerSet(
                    setNumber = 1,
                    repetitions = 10,
                    weight = 10f,
                )
                DetailsPerSet(
                    setNumber = 2,
                    repetitions = 10,
                    weight = 10f,
                )
                DetailsPerSet(
                    setNumber = 3,
                    repetitions = 10,
                    weight = 10f,
                )
                Spacer(modifier = Modifier.height(10.dp))
                WeightSelectionButtons()
            }
        }
    }
}
@Composable
fun TextAndFrame(text: String){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Box(
            modifier = Modifier
                .size(150.dp, 30.dp),
            contentAlignment = Alignment.BottomStart
        ){
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
        }

        Box(
            modifier = Modifier
                .size(180.dp, 30.dp)
                .background(Blue, RoundedCornerShape(5.dp)),
            contentAlignment = Alignment.Center
        ){
            numberButton()
        }
    }

}

@Composable
fun numberButton(){
    var number by remember {
        mutableStateOf(0)
    }

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
                onClick = { if(number>0){ number-- } })
            {
                Text(
                    text = "-",
                    style = TextStyle(
                        fontFamily = customFontFamily,
                        fontWeight = FontWeight.Light,
                        fontSize = 10.sp,
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
                    fontSize = 10.sp,
                    fontStyle = FontStyle.Normal,
                    color = LightBlue,
                ),

                )

            Button(
                modifier =Modifier.weight(0.3f),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Blue,
                    contentColor = LightBlue),
                onClick = { number++ },)
            {
                Text(
                    text = "+",
                    style = TextStyle(
                        fontFamily = customFontFamily,
                        fontWeight = FontWeight.Light,
                        fontSize = 10.sp,
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
    repetitions: Int,
    weight: Float,
) {
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
                modifier = Modifier.padding(start = 10.dp),
                style = TextStyle(
                    fontFamily = customFontFamily,
                    fontWeight = FontWeight.Medium,
                    fontSize = 16.sp,
                    fontStyle = FontStyle.Normal,
                    color = DarkBlue,
                )
            )
            Text(
                text = "$weight",
                style = TextStyle(
                    fontFamily = customFontFamily,
                    fontWeight = FontWeight.Medium,
                    fontSize = 16.sp,
                    fontStyle = FontStyle.Normal,
                    color = DarkBlue,
                )
            )
            Text(
                text = "$repetitions",
                modifier = Modifier.padding(end = 10.dp),
                style = TextStyle(
                    fontFamily = customFontFamily,
                    fontWeight = FontWeight.Medium,
                    fontSize = 16.sp,
                    fontStyle = FontStyle.Normal,
                    color = DarkBlue,
                )
            )
        }
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
@Composable
@Preview
fun DetailsPeraExercisePreview(){
    DetailsPerExerciseScreen(navController = rememberNavController(), setShow = {}, exerciseList = emptyList())
}
