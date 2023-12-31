package com.example.fitsteps.screens

import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddAPhoto
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import com.example.fitsteps.R
import com.example.fitsteps.authentication.DatabaseUtils
import com.example.fitsteps.authentication.User
import com.example.fitsteps.ui.theme.Blue
import com.example.fitsteps.ui.theme.DarkBlue
import com.example.fitsteps.ui.theme.LightBlue
import com.example.fitsteps.ui.theme.customFontFamily
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


@Composable
fun ProfileScreen(navController: NavHostController, rootNavController: NavHostController) {
    val userid = Firebase.auth.currentUser?.uid
    val userEmail = Firebase.auth.currentUser?.email
    val usuario = remember { mutableStateOf(User()) } //obligatorio

    var selectedImageUri by remember { mutableStateOf<Uri>(Uri.EMPTY) }
    val photoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = {
            if (it != null) {
                selectedImageUri = it
                Log.d("ProfileScreen", "Selected image URI: $selectedImageUri")
            }
        })

    LaunchedEffect(userid) {
        userid?.let { uid ->
            val userData = DatabaseUtils().getUserDataByUID(uid)
            if (userData != null) {
                usuario.value = userData
            }
        }
    }
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
                if (selectedImageUri != Uri.EMPTY) {
                    // Actualiza la imagen en el estado de Compose
                    usuario.value = usuario.value.copy(avatar = selectedImageUri.toString())

                    // Llama a la función para subir la nueva imagen a Firebase
                    DatabaseUtils().updateUserAvatar(selectedImageUri, userid!!, usuario)
                }
                Box(
                    modifier = Modifier
                        .size(150.dp),
                    contentAlignment = Alignment.BottomEnd,
                ) {
                    val message = remember {
                        mutableStateOf("")
                    }
                    Image(
                        painter = userProfileAvatar(usuario.value.avatar),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(150.dp)
                            .clip(CircleShape)
                    )

                    SmallFloatingActionButton(
                        onClick = {
                            photoPickerLauncher.launch(
                                PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                            )
                        },
                        shape = CircleShape,
                        contentColor = Blue,
                        containerColor = LightBlue
                    )
                    {
                        Icon(
                            imageVector = Icons.Default.AddAPhoto,
                            contentDescription = null,
                            tint = Blue
                        )
                    }
                    if (selectedImageUri != Uri.EMPTY) {
                        DatabaseUtils().updateUserAvatar(selectedImageUri, userid!!, usuario)
                    }
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
                    text = stringResource(id = R.string.name_ex),
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
                        text = usuario.value.user_name,
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
                    if (userEmail != null) {
                        Text(
                            text = userEmail,
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
                        text = usuario.value.user_birth_date,
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
                        text = usuario.value.gender,
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
                        text = usuario.value.weight.toString(),
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
                        text = usuario.value.height.toString(),
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
                        text = usuario.value.experience,
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
    //}
}


@Composable
fun userProfileAvatar(avatar: String): Painter {
    val defaultImage =
        "https://firebasestorage.googleapis.com/v0/b/fitsteps-eb0ef.appspot.com/o/images%2Fdefault.jpg?alt=media&token=bdbbac1d-2340-4bc9-81ea-52af9878318d&_gl=1*hzwwna*_ga*MTIyNDA3MTA2NS4xNjk2MzkxMDc3*_ga_CW55HF8NVT*MTY5NzgwNTM2Mi44LjEuMTY5NzgwNTM4Ny4zNS4wLjA."
    if (avatar.isBlank() || avatar.isEmpty() || avatar == "null") {
        return rememberAsyncImagePainter(defaultImage)
    }
    return rememberAsyncImagePainter(avatar)
}

@Composable
@Preview
fun ProfileScreenPreview() {
    ProfileScreen(
        navController = rememberNavController(),
        rootNavController = rememberNavController()
    )
}