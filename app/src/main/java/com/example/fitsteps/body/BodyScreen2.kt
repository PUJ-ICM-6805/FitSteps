package com.example.fitsteps.body

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.fitsteps.R
import com.example.fitsteps.navigation.Screen
import com.example.fitsteps.screens.HamburgersDropList
import com.example.fitsteps.ui.theme.Blue
import com.example.fitsteps.ui.theme.DarkBlue
import com.example.fitsteps.ui.theme.LightBlue
import com.example.fitsteps.ui.theme.Red
import com.example.fitsteps.ui.theme.White
import com.example.fitsteps.ui.theme.customFontFamily

@Composable
fun BodyScreen2(navController: NavHostController, rootNavController: NavHostController) {
    LazyColumn (
        modifier = Modifier
            .fillMaxSize()
            .background(White),
    ){
        item {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.TopEnd
            ) {
                Column {
                    Spacer(modifier = Modifier.height(20.dp))
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(30.dp),
                        contentAlignment = Alignment.CenterStart,
                    ) {
                        HamburgersDropList(
                            navController = navController,
                            rootNavController = rootNavController
                        )
                    }
                    Spacer(modifier = Modifier.height(40.dp))
                }
            }
        }
        item {
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
                        .align(Alignment.CenterEnd)
                        .clickable { navController.navigate(Screen.BodyScreenEditable.route) },
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
        }
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center,
            ) {
                Navigation2(navController = navController)
            }
        }
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp, bottom = 0.dp, start = 20.dp, end = 20.dp),
                contentAlignment = Alignment.TopStart,
            ) {
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
        }
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 2.dp, start = 20.dp, end = 20.dp, bottom = 10.dp)
                    .height(500.dp),
                contentAlignment = Alignment.TopStart,
            ) {
                MeasuresTable(editable= false)
            }
        }
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp, bottom = 0.dp, start = 20.dp, end = 20.dp),
                contentAlignment = Alignment.TopStart,
            ) {
                Text(
                    stringId = R.string.gallery,
                    weight = FontWeight.SemiBold,
                    size = 23, color = DarkBlue
                )
            }
        }
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 2.dp, bottom = 0.dp, start = 20.dp, end = 0.dp),
                contentAlignment = Alignment.TopStart
            ) {
                LazyRow() {
                    item {
                        GalleryCard()
                    }
                    item {
                        GalleryCard()
                    }
                }
            }
        }
        item {
            Spacer(modifier = Modifier.height(80.dp))
        }
    }

}
@Composable
fun Navigation2(navController: NavController){
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
                .clip(shape = RoundedCornerShape(8.dp))
                .clickable {
                    navController.popBackStack()
                },
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
fun MeasuresTable(editable: Boolean){
    Box(
        modifier = Modifier
            .background(
                Color.White,
                RoundedCornerShape(8.dp)
            ),
    ) {
        Spacer(modifier = Modifier.height(2.dp))
        Column() {
            TableColumns(stringResource(id = R.string.Deltoids), "--",editable)
            TableColumns(stringResource(id = R.string.Chest), "--", editable)
            TableColumns(stringResource(id = R.string.LeftForearm), "--", editable)
            TableColumns(stringResource(id = R.string.RightForearm), "--", editable)
            TableColumns(stringResource(id = R.string.LeftArm), "--", editable)
            TableColumns(stringResource(id = R.string.RightArm), "--", editable)
            TableColumns(stringResource(id = R.string.Waist), "--", editable)
            TableColumns(stringResource(id = R.string.Hips), "--", editable)
            TableColumns(stringResource(id = R.string.LeftLeg), "--", editable)
            TableColumns(stringResource(id = R.string.RightLeg), "--", editable)
            TableColumns(stringResource(id = R.string.LeftCalf), "--", editable)
            TableColumns(stringResource(id = R.string.RightCalf), "--", editable)
        }
        Spacer(modifier = Modifier.height(2.dp))
    }
}
@Composable
fun TableColumns(item1 :String, item2: String, editable: Boolean){
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
if(editable) {
    Box(
        modifier = Modifier.size(75.dp, 30.dp)
            .background(Blue, RoundedCornerShape(5.dp)),
        contentAlignment = Alignment.CenterEnd
    ) {
        Text(
            modifier = Modifier.padding(end = 7.dp),
            text = "$item2 cm",
            style = TextStyle(
                fontFamily = customFontFamily,
                fontWeight = FontWeight.Medium,
                fontSize = 15.sp,
                fontStyle = FontStyle.Normal,
                color = LightBlue,
            )
        )
    }
}else{
    Box(
        modifier = Modifier.size(75.dp, 30.dp),
        contentAlignment = Alignment.CenterEnd
    ) {
        Text(
            modifier = Modifier.padding(end = 7.dp),
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
}
    }
    Divider(
        modifier = Modifier
            .fillMaxWidth(),
        color = LightBlue
    )


}
@Composable
fun GalleryCard() {
    Card(
        modifier = Modifier
            .height(150.dp)
            .width(300.dp)
            .padding(end = 20.dp),
        shape = RoundedCornerShape(20.dp),
        backgroundColor = Color.White,

    ){
        Box(
            modifier = Modifier
                .fillMaxSize(),
        ) {
            Image(
                painter = painterResource(id = R.drawable.woman),
                contentDescription = "",
                contentScale = ContentScale.Crop,
                alignment = Alignment.BottomCenter,
            )
            Text(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(10.dp),
                text = "30/08/23",
                style = TextStyle(
                    fontFamily = customFontFamily,
                    fontWeight = FontWeight.Normal,
                    fontSize = 20.sp,
                    fontStyle = FontStyle.Italic,
                    color = White,
                )
            )
        }
    }
}
@Composable
fun Text(stringId: Int, weight: FontWeight,size: Int, color: Color){
    Text(
        text = stringResource(id = stringId),
        style = TextStyle(
            fontFamily = customFontFamily,
            fontWeight = weight,
            fontSize = size.sp,
            fontStyle = FontStyle.Normal,
            color = color,
        )
    )
}
@Composable
@Preview
fun BodyScreen2Preview() {
    BodyScreen2(navController = rememberNavController(), rootNavController = rememberNavController())
}