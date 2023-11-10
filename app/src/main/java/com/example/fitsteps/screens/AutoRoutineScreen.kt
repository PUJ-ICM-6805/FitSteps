package com.example.fitsteps.screens

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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import com.example.fitsteps.R
import com.example.fitsteps.navigation.Screen
import com.example.fitsteps.screens.authentication.RoundedLinearProgressIndicator
import com.example.fitsteps.screens.training.trainingMainScreen.LargeButtons
import com.example.fitsteps.ui.theme.Blue
import com.example.fitsteps.ui.theme.DarkBlue
import com.example.fitsteps.ui.theme.LightBlue
import com.example.fitsteps.ui.theme.customFontFamily

@Composable
fun AutoRoutineMainScreen(navController: NavController) {
    Box(
       modifier = Modifier
           .fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = R.drawable.personalized_routine_img),
            contentDescription = "BackgroundImg",
            modifier = Modifier
                .fillMaxSize(),
            contentScale = ContentScale.Crop,
            alignment = Alignment.BottomCenter,
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0x70000000)),
        )
        Column (
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.Start,
        ) {
            Text(
                text = stringResource(id = R.string.routine),
                style = TextStyle(
                    fontFamily = customFontFamily,
                    fontWeight = FontWeight.Light,
                    fontSize = 35.sp,
                    color = Color.White,
                ),
                modifier = Modifier
                    .padding(start = 20.dp, top = 0.dp, bottom = 0.dp)
                    .padding(all = 0.dp)
                    .height(42.dp),

            )
            Text(
                text = stringResource(id = R.string.personalized),
                style = TextStyle(
                    fontFamily = customFontFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 35.sp,
                    color = Color.White,
                ),
                modifier = Modifier
                    .padding(start = 20.dp, top = 0.dp, bottom = 0.dp)
                    .height(45.dp),
            )
            Text(
                text = stringResource(id = R.string.create_routine_description),
                style = TextStyle(
                    fontFamily = customFontFamily,
                    fontWeight = FontWeight.Medium,
                    fontSize = 12.sp,
                    color = Color(0XFFF4F4F4),
                ),
                modifier = Modifier
                    .padding(start = 20.dp, top = 0.dp, bottom = 0.dp),
            )
            LargeButtons(
                text = stringResource(id = R.string.create_new_routine),
                onClick = {
                    navController.navigate(Screen.CustomRoutineScreen1.route)
                },
                style = TextStyle(
                    fontFamily = customFontFamily,
                    fontWeight = FontWeight.Medium,
                    fontSize = 20.sp,
                ),
                modifier = Modifier
                    .padding(horizontal = 20.dp, vertical = 10.dp)
                    .fillMaxWidth(),
            )
            Spacer(modifier = Modifier.height(80.dp))
        }
    }
}

@Composable
fun CustomRoutineScreen(
    navController: NavController
){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 40.dp),
        verticalArrangement = Arrangement.SpaceAround,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(modifier = Modifier.height(40.dp))
        RoundedLinearProgressIndicator(
            progress = 0.0f,
            modifier = Modifier.padding(start = 20.dp, bottom = 40.dp, end = 20.dp, top = 5.dp)
        )
        Text(
            text = stringResource(id = R.string.what_objective),
            style = TextStyle(
                fontFamily = customFontFamily,
                fontWeight = FontWeight.SemiBold,
                fontSize = 25.sp,
                color = DarkBlue,
            ),
            modifier = Modifier
                .padding(start = 20.dp, top = 0.dp, bottom = 0.dp),
        )
        ButtonsListTwoText(
            itemsButtons = listOf(
                listOf(
                    stringResource(id = R.string.hypertrophy),
                    stringResource(id = R.string.gain_muscle)
                ),
                listOf(
                    stringResource(id = R.string.muscle_definition),
                    stringResource(id = R.string.muscle_strengthen)
                ),
                listOf(
                    stringResource(id = R.string.weight_loss),
                    stringResource(id = R.string.grease_loss)
                ),
            ),
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
        ) {
            Column (
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Bottom,
                horizontalAlignment = Alignment.CenterHorizontally,
            ){
                LargeButtons(
                    text = stringResource(id = R.string.forward),
                    onClick = {
                         navController.navigate(Screen.CustomRoutineScreen2.route)
                        },
                    modifier = Modifier
                        .padding(horizontal = 40.dp, vertical = 10.dp)
                        .fillMaxWidth(),
                )
                Spacer(modifier = Modifier.height(80.dp))
            }
        }
    }
}

