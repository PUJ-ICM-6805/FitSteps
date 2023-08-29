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
import com.example.fitsteps.ui.theme.DarkBlue
import com.example.fitsteps.ui.theme.White

@Composable
fun ExerciseScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(White),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Ejercicio",
            color = DarkBlue,
            fontWeight = FontWeight.Bold,
            fontSize = MaterialTheme.typography.bodyLarge.fontSize
        )
    }
}

@Composable
@Preview
fun ExerciseScreenPreview() {
    ExerciseScreen()
}