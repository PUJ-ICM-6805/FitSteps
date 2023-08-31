package com.example.fitsteps.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.fitsteps.R
import com.example.fitsteps.ui.theme.DarkBlue
import com.example.fitsteps.ui.theme.Red
import com.example.fitsteps.ui.theme.White
import com.example.fitsteps.ui.theme.customFontFamily

@Composable
fun BodyScreenFrame(){
    Surface(
        shape = RoundedCornerShape(16.dp),
        color = White,
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp)
            .padding(20.dp),
    ){
        Column (
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        ){
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ){
                Text(
                    text = stringResource(id = R.string.cancel),
                    style = TextStyle(
                        fontFamily = customFontFamily,
                        fontWeight = FontWeight.Light,
                        fontSize = 15.sp,
                        fontStyle = FontStyle.Normal,
                        color = Red,
                    )
                )
                Text(
                    text = stringResource(id = R.string.save),
                    style = TextStyle(
                        fontFamily = customFontFamily,
                        fontWeight = FontWeight.Light,
                        fontSize = 15.sp,
                        fontStyle = FontStyle.Normal,
                        color = Red,
                    )
                )

            }
            Text(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                text = stringResource(id = R.string.addRegistry),
                style = TextStyle(
                    fontFamily = customFontFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp,
                    fontStyle = FontStyle.Normal,
                    color = DarkBlue,
                )
            )
        }

    }
}

@Composable
@Preview
fun BodyScreenFramePreview(){
    BodyScreenFrame()
}