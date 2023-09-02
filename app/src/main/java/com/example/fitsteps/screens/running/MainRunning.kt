package com.example.fitsteps.screens.running

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.fitsteps.R
import com.example.fitsteps.navigation.Screen
import com.example.fitsteps.screens.HamburgersDropList
import com.example.fitsteps.screens.LargeButtons
import com.example.fitsteps.ui.theme.DarkBlue
import com.example.fitsteps.ui.theme.customFontFamily

@Composable
fun MainRunning(navController: NavHostController, rootNavController: NavHostController) {
    val painterMap = painterResource(id = R.drawable.map)
    LazyColumn(
    ) {
        item {
            Spacer(modifier = Modifier.height(20.dp))
        }
        item {
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
        }
        item {
            Spacer(modifier = Modifier.height(40.dp))
        }
        item {
            Box(
                contentAlignment = Alignment.BottomStart, modifier = Modifier
                    .padding(start = 20.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.running),
                    modifier = Modifier.padding(vertical = 0.dp),
                    style = TextStyle(
                        fontFamily = customFontFamily,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 30.sp,
                        fontStyle = FontStyle.Normal,
                        color = DarkBlue
                    )
                )
            }
        }
        item {
            Spacer(modifier = Modifier.height(10.dp))
        }
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                shape = RoundedCornerShape(15.dp),
                elevation = 5.dp
            ) {
                Box(
                    contentAlignment = Alignment.BottomCenter,
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    Image(
                        painter = painterMap,
                        contentDescription = "",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(350.dp)
                    )
                    Box(
                        contentAlignment = Alignment.BottomCenter, modifier = Modifier
                            .padding(start = 20.dp)
                            .fillMaxHeight()
                    ) {
                        LargeButtons(
                            text = stringResource(id = R.string.iniciar),
                            onClick = {
                                navController.navigate(Screen.RunningMapScreen.route)
                            },
                            modifier = Modifier
                                .padding(horizontal = 15.dp, vertical = 20.dp)
                                .size(200.dp, 50.dp),
                            needIcon = true,
                            icon = R.drawable.ic_play_icon
                        )
                    }
                }
            }
        }
        item{
            Spacer(modifier = Modifier.height(5.dp))
        }
        item {
            Box(
                contentAlignment = Alignment.BottomStart, modifier = Modifier
                    .padding(start = 20.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.myroutes),
                    modifier = Modifier.padding(horizontal = 20.dp, vertical = 0.dp),
                    color = DarkBlue,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                )
            }
        }
        item{
            Spacer(modifier = Modifier.height(5.dp))
        }
        item{
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 20.dp)
            ) {
                items(3) {//TODO: Change for the actual routes
                    ImageCard(
                        painter = painterResource(id = R.drawable.map),
                        contentDescription = "",
                        title = "11/08/2023", //TODO: Change for the actual date
                        modifier = Modifier
                            .padding(end = 16.dp)
                            .width(300.dp)
                            .height(175.dp),
                        navController = navController
                    )
                }
            }
        }

        item{
            Spacer(modifier = Modifier.height(80.dp))
        }
    }
}

@Composable
fun ImageCard(
    painter: Painter,
    contentDescription: String,
    title: String,
    modifier: Modifier = Modifier,
    navController: NavHostController,
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable {
                navController.navigate(Screen.RunningRouteDetails.route)
            },
        shape = RoundedCornerShape(15.dp),
        elevation = 5.dp
    ) {
        Box(
            modifier = Modifier
                .height(200.dp)
                .width(300.dp)
        ) {
            Image(
                painter = painter,
                contentDescription = contentDescription,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(12.dp),
                contentAlignment = Alignment.BottomStart
            ) {
                Text(
                    title,
                    style = TextStyle(
                        color = Color(0xFFD3E7EF),
                        fontSize = 16.sp,
                        fontFamily = FontFamily(Font(R.font.poppinslight)),
                        fontWeight = FontWeight(400),
                        fontStyle = FontStyle.Italic
                    )
                )
            }
        }
    }
}


@Composable
@Preview
fun MainRunningPreview() {
    MainRunning(
        navController = rememberNavController(),
        rootNavController = rememberNavController()
    )
}