@Composable
fun CustomRoutineScreenSteps(
    progress : Float = 0.0f,
    items : Array<String>,
    mainText : String,
    onClick : () -> Unit = {},
    colorUnselected : Color = LightBlue,
    colorSelected : Color = Blue,
    textColorSelected : Color = Color(0XFFF4F4F4),
    textColorUnselected : Color = Blue,
    nextOptionText : String = stringResource(id = R.string.forward),
    multipleItems: Boolean = false,
    onClose : () -> Unit = {},
    showCompletedFrame : Boolean = false,
    navController: NavController,
){
    var showCompletedRoutineFrame = remember { mutableStateOf(false) }
    if (showCompletedRoutineFrame.value) {
        RoutineCompleted(onClose = {onClose()})
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 40.dp, bottom = 80.dp, start = 20.dp, end = 20.dp),
        verticalArrangement = Arrangement.SpaceAround,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(modifier = Modifier.height(40.dp))
        RoundedLinearProgressIndicator(
            progress = progress,
            modifier = Modifier.padding(start = 20.dp, bottom = 40.dp, end = 20.dp, top = 5.dp)
        )
        Text(
            text = mainText,
            style = TextStyle(
                fontFamily = customFontFamily,
                fontWeight = FontWeight.SemiBold,
                fontSize = 25.sp,
                color = DarkBlue,
            ),
            modifier = Modifier
                .padding(start = 20.dp, top = 0.dp, bottom = 0.dp),
        )
        val selectedValue = remember { mutableStateOf("") }
        val selectedValues = remember { mutableStateListOf<String>() }
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier
                .padding(top = 40.dp),
        ) {
            items(items) { item ->
                val isSelected = if(multipleItems) (selectedValues.contains(item)) else (selectedValue.value == item)
                androidx.compose.material3.Surface(
                    modifier = Modifier
                        .selectable(
                            selected = if (multipleItems) (selectedValues.contains(item)) else (selectedValue.value == item),
                            onClick = {
                                if (multipleItems) {
                                    if (selectedValues.contains(item)) {
                                        selectedValues.remove(item)
                                    } else {
                                        selectedValues.add(item)
                                    }
                                } else {
                                    selectedValue.value = item
                                }
                            },
                            role = Role.Checkbox
                        )
                        .padding(vertical = 10.dp, horizontal = 10.dp)
                        .fillMaxWidth()
                        .height(70.dp),
                    shape = RoundedCornerShape(10.dp),
                    color = if (isSelected) colorSelected else colorUnselected,
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize(),
                        contentAlignment = Alignment.Center,
                    ) {
                        Column(
                            modifier = Modifier
                                .wrapContentSize(),
                            verticalArrangement = Arrangement.SpaceEvenly,
                            horizontalAlignment = Alignment.Start,
                        ) {
                            Text(
                                text = item,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(start = 10.dp, top = 5.dp),
                                style = TextStyle(
                                    fontFamily = customFontFamily,
                                    fontWeight = FontWeight.Medium,
                                    fontStyle = FontStyle.Normal,
                                    fontSize = 18.sp,
                                ),
                                color = if (isSelected) textColorSelected else textColorUnselected,
                                textAlign = TextAlign.Center,
                            )
                        }
                    }
                }
            }
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
        ) {
            Column (
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Bottom,
                horizontalAlignment = Alignment.CenterHorizontally,
            ){
                LargeButtons(
                    text = nextOptionText,
                    onClick = {
                        onClick()
                        if (showCompletedFrame) {
                            showCompletedRoutineFrame.value = true
                        }
                    },
                    modifier = Modifier
                        .padding(horizontal = 40.dp, vertical = 10.dp)
                        .fillMaxWidth(),
                )
            }
        }
    }
}

