package com.example.fitsteps.screens.exercise

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.fitsteps.R
import com.example.fitsteps.navigation.Screen
import com.example.fitsteps.screens.training.trainingMainScreen.LargeButtons
import com.example.fitsteps.ui.theme.DarkBlue
import com.example.fitsteps.ui.theme.LightBlue
import com.example.fitsteps.ui.theme.Red
import com.example.fitsteps.ui.theme.White
import com.example.fitsteps.ui.theme.customFontFamily

@Composable
fun RoutinePreviewScreen2(
    navController: NavHostController
) {
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
                onClick = {
                     navController.navigate(Screen.DemoPrepareScreen1.route)
                },
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