package com.example.fitsteps.screens.body

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.fitsteps.R
import com.example.fitsteps.ui.theme.Blue
import com.example.fitsteps.ui.theme.DarkBlue
import com.example.fitsteps.ui.theme.LightBlue
import com.example.fitsteps.ui.theme.Red
import com.example.fitsteps.ui.theme.White
import com.example.fitsteps.ui.theme.customFontFamily

@Composable
fun BodyScreenFrame(
    onClick: (Boolean) -> Unit = { _ -> }
){
    var input_name by remember {
        mutableStateOf("")
    }
    Dialog(
        onDismissRequest = {},
        properties = DialogProperties(
            dismissOnBackPress = false,
            dismissOnClickOutside = false,
            usePlatformDefaultWidth = false,
        ),
    ) {
        Surface(
            shape = RoundedCornerShape(16.dp),
            color = White,
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
                .padding(20.dp),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = stringResource(id = R.string.cancel),
                        style = TextStyle(
                            fontFamily = customFontFamily,
                            fontWeight = FontWeight.Light,
                            fontSize = 15.sp,
                            fontStyle = FontStyle.Normal,
                            color = Red,
                        ),
                        modifier = Modifier.clickable { onClick(false) }
                    )
                    Text(
                        text = stringResource(id = R.string.save),
                        style = TextStyle(
                            fontFamily = customFontFamily,
                            fontWeight = FontWeight.Light,
                            fontSize = 15.sp,
                            fontStyle = FontStyle.Normal,
                            color = Red,
                        ),
                        modifier = Modifier.clickable { onClick(false) }
                    )

                }
                Text(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    text = stringResource(id = R.string.addRegistry),
                    style = TextStyle(
                        fontFamily = customFontFamily,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 23.sp,
                        fontStyle = FontStyle.Normal,
                        color = DarkBlue,
                    )
                )
                Divider(
                    modifier = Modifier.fillMaxWidth()
                        .background(LightBlue)
                )
                Spacer(Modifier.height(10.dp))
                Row(
                    Modifier.padding(horizontal = 10.dp),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_weight_icon),
                        contentDescription = "weight",
                        modifier = Modifier
                            .size(25.dp),
                        tint = Red
                    )
                    Text(
                        text = stringResource(id = R.string.weight2),
                        style = TextStyle(
                            fontFamily = customFontFamily,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 20.sp,
                            fontStyle = FontStyle.Normal,
                            color = DarkBlue,
                        )
                    )
                }
                androidx.compose.material.Surface(
                    shape = RoundedCornerShape(10.dp),
                    color = Color.White,
                    elevation = 3.dp,
                    modifier = Modifier
                        .padding(horizontal = 10.dp, vertical = 0.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(),
                        contentAlignment = Alignment.CenterStart,
                    ) {
                        TextField(
                            value = input_name,
                            onValueChange = { input_name = it },
                            placeholder = {
                                Text(
                                    text = stringResource(id = R.string.kg),
                                    style = TextStyle(
                                        fontFamily = customFontFamily,
                                        fontWeight = FontWeight.Light,
                                        fontSize = 15.sp,
                                        color = Blue,
                                        textAlign = TextAlign.Start,
                                    ),
                                )
                            },
                            textStyle = TextStyle(
                                fontFamily = customFontFamily,
                                fontWeight = FontWeight.Normal,
                                fontSize = 12.sp,
                                color = DarkBlue,
                                textAlign = TextAlign.Start,
                            ),
                            colors = TextFieldDefaults.textFieldColors(
                                backgroundColor = Color.White,
                                focusedIndicatorColor = DarkBlue,
                                cursorColor = DarkBlue,
                                unfocusedIndicatorColor = Color.Transparent,
                                disabledIndicatorColor = Color.Transparent,
                            ),
                            modifier = Modifier
                                .fillMaxWidth(),
                        )
                    }
                }

            }

        }
    }
}

@Composable
@Preview
fun BodyScreenFramePreview(){
    BodyScreenFrame()
}