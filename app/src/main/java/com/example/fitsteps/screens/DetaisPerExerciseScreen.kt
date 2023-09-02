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
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import com.example.fitsteps.ui.theme.LightBlue
import com.example.fitsteps.ui.theme.Red
import com.example.fitsteps.ui.theme.White
import com.example.fitsteps.ui.theme.customFontFamily
import androidx.compose.ui.graphics.Color
import com.example.fitsteps.ui.theme.DarkBlue


@Composable
fun DetailsPerExerciseScreen(){
    Column(
        modifier = Modifier
            .fillMaxSize(0.9f)
            .background(White, RoundedCornerShape(10.dp))
            .padding(horizontal = 20.dp, vertical = 10.dp)
    ){
        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            androidx.compose.material3.Text(
                text = stringResource(id = R.string.backlc),
                modifier = Modifier.padding(horizontal = 10.dp, vertical = 0.dp),
                style = TextStyle(
                    fontFamily = customFontFamily,
                    fontWeight = FontWeight.Light,
                    fontSize = 10.sp,
                    fontStyle = FontStyle.Normal,
                    color = Blue,
                )
            )
            androidx.compose.material3.Text(
                text = stringResource(id = R.string.save),
                modifier = Modifier.padding(horizontal = 10.dp, vertical = 0.dp),
                style = TextStyle(
                    fontFamily = customFontFamily,
                    fontWeight = FontWeight.Light,
                    fontSize = 10.sp,
                    fontStyle = FontStyle.Normal,
                    color = Red,
                )
            )

        }
        Box(

        ){
            androidx.compose.material3.Text(
                text = stringResource(id = R.string.Barbell_bp),
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
                //.padding(horizontal = 20.dp)
                .background(Color.White, RoundedCornerShape(16.dp))
        ) {
            Image(
                contentDescription = "Run Button",
                modifier = Modifier
                    .align(Alignment.Center)
                    .fillMaxSize(0.25f),
                painter = painterResource(id = R.drawable.ic_play_whitebg),
            )
        }
        TextAndFrame(text = "Número de Repeticiones",
            field ="-  1  +" )
        TextAndFrame(text = "Número de Series",
            field ="-  3  +" )
        TextAndFrame(text = "Descanso entre series",
            field ="-  0  +" )
        Row(
            horizontalArrangement = Arrangement.SpaceBetween

        ){
            androidx.compose.material3.Text(
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
            androidx.compose.material3.Text(
                text = stringResource(id = R.string.weight),
                modifier = Modifier.padding(horizontal = 10.dp, vertical = 0.dp),
                style = TextStyle(
                    fontFamily = customFontFamily,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 10.sp,
                    fontStyle = FontStyle.Normal,
                    color = DarkBlue,
                )
            )
            androidx.compose.material3.Text(
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
}
@Composable
fun TextAndFrame(text: String, field: String){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(9.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Box(
            modifier = Modifier
                .size(150.dp, 30.dp),
            contentAlignment = Alignment.BottomCenter
        ){
            androidx.compose.material3.Text(
                text = text,
                modifier = Modifier.padding(horizontal = 5.dp, vertical = 0.dp)
                    .offset(y=-7.dp),
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
                .size(75.dp, 30.dp)
                .background(Blue, RoundedCornerShape(5.dp)),
            contentAlignment = Alignment.Center
        ){
            androidx.compose.material3.Text(
                text = field,
                modifier = Modifier.padding(horizontal = 5.dp, vertical = 0.dp),
                style = TextStyle(
                    fontFamily = customFontFamily,
                    fontWeight = FontWeight.Light,
                    fontSize = 20.sp,
                    fontStyle = FontStyle.Normal,
                    color = LightBlue,
                )
            )

        }
    }

}

@Composable
@Preview
fun DetailsPeraExercisePreview(){
    DetailsPerExerciseScreen()
}
