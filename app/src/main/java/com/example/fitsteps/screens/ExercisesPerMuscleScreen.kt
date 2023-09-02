package com.example.fitsteps.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.fitsteps.R
import com.example.fitsteps.ui.theme.DarkBlue
import com.example.fitsteps.ui.theme.LightBlue
import com.example.fitsteps.ui.theme.White
import com.example.fitsteps.ui.theme.customFontFamily

@Composable
fun ExercisesPerMuscleScreen(
    navController: NavHostController,
) {
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
            androidx.compose.material3.Text(
                text = stringResource(id = R.string.chest),
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
                    color = Color.White,
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
                androidx.compose.material3.Text(
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
            ExercisesCardList()
        }


    }


}
@Composable
fun ExercisesCardList(){
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier
            .padding(top = 20.dp)
    ){
        items(8){item->
            Box(Modifier.padding(10.dp)) {
                ExercisesCard(
                    painter = painterResource(id = R.drawable.benchpress),
                    contentDescription = "a",
                    title = "Nombre ejercicio"
                )
            }
        }
    }
}
@Composable
fun ExercisesCard(
    painter: Painter,
    contentDescription: String,
    title: String,
    modifier: Modifier = Modifier
){
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(10.dp),
        //elevation = 5.dp
        ){
        Box(
            modifier =Modifier.height(150.dp)
        ){
          Image(
              modifier =Modifier.fillMaxSize(),
              painter =painter,
              contentDescription = contentDescription,
              contentScale = ContentScale.FillBounds
          )
            Image(
                contentDescription = "Run Button",
                modifier = Modifier
                    .align(Alignment.Center)
                    .fillMaxSize(0.3f),
                painter = painterResource(id = R.drawable.ic_play_whitebg),
                )
           Box(
               modifier = Modifier.fillMaxSize()
                   .background(
                       Brush.verticalGradient(
                           colors = listOf(
                               Color.Transparent,
                               Color.Black
                           ),
                           startY = 300f
                       )
                   )
           )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 12.dp, end = 12.dp, top =12.dp, bottom = 5.dp),
                contentAlignment = Alignment.BottomCenter
            ){
                androidx.compose.material3.Text(
                    text = title,
                    style = TextStyle(
                        fontFamily = customFontFamily,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 15.sp,
                        fontStyle = FontStyle.Normal,
                        color = Color.White,
                    )
                )
            }

        }
    }
}
@Composable
@Preview
fun ExercisesPerMuscleScreenPreview() {
    ExercisesPerMuscleScreen(navController = rememberNavController())
}