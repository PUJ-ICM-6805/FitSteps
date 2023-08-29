package com.example.fitsteps

import android.graphics.drawable.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.core.graphics.drawable.toDrawable

sealed class BottomBarScreen(
    val route: String,
    val title: String,
    val icon: Int
) {
    //object SplashScreen : Screen
    object Summary : BottomBarScreen(
        route = "summary",
        title = "Resumen",
        icon = R.drawable.ic_summary
    )

    object Running : BottomBarScreen(
        route = "running",
        title = "Running",
        icon = R.drawable.ic_running
    )

    object Exercise : BottomBarScreen(
        route = "exercise",
        title = "Ejercicio",
        icon = R.drawable.ic_exercise
    )
    object Body : BottomBarScreen(
        route = "body",
        title = "Cuerpo",
        icon = R.drawable.ic_body
    )
    object Social : BottomBarScreen(
        route = "social",
        title = "Social",
        icon = R.drawable.ic_social
    )
}
