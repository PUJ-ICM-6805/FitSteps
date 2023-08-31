package com.example.fitsteps.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.fitsteps.R
import com.example.fitsteps.ui.theme.Blue
import com.example.fitsteps.ui.theme.DarkBlue
import com.example.fitsteps.ui.theme.LightBlue
import com.example.fitsteps.ui.theme.Red
import com.example.fitsteps.ui.theme.White
import com.example.fitsteps.ui.theme.customFontFamily

@Composable
fun TrainingsPerDayScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize().background(color = White)
    ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(),
                contentAlignment = Alignment.CenterEnd,
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_settings),
                    contentDescription = "",
                    tint = DarkBlue,
                    modifier = Modifier
                        .padding(15.dp)
                        //.clickable { true }
                )
            }


            Text(
                text = stringResource(id = R.string.training),
                modifier = Modifier.padding(horizontal = 15.dp, vertical = 10.dp),
                style = TextStyle(
                    fontFamily = customFontFamily,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 30.sp,
                    fontStyle = FontStyle.Normal,
                    color = DarkBlue,
                )
            )


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


            WeekButtonsRow()

            Box(
                modifier = Modifier.fillMaxSize()
                .padding(horizontal = 40.dp, vertical = 5.dp)
                .background(color = Color.White, shape = RoundedCornerShape(20.dp))
                ,
            ){
                 TrainingCard()
            }

    }
}
@Composable
fun TrainingCard(){

        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp, vertical = 2.dp),
                contentAlignment = Alignment.TopEnd,
            ) {
                Text(
                    text = stringResource(id = R.string.add),
                    style = TextStyle(
                        fontFamily = customFontFamily,
                        fontWeight = FontWeight.Normal,
                        fontSize = 14.sp,
                        fontStyle = FontStyle.Normal,
                        color = Red,
                    )
                )
            }
            Box(
                Modifier.fillMaxWidth()
            ){
                RoutineDescription()
            }
            Divider(
                modifier = Modifier
                    .fillMaxWidth(),
                color = Red
            )
            ExercisesList()
        }

}
@Composable
fun ExercisesList(){
    LazyColumn(
    ){
        item {
            Exercise(R.string.squat)
        }
        item{
            Exercise(R.string.plank)
        }
        item{
            Exercise(R.string.legElevation)
        }
        item{
            Exercise(R.string.bench_press)
        }
    }
}
@Composable
fun RoutineDescription(){
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ){
        Text(
            text = stringResource(id = R.string.leg),
            modifier = Modifier.padding(horizontal = 15.dp, vertical = 0.dp),
            style = TextStyle(
                fontFamily = customFontFamily,
                fontWeight = FontWeight.SemiBold,
                fontSize = 25.sp,
                fontStyle = FontStyle.Normal,
                color = DarkBlue,
            )
        )
        Text(
            text ="Día 1 | Principiante",
            modifier = Modifier.padding(horizontal = 15.dp, vertical = 0.dp),
            style = TextStyle(
                fontFamily = customFontFamily,
                fontWeight = FontWeight.Normal,
                fontSize = 25.sp,
                fontStyle = FontStyle.Normal,
                color = DarkBlue,
            )
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ){
            IconAndText(iconId = R.drawable.ic_time, contentDescription = "time icon", text = "50 min")
            IconAndText(iconId = R.drawable.ic_barbell_icon , contentDescription ="barbell icon" , text ="20kg" )

        }
    }
}
@Composable
fun IconAndText(iconId: Int, contentDescription : String, text: String){
    Row(
        horizontalArrangement = Arrangement.spacedBy(10.dp),
    ){
        Image(
            painter = painterResource(id =iconId),
            contentDescription = contentDescription)
        Text(
            text = text,
            modifier = Modifier.padding(horizontal = 0.dp, vertical = 5.dp),
            style = TextStyle(
                fontFamily = customFontFamily,
                fontWeight = FontWeight.Normal,
                fontSize = 14.sp,
                fontStyle = FontStyle.Normal,
                color = DarkBlue,
            )
        )
    }
}
@Composable
fun Exercise(name: Int){

        Column() {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.TopEnd
            ) {
                Text(
                    modifier = Modifier.padding(top = 5.dp, end = 10.dp, bottom = 0.dp, start = 0.dp),
                    text = stringResource(id = R.string.edit),
                    style = TextStyle(
                        fontFamily = customFontFamily,
                        fontWeight = FontWeight.Light,
                        fontSize = 15.sp,
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
                        .padding(11.dp)
                        .background(White, shape = RoundedCornerShape(10.dp))
                        .weight(0.3f)
                ) {
                    Image(
                        contentDescription = "Run Button",
                        modifier = Modifier
                            .align(Alignment.Center)
                            .fillMaxSize(0.8f),
                        painter = painterResource(id = R.drawable.ic_play_icon),

                        )
                }
                Column(
                    Modifier
                        .weight(0.7f)
                        .align(Alignment.CenterVertically)
                ) {
                    Text(
                        modifier = Modifier.padding(horizontal = 15.dp, vertical = 0.dp),
                        text = stringResource(id =name),
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
                        text = "3 " + stringResource(id = R.string.series) + ", " +
                                "4 " + stringResource(id = R.string.repetitions),
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
        }
    Divider(
        modifier = Modifier
            .fillMaxWidth(),
        color = LightBlue
        )
    }

@Composable
@Preview
fun TrainingsPerDayScreenPreview() {
    TrainingsPerDayScreen()
    //TrainingCard()
}
