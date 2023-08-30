package com.example.fitsteps.screens

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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
fun BodyScreen2() {
    Column (
        modifier = Modifier
            .fillMaxSize()
            .background(White),
    ){
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.TopEnd
        ) {
            Icon(
                modifier = Modifier
                    .padding(16.dp),
                painter = painterResource(id = R.drawable.ic_config_button),
                contentDescription = "Configuration Button",
                tint = DarkBlue
            )
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
        ) {
            Text(
                modifier = Modifier
                    .align(Alignment.TopStart),
                text = stringResource(id = R.string.body),
                style = TextStyle(
                    fontFamily = customFontFamily,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 30.sp,
                    fontStyle = FontStyle.Normal,
                    color = DarkBlue,
                )
            )
            Text(
                modifier = Modifier
                    .align(Alignment.BottomEnd),
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
            modifier = Modifier
                .fillMaxWidth(),
            contentAlignment = Alignment.Center,
        ){
            Navigation2()
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp, bottom = 0.dp, start = 20.dp, end = 20.dp),
            contentAlignment = Alignment.TopStart,
        ){
            Text(
                text = stringResource(id = R.string.measures),
                style = TextStyle(
                    fontFamily = customFontFamily,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 23.sp,
                    fontStyle = FontStyle.Normal,
                    color = DarkBlue,
                )
            )
        }
        Box {
            MeasuresTable()
        }
    }

}
@Composable
fun Navigation2(){
    Row(
        modifier = Modifier
            .clip(shape = RoundedCornerShape(8.dp))
            .background(LightBlue)
            .width(350.dp)
            .height(35.dp),
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f)
                .clip(shape = RoundedCornerShape(8.dp)),
            contentAlignment = Alignment.Center

        ) {
            Text(
                text = stringResource(id = R.string.body),
                style = TextStyle(
                    fontFamily = customFontFamily,
                    fontWeight = FontWeight.Medium,
                    fontSize = 15.sp,
                    fontStyle = FontStyle.Normal,
                    color = Blue,
                )
            )
        }
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Blue)
                .weight(1f)
                .clip(shape = RoundedCornerShape(8.dp)),
            contentAlignment = Alignment.Center

        ) {
            Text(
                text = stringResource(id = R.string.measures),
                style = TextStyle(
                    fontFamily = customFontFamily,
                    fontWeight = FontWeight.Medium,
                    fontSize = 15.sp,
                    fontStyle = FontStyle.Normal,
                    color = White,
                )
            )

        }
    }

}
@Composable
fun MeasuresTable(){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 10.dp, bottom = 105.dp, start = 20.dp, end = 20.dp)
            .background(
                Color.White,
                RoundedCornerShape(8)
            ),
    ) {
        Spacer(modifier = Modifier.height(2.dp))
        TableColumns(stringResource(id = R.string.Deltoids), "--")
        TableColumns(stringResource(id = R.string.Chest), "--")
        TableColumns(stringResource(id = R.string.LeftForearm), "--")
        TableColumns(stringResource(id = R.string.RightForearm), "--")
        TableColumns(stringResource(id = R.string.LeftArm), "--")
        TableColumns(stringResource(id = R.string.RightArm), "--")
        TableColumns(stringResource(id = R.string.Waist), "--")
        TableColumns(stringResource(id = R.string.Hips), "--")
        TableColumns(stringResource(id = R.string.LeftLeg), "--")
        TableColumns(stringResource(id = R.string.RightLeg), "--")
        TableColumns(stringResource(id = R.string.LeftCalf), "--")
        TableColumns(stringResource(id = R.string.RightCalf), "--")
        Spacer(modifier = Modifier.height(2.dp))


    }
}
@Composable
fun TableColumns(item1 :String, item2: String){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(9.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ){
        Text(
            text =item1,
            style = TextStyle(
                fontFamily = customFontFamily,
                fontWeight = FontWeight.Light,
                fontSize = 15.sp,
                fontStyle = FontStyle.Normal,
                color = Blue,
            )
        )
        Text(
            text = "$item2 cm",
            style = TextStyle(
                fontFamily = customFontFamily,
                fontWeight = FontWeight.Medium,
                fontSize = 15.sp,
                fontStyle = FontStyle.Normal,
                color = Blue,
            )
        )
    }

    Divider(
        modifier = Modifier
            .fillMaxWidth(),
        color = LightBlue
    )


}
@Composable
@Preview
fun BodyScreen2Preview() {
    BodyScreen2()
}