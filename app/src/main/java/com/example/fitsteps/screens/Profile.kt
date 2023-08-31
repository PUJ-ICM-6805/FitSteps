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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddAPhoto
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.SmallFloatingActionButton
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
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.fitsteps.R
import com.example.fitsteps.navigation.CUSTOM_ROUTINE_ROUTE
import com.example.fitsteps.ui.theme.Blue
import com.example.fitsteps.ui.theme.DarkBlue
import com.example.fitsteps.ui.theme.LightBlue
import com.example.fitsteps.ui.theme.Red
import com.example.fitsteps.ui.theme.customFontFamily

@Composable
fun ProfileScreen(navController: NavHostController, rootNavController: NavHostController) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
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
                HamburgersDropList(navController = navController, rootNavController = rootNavController)
            }
        }
        item {
            Spacer(modifier = Modifier.height(40.dp))
        }
        item {
            Text(
                text = stringResource(id = R.string.profile),
                modifier = Modifier.padding(horizontal = 15.dp, vertical = 0.dp),
                style = TextStyle(
                    fontFamily = customFontFamily,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 30.sp,
                    fontStyle = FontStyle.Normal,
                    color = DarkBlue,
                )
            )
        }
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Box(
                    modifier = Modifier
                        .size(150.dp),
                    contentAlignment = Alignment.BottomEnd,
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.profile_example_one),
                        contentDescription = null,
                        modifier = Modifier
                            .size(150.dp)
                            .clip(CircleShape)
                    )
                    SmallFloatingActionButton(
                        onClick = { /*TODO*/ },
                        shape = CircleShape,
                        contentColor = Blue,
                        containerColor = LightBlue
                    ) {
                        Icon(
                            imageVector = Icons.Default.AddAPhoto,
                            contentDescription = null,
                            tint = Blue
                        )
                    }
                }
            }
        }
        //TODO change the items for the actual profile data
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 15.dp, vertical = 5.dp),
            ) {
                Text(
                    text = stringResource(id = R.string.name),
                    modifier = Modifier.padding(horizontal = 15.dp, vertical = 5.dp),
                    style = TextStyle(
                        fontFamily = customFontFamily,
                        fontWeight = FontWeight.Medium,
                        fontSize = 16.sp,
                        fontStyle = FontStyle.Normal,
                        color = DarkBlue,
                    )
                )
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(40.dp)
                        .background(color = Color.White, shape = RoundedCornerShape(10.dp)),
                    contentAlignment = Alignment.CenterStart,
                ) {
                    Text(
                        text = "Santiago",
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 0.dp),
                        style = TextStyle(
                            fontFamily = customFontFamily,
                            fontWeight = FontWeight.Light,
                            fontSize = 16.sp,
                            fontStyle = FontStyle.Normal,
                            color = Blue,
                        )
                    )
                }
            }
        }
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 15.dp, vertical = 5.dp),
            ) {
                Text(
                    text = stringResource(id = R.string.email),
                    modifier = Modifier.padding(horizontal = 15.dp, vertical = 5.dp),
                    style = TextStyle(
                        fontFamily = customFontFamily,
                        fontWeight = FontWeight.Medium,
                        fontSize = 16.sp,
                        fontStyle = FontStyle.Normal,
                        color = DarkBlue,
                    )
                )
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(40.dp)
                        .background(color = Color.White, shape = RoundedCornerShape(10.dp)),
                    contentAlignment = Alignment.CenterStart,
                ) {
                    Text(
                        text = "santiago@mail.com",
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 0.dp),
                        style = TextStyle(
                            fontFamily = customFontFamily,
                            fontWeight = FontWeight.Light,
                            fontSize = 16.sp,
                            fontStyle = FontStyle.Normal,
                            color = Blue,
                        )
                    )
                }
            }
        }
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 15.dp, vertical = 5.dp),
            ) {
                Text(
                    text = stringResource(id = R.string.birth_date),
                    modifier = Modifier.padding(horizontal = 15.dp, vertical = 5.dp),
                    style = TextStyle(
                        fontFamily = customFontFamily,
                        fontWeight = FontWeight.Medium,
                        fontSize = 16.sp,
                        fontStyle = FontStyle.Normal,
                        color = DarkBlue,
                    )
                )
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(40.dp)
                        .background(color = Color.White, shape = RoundedCornerShape(10.dp)),
                    contentAlignment = Alignment.CenterStart,
                ) {
                    Text(
                        text = "05/04/1998",
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 0.dp),
                        style = TextStyle(
                            fontFamily = customFontFamily,
                            fontWeight = FontWeight.Light,
                            fontSize = 16.sp,
                            fontStyle = FontStyle.Normal,
                            color = Blue,
                        )
                    )
                }
            }
        }
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 15.dp, vertical = 5.dp),
            ) {
                Text(
                    text = stringResource(id = R.string.gender),
                    modifier = Modifier.padding(horizontal = 15.dp, vertical = 5.dp),
                    style = TextStyle(
                        fontFamily = customFontFamily,
                        fontWeight = FontWeight.Medium,
                        fontSize = 16.sp,
                        fontStyle = FontStyle.Normal,
                        color = DarkBlue,
                    )
                )
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(40.dp)
                        .background(color = Color.White, shape = RoundedCornerShape(10.dp)),
                    contentAlignment = Alignment.CenterStart,
                ) {
                    Text(
                        text = "Hombre",
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 0.dp),
                        style = TextStyle(
                            fontFamily = customFontFamily,
                            fontWeight = FontWeight.Light,
                            fontSize = 16.sp,
                            fontStyle = FontStyle.Normal,
                            color = Blue,
                        )
                    )
                }
            }
        }
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 15.dp, vertical = 5.dp),
            ) {
                Text(
                    text = stringResource(id = R.string.weight),
                    modifier = Modifier.padding(horizontal = 15.dp, vertical = 5.dp),
                    style = TextStyle(
                        fontFamily = customFontFamily,
                        fontWeight = FontWeight.Medium,
                        fontSize = 16.sp,
                        fontStyle = FontStyle.Normal,
                        color = DarkBlue,
                    )
                )
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(40.dp)
                        .background(color = Color.White, shape = RoundedCornerShape(10.dp)),
                    contentAlignment = Alignment.CenterStart,
                ) {
                    Text(
                        text = "70",
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 0.dp),
                        style = TextStyle(
                            fontFamily = customFontFamily,
                            fontWeight = FontWeight.Light,
                            fontSize = 16.sp,
                            fontStyle = FontStyle.Normal,
                            color = Blue,
                        )
                    )
                }
            }
        }
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 15.dp, vertical = 5.dp),
            ) {
                Text(
                    text = stringResource(id = R.string.height),
                    modifier = Modifier.padding(horizontal = 15.dp, vertical = 5.dp),
                    style = TextStyle(
                        fontFamily = customFontFamily,
                        fontWeight = FontWeight.Medium,
                        fontSize = 16.sp,
                        fontStyle = FontStyle.Normal,
                        color = DarkBlue,
                    )
                )
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(40.dp)
                        .background(color = Color.White, shape = RoundedCornerShape(10.dp)),
                    contentAlignment = Alignment.CenterStart,
                ) {
                    Text(
                        text = "180",
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 0.dp),
                        style = TextStyle(
                            fontFamily = customFontFamily,
                            fontWeight = FontWeight.Light,
                            fontSize = 16.sp,
                            fontStyle = FontStyle.Normal,
                            color = Blue,
                        )
                    )
                }
            }
        }
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 15.dp, vertical = 5.dp),
            ) {
                Text(
                    text = stringResource(id = R.string.experience),
                    modifier = Modifier.padding(horizontal = 15.dp, vertical = 5.dp),
                    style = TextStyle(
                        fontFamily = customFontFamily,
                        fontWeight = FontWeight.Medium,
                        fontSize = 16.sp,
                        fontStyle = FontStyle.Normal,
                        color = DarkBlue,
                    )
                )
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(40.dp)
                        .background(color = Color.White, shape = RoundedCornerShape(10.dp)),
                    contentAlignment = Alignment.CenterStart,
                ) {
                    Text(
                        text = "Intermedio",
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 0.dp),
                        style = TextStyle(
                            fontFamily = customFontFamily,
                            fontWeight = FontWeight.Light,
                            fontSize = 16.sp,
                            fontStyle = FontStyle.Normal,
                            color = Blue,
                        )
                    )
                }
            }
        }
        item {
            Spacer(modifier = Modifier.height(20.dp))
        }

    }
}

@Composable
@Preview
fun ProfileScreenPreview() {
    ProfileScreen(navController = rememberNavController(), rootNavController = rememberNavController())
}