@Composable
fun RoutineCompleted(
    onClose: () -> Unit,
    style: TextStyle = TextStyle(
        fontFamily = customFontFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 35.sp,
        color = Color.White,
        textAlign = TextAlign.Center,
    )
){
    var resizedTextStyle by remember {mutableStateOf(style)}
    Dialog(
        onDismissRequest = {},
    ) {
        Surface(
            shape = RoundedCornerShape(16.dp),
            color = Color.Transparent,
            modifier = Modifier
                .fillMaxWidth()
                .height(350.dp),
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxSize(),
            ) {
                Image(
                    painter = painterResource(id = R.drawable.frame_routine_created),
                    contentDescription = "RoutineCompleted",
                    modifier = Modifier
                        .fillMaxSize(),
                    contentScale = ContentScale.Crop,
                    alignment = Alignment.Center,
                )
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color(0x70000000)),
                )
                Column(
                    modifier = Modifier
                        .padding(20.dp)
                        .fillMaxSize(),
                    ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_exit),
                            contentDescription = "",
                            tint = Color.White,
                            modifier = Modifier
                                .size(25.dp)
                                .clickable {onClose()}
                        )
                    }
                    Spacer(modifier = Modifier.height(20.dp))
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = stringResource(id = R.string.congratulations),
                            softWrap = false,
                            onTextLayout = {
                                result ->
                                if(result.didOverflowWidth) {
                                    resizedTextStyle = resizedTextStyle.copy(
                                        fontSize = resizedTextStyle.fontSize * 0.95f,
                                    )
                                }
                            },
                            style = resizedTextStyle,
                            modifier = Modifier
                                .padding(horizontal = 20.dp, vertical = 0.dp),
                        )
                        Text(
                            text = stringResource(id = R.string.new_routine_added),
                            style = resizedTextStyle.copy(
                                fontWeight = FontWeight.Normal,
                                fontSize = resizedTextStyle.fontSize * 0.7f,
                            ),
                            modifier = Modifier
                                .padding(horizontal = 20.dp, vertical = 0.dp),
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ButtonsListTwoText(
    itemsButtons : List<List<String>>,
    colorUnselected : Color = LightBlue,
    colorSelected : Color = Blue,
    textColorSelected : Color = Color.White,
    textColorUnselected : Color = DarkBlue,
    subTextColorSelected : Color = Color.White,
    subTextColorUnselected : Color = Blue,
) {
    val selectedValue = remember { mutableStateOf("") }
    LazyColumn(Modifier.padding(horizontal = 40.dp)) {
        items(itemsButtons) { item ->
            androidx.compose.material3.Surface(
                modifier = Modifier
                    .selectable(
                        selected = (selectedValue.value == item[0]),
                        onClick = { selectedValue.value = item[0] },
                        role = Role.RadioButton
                    )
                    .padding(vertical = 10.dp),
                shape = RoundedCornerShape(20.dp),
                color = if (selectedValue.value == item[0]) colorSelected else colorUnselected,
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight(),
                    contentAlignment = Alignment.TopStart,
                ) {
                    Column(
                        modifier = Modifier
                            .wrapContentSize(),
                        verticalArrangement = Arrangement.SpaceEvenly,
                        horizontalAlignment = Alignment.Start,
                    ) {
                        Text(
                            text = item[0],
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 10.dp, top = 5.dp),
                            style = TextStyle(
                                fontFamily = customFontFamily,
                                fontWeight = FontWeight.Medium,
                                fontStyle = FontStyle.Normal,
                                fontSize = 18.sp,
                            ),
                            color = if (selectedValue.value == item[0]) textColorSelected else textColorUnselected,
                            textAlign = TextAlign.Start,
                        )
                        Text(
                            text = item[1],
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 10.dp, bottom = 5.dp),
                            style = TextStyle(
                                fontFamily = customFontFamily,
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Normal,
                                fontStyle = FontStyle.Normal,
                            ),
                            color = if (selectedValue.value == item[0]) subTextColorSelected else subTextColorUnselected,
                            textAlign = TextAlign.Start,
                        )
                    }
                }
            }
        }
    }
}

@Composable
@Preview
fun AutoRoutineMainScreenPreview() {
    RoutineCompleted({ })
}
