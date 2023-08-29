package com.example.fitsteps.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.fitsteps.R

@Composable
fun RegisterScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ){
        Box(
            modifier = Modifier
                .weight(1f),
            contentAlignment = Alignment.BottomStart
        ) {
            Image(
                painter = painterResource(id = R.drawable.pesasregistro),
                contentDescription = "",
                modifier = Modifier
                    .fillMaxSize(),
                contentScale = ContentScale.Crop
            )
            Text(
                text = stringResource(id = R.string.register_process),
                color = Color(0xFFF0F0F0),
                fontSize = 36.sp,
                fontWeight = FontWeight.SemiBold,
                fontStyle = FontStyle.Italic,
                modifier = Modifier
                    .padding(start = 20.dp, bottom = 10.dp)
            )

        }
        Box(
            modifier = Modifier
                .weight(2f),
        ) {

        }
    }
}

@Composable
@Preview
fun RegisterScreenPreview() {
    RegisterScreen()
}