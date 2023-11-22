package com.example.fitsteps.screens.training.trainingMainScreen

import android.content.Context
import android.util.Log
import androidx.browser.trusted.sharing.ShareTarget.RequestMethod
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.lifecycle.viewmodel.compose.viewModel
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.fitsteps.R
import com.example.fitsteps.firebaseData.firebaseOwnProgramData.TrainingProgram
import com.example.fitsteps.firebaseData.firebaseOwnProgramData.TrainingProgramViewModel
import com.example.fitsteps.firebaseData.firebaseOwnProgramData.rest.ImageFromJSONViewModel
import com.example.fitsteps.firebaseData.firebaseOwnProgramData.rest.VolleySingleton
import com.example.fitsteps.ui.theme.Blue
import com.example.fitsteps.ui.theme.DarkBlue
import com.example.fitsteps.ui.theme.LightBlue
import com.example.fitsteps.ui.theme.customFontFamily
import org.json.JSONArray


@Composable
fun CreateNewTrainingPlanFrame(
    style: TextStyle = TextStyle(
        fontFamily = customFontFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 35.sp,
        color = Color.White,
        textAlign = TextAlign.Center,
    ),
    trainingProgramViewModel: TrainingProgramViewModel,
    show: Boolean,
    setShow: (Boolean, Boolean) -> Unit,
    imagesViewModel: ImageFromJSONViewModel = remember { ImageFromJSONViewModel() },
    imagesInUse : MutableList<String> = mutableListOf(),
) {
    var resizedTextStyle by remember { mutableStateOf(style) }
    var input_name by remember {
        mutableStateOf("")
    }
    var input_desc by remember {
        mutableStateOf("")
    }
    val localContext = LocalContext.current
    //view model for images
    Log.d("API", "first cond: %s".format(!imagesViewModel.alreadyRequested.value))
    Log.d("API", "Images: %s".format(imagesViewModel.getImageLinks()))
    if (imagesViewModel.getImageLinks().isEmpty()) {
        Log.d("API", "Requesting images")
        imagesAPIRequest(localContext, imagesViewModel)
    }
    Log.d("API", "Images: %s".format(imagesViewModel.getImageLinks()))
    imagesInUse.forEach {
        imagesViewModel.markImageAsUsed(it)
    }

    Dialog(
        onDismissRequest = {},
        properties = DialogProperties(
            dismissOnBackPress = false,
            dismissOnClickOutside = false,
            usePlatformDefaultWidth = false,
        ),
    ) {
        Card(
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Transparent)
                .height(400.dp)
                .padding(horizontal = 20.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color(0xFFF4F4F4),
            ),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 3.dp
            ),
        ) {
            Box(
                modifier = Modifier
                    .wrapContentHeight()
                    .fillMaxWidth(),
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight(),
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(2f),
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.frame_create_routine_foto),
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
                                .fillMaxSize(),
                            verticalArrangement = Arrangement.Bottom,
                        ) {
                            Text(
                                text = stringResource(id = R.string.create_new_plan),
                                softWrap = false,
                                style = TextStyle(
                                    fontFamily = customFontFamily,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 20.sp,
                                    color = Color(0xFFF4F4F4),
                                    textAlign = TextAlign.Start,
                                ),
                                modifier = Modifier
                                    .padding(horizontal = 20.dp, vertical = 0.dp),
                            )
                            Text(
                                text = stringResource(id = R.string.create_new_plan_description),
                                style = TextStyle(
                                    fontFamily = customFontFamily,
                                    fontWeight = FontWeight.Normal,
                                    fontSize = 11.sp,
                                    color = Color(0xFFF4F4F4),
                                    textAlign = TextAlign.Start,
                                ),
                                modifier = Modifier
                                    .padding(start = 20.dp, end = 20.dp, bottom = 20.dp),
                            )
                        }
                    }
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight()
                            .weight(4f),
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth(),
                            verticalArrangement = Arrangement.SpaceEvenly,
                        ) {
                            Text(
                                text = stringResource(id = R.string.name_ex),
                                style = TextStyle(
                                    fontFamily = customFontFamily,
                                    fontWeight = FontWeight.Medium,
                                    fontSize = 12.sp,
                                    color = DarkBlue,
                                    textAlign = TextAlign.Start,
                                ),
                                modifier = Modifier
                                    .padding(horizontal = 20.dp, vertical = 5.dp),
                            )
                            Surface(
                                shape = RoundedCornerShape(10.dp),
                                color = Color.White,
                                elevation = 3.dp,
                                modifier = Modifier
                                    .padding(horizontal = 20.dp, vertical = 0.dp)
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
                                                text = stringResource(id = R.string.my_plan),
                                                style = TextStyle(
                                                    fontFamily = customFontFamily,
                                                    fontWeight = FontWeight.Light,
                                                    fontSize = 12.sp,
                                                    color = DarkBlue,
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
                            Text(
                                text = stringResource(id = R.string.description),
                                style = TextStyle(
                                    fontFamily = customFontFamily,
                                    fontWeight = FontWeight.Medium,
                                    fontSize = 12.sp,
                                    color = DarkBlue,
                                    textAlign = TextAlign.Start,
                                ),
                                modifier = Modifier
                                    .padding(horizontal = 20.dp, vertical = 5.dp),
                            )
                            Surface(
                                shape = RoundedCornerShape(10.dp),
                                color = Color.White,
                                elevation = 3.dp,
                                modifier = Modifier
                                    .padding(horizontal = 20.dp, vertical = 0.dp)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth(),
                                    contentAlignment = Alignment.CenterStart,
                                ) {
                                    TextField(
                                        value = input_desc,
                                        onValueChange = { input_desc = it },
                                        placeholder = {
                                            Text(
                                                text = stringResource(id = R.string.description),
                                                style = TextStyle(
                                                    fontFamily = customFontFamily,
                                                    fontWeight = FontWeight.Light,
                                                    fontSize = 12.sp,
                                                    color = DarkBlue,
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
                                            .fillMaxWidth()
                                            .height(80.dp),
                                    )
                                }
                            }
                        }
                    }
                    Spacer(
                        modifier = Modifier
                            .height(1.dp)
                            .background(LightBlue)
                            .fillMaxWidth()
                    )
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .weight(1f),
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 0.dp, vertical = 0.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .fillMaxSize()
                                    .background(Color.White)
                                    .clickable { setShow(false, false) },
                                contentAlignment = Alignment.Center,
                            ) {

                                Text(
                                    text = stringResource(id = R.string.cancel),
                                    style = TextStyle(
                                        fontFamily = customFontFamily,
                                        fontWeight = FontWeight.Medium,
                                        fontSize = 14.sp,
                                        color = Blue,
                                        textAlign = TextAlign.Center,
                                    ),
                                )
                            }
                            Spacer(
                                modifier = Modifier
                                    .width(1.dp)
                                    .background(LightBlue)
                                    .fillMaxHeight()
                            )
                            //add new program
                            //para las imagenes, se puede usar el viewmodel de las imagenes, y se envía la primera unused que se encuentre
                            //al enviarle esa imagen automaticamente se convierte en used

                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .weight(1f)
                                    .background(Color.White)
                                    .clickable {
                                        setShow(false, true)
                                        val firstUnusedImage =
                                            imagesViewModel.getFirstUnusedImage()
                                        Log.d(
                                            "API",
                                            "First unused image: %s".format(firstUnusedImage)
                                        )
                                        Log.d(
                                            "Unused image",
                                            "Is image used: %s".format(
                                                imagesViewModel.isImageUsed(firstUnusedImage)
                                            )
                                        )
                                        if (firstUnusedImage != "") {
                                            imagesViewModel.markImageAsUsed(firstUnusedImage)
                                            Log.d(
                                                "Unused image updated",
                                                "Is image used: %s".format(
                                                    imagesViewModel.isImageUsed(firstUnusedImage)
                                                )
                                            )
                                            val ownTraningProgram = TrainingProgram(
                                                input_desc,
                                                input_name,
                                                "TODO",
                                                image = firstUnusedImage
                                            )
                                            trainingProgramViewModel.saveTrainingProgram(
                                                ownTraningProgram,
                                                onSuccess = {
                                                    Log.d("Save program", "Save program success")
                                                },
                                                onFailure = { exception ->
                                                    Log.e(
                                                        "Save program",
                                                        "Error saving program ${exception.message}"
                                                    )
                                                }
                                            )
                                        }
                                    },
                                contentAlignment = Alignment.Center,
                            ) {
                                Text(
                                    text = stringResource(id = R.string.save),
                                    style = TextStyle(
                                        fontFamily = customFontFamily,
                                        fontWeight = FontWeight.Medium,
                                        fontSize = 14.sp,
                                        color = Blue,
                                        textAlign = TextAlign.Center,
                                    ),
                                )
                            }
                        }

                    }
                }
            }
        }
    }
}

