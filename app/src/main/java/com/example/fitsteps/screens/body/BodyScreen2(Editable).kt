package com.example.fitsteps.screens.body

import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.fitsteps.R
import com.example.fitsteps.screens.training.LargeButtons
import com.example.fitsteps.firebaseData.firebaseBodyMeasuresData.MeasuresViewModel
import com.example.fitsteps.screens.HamburgersDropList
import com.example.fitsteps.screens.LargeButtons
import com.example.fitsteps.ui.theme.DarkBlue
import com.example.fitsteps.ui.theme.Red
import com.example.fitsteps.ui.theme.White
import com.example.fitsteps.ui.theme.customFontFamily
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import java.io.File
import java.text.SimpleDateFormat

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun BodyScreen2Edit(
    navController: NavHostController,
    rootNavController: NavHostController,
    measuresViewModel: MeasuresViewModel = MeasuresViewModel()
) {
    val context = LocalContext.current
    val file = context.createImageFile()
    val uri = FileProvider.getUriForFile(
        context,
        "com.example.fitsteps.fileprovider",
        file
    )
    var capturedImageUri by remember { mutableStateOf<Uri>(Uri.EMPTY) }

    val cameraLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.TakePicture()) { success ->
            if (success) {
                capturedImageUri = uri
                measuresViewModel.actualMeasure.value.foto = capturedImageUri.toString()
                Log.d("Camera", "Success")
            } else {
                Log.d("Camera", "Failure")
            }
        }
    val permissionLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                Toast.makeText(context, "Permission Granted", Toast.LENGTH_SHORT).show()
                cameraLauncher.launch(uri)
            } else {
                Toast.makeText(context, "Permission Denied :(", Toast.LENGTH_SHORT).show()
                Log.d("Permission", "Denied")
            }
        }
    val cameraPermissionState = rememberPermissionState(android.Manifest.permission.CAMERA)
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
                modifier = Modifier
                    .fillMaxSize()
                    .offset(y = (-20).dp),

            ){
                LargeButtons(
                    text = stringResource(id = R.string.addPhoto) ,
                    onClick = {
                        val permissionCheckResult = ContextCompat.checkSelfPermission(
                            context,
                            android.Manifest.permission.CAMERA
                        )
                        if (permissionCheckResult == PackageManager.PERMISSION_GRANTED) {
                            cameraLauncher.launch(uri)
                        } else {
                            Log.d("Permission", "Denied")
                            permissionLauncher.launch(android.Manifest.permission.CAMERA)
                        }
                        if (capturedImageUri != Uri.EMPTY) {
                            if (capturedImageUri.path?.isNotEmpty() == true){
                                measuresViewModel.actualMeasure.value.foto = capturedImageUri.toString()
                            }
                        }

                    },
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

private fun Context.createImageFile(): File {
    val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(System.currentTimeMillis())
    val imageName = "JPEG_${timeStamp}_"
    return File.createTempFile(imageName, ".jpg", externalCacheDir)
}

@Composable
@Preview
fun BodyScreen2EditPreview() {
    BodyScreen2Edit(navController = rememberNavController(), rootNavController = rememberNavController())
}
