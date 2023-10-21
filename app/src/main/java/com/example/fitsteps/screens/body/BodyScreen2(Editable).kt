package com.example.fitsteps.screens.body

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.fitsteps.R
import com.example.fitsteps.firebaseData.firebaseBodyMeasuresData.MeasuresViewModel
import com.example.fitsteps.screens.HamburgersDropList
import com.example.fitsteps.screens.LargeButtons
import com.example.fitsteps.ui.theme.DarkBlue
import com.example.fitsteps.ui.theme.Red
import com.example.fitsteps.ui.theme.White
import com.example.fitsteps.ui.theme.customFontFamily
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

@Composable
fun BodyScreen2Edit(
    navController: NavHostController,
    rootNavController: NavHostController,
    measuresViewModel: MeasuresViewModel = MeasuresViewModel()
) {
    val userid = Firebase.auth.currentUser?.uid
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
                androidx.compose.material3.Text(
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
                androidx.compose.material3.Text(
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .clickable {
                            navController.popBackStack()
                            measuresViewModel.uploadMeasure()
                        },
                    text = stringResource(id = R.string.save),
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
                androidx.compose.material3.Text(
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
                    .padding(top = 2.dp, start = 20.dp, end = 20.dp, bottom = 10.dp),
                contentAlignment = Alignment.TopStart,
            ) {
                MeasuresTable(true, measuresViewModel)
            }
        }
        item{
            Box(
                modifier = Modifier.fillMaxSize()
                    .offset(y = (-20).dp),

            ){
                LargeButtons(
                    text = stringResource(id = R.string.addPhoto) ,
                    onClick = {},
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp),
                    needIcon = true,
                    icon = R.drawable.ic_photo_icon
                )
            }
        }
        item {
            Spacer(modifier = Modifier.height(80.dp))
        }
    }


}

@Composable
@Preview
fun BodyScreen2EditPreview() {
    BodyScreen2Edit(navController = rememberNavController(), rootNavController = rememberNavController())
}
