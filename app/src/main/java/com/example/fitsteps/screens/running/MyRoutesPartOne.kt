package com.example.fitsteps.screens.running

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.fitsteps.R
import com.example.fitsteps.navigation.Screen
import com.example.fitsteps.screens.HamburgersDropList
import com.example.fitsteps.ui.theme.Blue
import com.example.fitsteps.ui.theme.LightBlue
import com.example.fitsteps.ui.theme.White
import com.example.fitsteps.ui.theme.customFontFamily

@Composable
fun MyRoutesPartOne(navController: NavHostController, rootNavController: NavHostController) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(
                  Color(0xFF212121))
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
                    text = stringResource(id = R.string.myroutes),
                    modifier = Modifier.padding(vertical = 0.dp),
                    style = TextStyle(
                        fontFamily = customFontFamily,
                        fontSize = 30.sp,
                        fontStyle = FontStyle.Normal,
                        color = Color(0xFFD3E7EF)
                    )
                )
            }
        }
        item {
            Box(
                contentAlignment = Alignment.BottomStart, modifier = Modifier
                    .padding(start = 20.dp)
            ) {
                Text(
                    text = "11/08/2023", //TODO: Change for the actual date
                    modifier = Modifier.padding(vertical = 0.dp),
                    style = TextStyle(
                        fontFamily = customFontFamily,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 30.sp,
                        fontStyle = FontStyle.Normal,
                        color = Color(0xFFD3E7EF)
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
                Navigation3(navController = navController)

            }
        }
        item {
            Spacer(modifier = Modifier.height(10.dp))
        }
        item {
            Box(
                contentAlignment = Alignment.BottomCenter,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.expanded_map),
                    contentDescription = "",
                    modifier = Modifier
                        .width(333.dp)
                        .height(400.dp)
                        .align(Alignment.Center),
                )
            }

        }
        item {
            Spacer(modifier = Modifier.height(80.dp))
        }
    }
}

@Composable
fun Navigation3(navController: NavController){
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
                .background(Blue)
                .weight(1f)
                .clip(shape = RoundedCornerShape(8.dp)),
            contentAlignment = Alignment.Center

        ) {
            Text(
                text = stringResource(id = R.string.myroutes),
                style = TextStyle(
                    fontFamily = customFontFamily,
                    fontWeight = FontWeight.Medium,
                    fontSize = 15.sp,
                    fontStyle = FontStyle.Normal,
                    color = White,
                )
            )
        }
        Box(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f)
                .clip(shape = RoundedCornerShape(8.dp))
                .clickable {
                    navController.navigate(Screen.RunningRouteDetails2.route)
                },
            contentAlignment = Alignment.Center

        ) {
            Text(
                text = stringResource(id = R.string.details),
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
@Composable
@Preview
fun MyRoutesPartOnePreview() {
    MyRoutesPartOne(navController = rememberNavController(),
        rootNavController = rememberNavController()
    )
}