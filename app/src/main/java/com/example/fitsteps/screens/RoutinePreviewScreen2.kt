package com.example.fitsteps.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
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
fun RoutinePreviewScreen2() {
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
                androidx.compose.material3.Text(
                    text = stringResource(id = R.string.abs),
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
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ){
                IconAndText(iconId = R.drawable.ic_time, contentDescription = "time icon", text = "50 min")
                IconAndText(iconId = R.drawable.ic_barbell_icon , contentDescription ="barbell icon" , text ="20kg" )

            }
        }
        item {
            LargeButtons(text = stringResource(id = R.string.startRoutine),
                onClick = { /*TODO*/ },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start=25.dp, end=25.dp, top=20.dp, bottom=20.dp)
            )
        }
        item{
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
        item{
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 40.dp),
                horizontalArrangement= Arrangement.SpaceBetween,
            ){
                androidx.compose.material3.Text(
                    text = stringResource(id = R.string.Exercises),
                    style = TextStyle(
                        fontSize = 20.sp,
                        fontWeight = FontWeight.SemiBold,
                        fontFamily = customFontFamily,
                        fontStyle = FontStyle.Normal,
                        color = DarkBlue,
                    )
                )
                androidx.compose.material3.Text(
                    text = stringResource(id = R.string.add),
                    style = TextStyle(
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Normal,
                        fontFamily = customFontFamily,
                        fontStyle = FontStyle.Normal,
                        color = Red,
                    )
                )
            }
        }
        item{
            ExercisesList()
        }
    }
}
@Composable
fun ExercisesList(){
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ){
        Exercise(R.string.plank,false, true)
    }

}
@Composable
fun Exercise(name: Int, isRepsxSeries: Boolean,isTime:Boolean){
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 25.dp)
            .background(Color.White, shape = RoundedCornerShape(10.dp))

    ) {
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.TopEnd
        ) {
            androidx.compose.material3.Text(
                modifier = Modifier.padding(top = 5.dp, end = 10.dp, bottom = 0.dp, start = 0.dp),
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
                    .weight(0.6f)
                    .align(Alignment.CenterVertically)
            ) {
                androidx.compose.material3.Text(
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
                androidx.compose.material3.Text(
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
        Spacer(Modifier.height(12.dp))
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
        androidx.compose.material3.Text(
            text = text,
            style = TextStyle(
                fontFamily = customFontFamily,
                fontWeight = FontWeight.Normal,
                fontSize = 20.sp,
                fontStyle = FontStyle.Normal,
                color = DarkBlue,
            )
        )
    }
}
@Composable
@Preview
fun RoutinePreviewScreen2Preview() {
    RoutinePreviewScreen2()
}