fun imagesAPIRequest(context: Context, imagesViewModel: ImageFromJSONViewModel) {
    val url =
        "https://pixabay.com/api/?key=40831815-1775c738159c511eff449f4bb&q=fitness(musculos|exercise)%20(training|gimnasio~)-(sneakers|yoga)&image_type=photo&pretty=true"
    val requestQueue = VolleySingleton(context).getInstance(context)?.getRequestQueue()
    //hay posibilidad de que se llame más de una vez y no se quiere eso
    Log.d("API", "requestQueue: %s".format(requestQueue != null))
    Log.d("API", "requestQueue: %s".format(!imagesViewModel.alreadyRequested.value))
    if (requestQueue != null && !imagesViewModel.alreadyRequested.value) {
        Log.d("API", "Requesting images in function")
        JsonObjectRequest(Request.Method.GET, url, null, { response ->
            Log.d("API", "Response: %s".format(response.toString()))
            JSONArray(response.getString("hits")).let { jsonArray ->
                val imagesLinks = mutableListOf<String>()
                for (i in 0 until jsonArray.length()) {
                    val image = jsonArray.getJSONObject(i)
                    imagesLinks.add(image.getString("webformatURL"))
                }
                imagesViewModel.alreadyRequested.value = true
                imagesViewModel.setImagesLinks(imagesLinks)
            }
        }, { error ->
            Log.d("API", "Error: %s".format(error.toString()))
        }).also {
            requestQueue.add(it)
        }
    }
}
