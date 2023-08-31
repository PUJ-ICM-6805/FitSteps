package com.example.fitsteps.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.fitsteps.ui.theme.DarkBlue
import com.example.fitsteps.ui.theme.LightBlue
import com.example.fitsteps.ui.theme.White

@Composable
fun BodyScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(White),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Cuerpo",
            color = DarkBlue,
            fontWeight = FontWeight.Bold,
            fontSize = MaterialTheme.typography.bodyLarge.fontSize
        )
    }
}

@Composable
@Preview
fun BodyScreenPreview() {
    BodyScreen()
}