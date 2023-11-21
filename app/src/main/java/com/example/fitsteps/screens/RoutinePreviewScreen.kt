package com.example.fitsteps.screens

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
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.fitsteps.R
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.fitsteps.navigation.Screen
import com.example.fitsteps.ui.theme.Blue
import com.example.fitsteps.ui.theme.DarkBlue
import com.example.fitsteps.ui.theme.LightBlue
import com.example.fitsteps.ui.theme.Red
import com.example.fitsteps.ui.theme.White
import com.example.fitsteps.ui.theme.customFontFamily

@Composable
fun RoutinePreviewScreen(navController: NavHostController) {
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
            Box(
                Modifier.fillMaxWidth()
            ) {
                RoutineDetails("0", "0", "0")
            }
        }
        item {
            LargeButtons(text = stringResource(id = R.string.startRoutine),
                onClick = { /*TODO*/ },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(25.dp),
                color = LightBlue,
                textColor = Blue
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
                Text(
                    text = stringResource(id = R.string.Exercises),
                    style = TextStyle(
                        fontSize = 20.sp,
                        fontWeight = FontWeight.SemiBold,
                        fontFamily = customFontFamily,
                        fontStyle = FontStyle.Normal,
                        color = DarkBlue,
                    )
                )
                Text(
                    text = stringResource(id = R.string.add),
                    style = TextStyle(
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Normal,
                        fontFamily = customFontFamily,
                        fontStyle = FontStyle.Normal,
                        color = Red,
                    ),
                    modifier = Modifier
                        .clickable { navController.navigate(Screen.AddExerciseScreen.route) }
                )
            }
        }
        item{
            NoExercisesYet()
        }
    }

}
@Composable
fun NoExercisesYet(){
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
            onClick = { /*TODO*/ },
            modifier = Modifier
                .fillMaxWidth()
                .padding(25.dp),

        )

    }
}
@Composable
fun RoutineDetails(minutes: String, numExercises: String, kcal: String) {
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
        Text(
            text = kcal+" "+stringResource(id = R.string.kcal),
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
@Preview
fun RoutinePreviewScreenPreview() {
    RoutinePreviewScreen(navController = rememberNavController())
}