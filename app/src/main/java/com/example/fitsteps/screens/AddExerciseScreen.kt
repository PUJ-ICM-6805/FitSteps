package com.example.fitsteps.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.fitsteps.R
import com.example.fitsteps.ui.theme.Blue
import com.example.fitsteps.ui.theme.DarkBlue
import com.example.fitsteps.ui.theme.LightBlue
import com.example.fitsteps.ui.theme.customFontFamily
import  androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.compose.rememberNavController
import com.example.fitsteps.navigation.Screen
import com.example.fitsteps.ui.theme.White

@Composable
fun AddExerciseScreen(navController: NavHostController){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(White)
            .padding(horizontal = 20.dp, vertical = 10.dp)
    ){
            Spacer(Modifier.height(40.dp))
            Box(
                Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = stringResource(id = R.string.addExercise),
                    modifier = Modifier
                        .padding(horizontal = 25.dp, vertical =5.dp),
                    style = TextStyle(
                        fontFamily = customFontFamily,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 20.sp,
                        fontStyle = FontStyle.Normal,
                        color = DarkBlue,
                    ),
                )
            }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(40.dp)
                .background(
                    color = androidx.compose.ui.graphics.Color.White,
                    shape = RoundedCornerShape(10.dp)
                ),
            contentAlignment = Alignment.CenterStart,
        ) {
            Row(

            ){
                Icon(
                    painter = painterResource(id = R.drawable.ic_search_icon),
                    contentDescription = "Search icon",
                    modifier = Modifier
                        .padding(horizontal = 10.dp, vertical = 0.dp),
                    tint = LightBlue
                )
                Text(
                    text = stringResource(id = R.string.search),
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 0.dp),
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
        Box(
            modifier = Modifier
                .padding(bottom = 60.dp)
        ) {
            MusclesList(navController = navController)
        }
    }

}
@Composable
fun MusclesList(
    navController: NavHostController,
){
    var resizedTextStyle by remember {
        mutableStateOf(
            TextStyle(
                fontFamily = customFontFamily,
                fontWeight = FontWeight.SemiBold,
                fontSize = 20.sp,
                fontStyle = FontStyle.Normal,
                color = Blue,
            )
        ) }
    val items: Array<String> = stringArrayResource(id = R.array.muscles)
    val color = remember{
        mutableStateOf(LightBlue)
    }
    LazyVerticalGrid(
    columns = GridCells.Fixed(3),
    modifier = Modifier
        .padding(top = 20.dp)
        .wrapContentHeight(),
    ){
        items(items){item->
            Box(
                modifier = Modifier
                    .padding(horizontal = 5.dp, vertical = 10.dp)
                    .aspectRatio(1f)
                    .clip(RoundedCornerShape(10.dp))
                    .background(color.value)
                    .clickable {
                        color.value = Blue
                        navController.navigate(Screen.AddSpecificExerciseScreen.route)
                    },
                contentAlignment = Alignment.Center
            ){
               Text(
                    modifier = Modifier
                        .align(Alignment.Center),
                    text = item,
                    style = resizedTextStyle,
                    /*style = TextStyle(
                        fontFamily = customFontFamily,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 20.sp,
                        fontStyle = FontStyle.Normal,
                        color = Blue,
                    ),*/
                   onTextLayout = { result ->
                       if (result.didOverflowWidth) {
                           resizedTextStyle = resizedTextStyle.copy(
                               fontSize = resizedTextStyle.fontSize * 0.95f,
                           )
                       }
                   },
                   softWrap = false,
                )
            }
        }
    }
}
@Composable
@Preview
fun AddExerciseScreenPreview(){
    AddExerciseScreen(navController = rememberNavController())
}