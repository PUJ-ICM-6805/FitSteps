package com.example.fitsteps.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material3.CardElevation
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.fitsteps.ui.theme.DarkBlue
import com.example.fitsteps.ui.theme.FitStepsTheme
import com.example.fitsteps.ui.theme.LightBlue
import com.example.fitsteps.ui.theme.White

@Composable
fun RunningMap() {
    FitStepsTheme {
        Column(
            verticalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.fillMaxSize()
        ) {
            ExpandableCardDemo()
            // Otros elementos de tu pantalla
        }
    }
}

@Composable
fun ExpandableCardDemo() {
    var expanded by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(if (expanded) 600.dp else 200.dp)
                .padding(16.dp)
                .clickable { expanded = !expanded },
            elevation = 5.dp
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                Text(
                    text = "Título de la tarjeta",
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Contenido de la tarjeta...",
                    fontSize = 16.sp
                )
            }
        }

        if (expanded) {
            // Agregar contenido adicional cuando la tarjeta está expandida
            // Por ejemplo, aquí puedes mostrar más detalles de la tarjeta
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)
                    .padding(16.dp)
                    .align(Alignment.BottomCenter)
            ) {
                Text(
                    text = "Contenido adicional de la tarjeta...",
                    fontSize = 16.sp
                )
            }
        }
    }
}

@Composable
@Preview
fun RunningMapPreview() {
    RunningMap()
}