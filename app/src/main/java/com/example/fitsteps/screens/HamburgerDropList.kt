package com.example.fitsteps.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import androidx.core.content.res.TypedArrayUtils.getString
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.fitsteps.R
import com.example.fitsteps.navigation.HOME_ROUTE
import com.example.fitsteps.navigation.ROOT_ROUTE
import com.example.fitsteps.navigation.Screen
import com.example.fitsteps.ui.theme.Blue
import com.example.fitsteps.ui.theme.DarkBlue
import com.example.fitsteps.ui.theme.LightBlue
import com.example.fitsteps.ui.theme.customFontFamily

@Composable
fun HamburgersDropList(navController: NavHostController, rootNavController: NavHostController){
    val suggestions = listOf(
        stringResource(id = R.string.home),
        stringResource(id = R.string.profile),
        stringResource(id = R.string.close_session),
    )
    var expanded by remember { mutableStateOf(false) }
    var selectedText by remember { mutableStateOf("") }

    var textfieldSize by remember { mutableStateOf(Size.Zero) }
    Box(
        modifier = Modifier
            .wrapContentHeight()
            .background(Color.Transparent)
            .width(
                if (with(LocalDensity.current) { textfieldSize.width.toDp() } > 80.dp) {
                    with(LocalDensity.current) { textfieldSize.width.toDp() }
                } else {
                    80.dp
                }
            )
            .padding(20.dp),
        contentAlignment = Alignment.TopStart,
    ) {
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .wrapContentWidth()
                .background(Blue)
                .onGloballyPositioned { coordinates ->
                    //This value is used to assign to the DropDown the same width
                    textfieldSize = coordinates.size.toSize()
                }
                .clip(RoundedCornerShape(0.dp))
                .align(Alignment.TopStart),
            offset = DpOffset(0.dp, 0.dp)
        ) {
            DropdownMenuItem(onClick = { /*TODO*/ }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_hamburger),
                    contentDescription = "menu",
                    Modifier
                        .clickable { expanded = !expanded }
                        .size(30.dp)
                        .onGloballyPositioned { coordinates ->
                            //This value is used to assign to the DropDown the same width
                            if (textfieldSize.width < coordinates.size.toSize().width)
                                textfieldSize = coordinates.size.toSize()
                        }
                        .padding(),
                    tint = LightBlue,
                )
            }
            suggestions.forEach { label ->
                DropdownMenuItem(
                    onClick = {
                        selectedText = label
                        when(selectedText) {
                            suggestions[1] -> {navController.navigate(
                                Screen.ProfileScreen.route,
                                )
                            }
                            suggestions[2] -> {rootNavController.navigate(
                                ROOT_ROUTE,
                                builder = {
                                    popUpTo(route = ROOT_ROUTE) {
                                        inclusive = true
                                    }
                                })
                            }
                            else -> {navController.navigate(
                                HOME_ROUTE,
                                builder = {
                                    popUpTo(route = HOME_ROUTE) {
                                        inclusive = true
                                    }
                                })
                            }
                        }
                        expanded = false
                    },
                    modifier = Modifier
                        .background(Color.Transparent)
                ) {
                    Text(
                        text = label,
                        style = TextStyle(
                            fontFamily = customFontFamily,
                            fontWeight = FontWeight.Light,
                            fontSize = 18.sp,
                            fontStyle = FontStyle.Normal,
                            color = Color(0xFFF4F4F4),
                        ),
                    )
                }
            }
        }

        if (!expanded) {
            Column (
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 15.dp, top = 15.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_hamburger),
                    contentDescription = "menu",
                    Modifier
                        .clickable { expanded = !expanded }
                        .requiredSize(30.dp)
                        .onGloballyPositioned { coordinates ->
                            //This value is used to assign to the DropDown the same width
                            if (textfieldSize.width < coordinates.size.toSize().width)
                                textfieldSize = coordinates.size.toSize()
                        },
                    tint = LightBlue,
                )
            }
        }
    }
}


@Composable
@Preview
fun HamburgersDropListPreview(){
    HamburgersDropList(navController = rememberNavController(), rootNavController = rememberNavController())
}
