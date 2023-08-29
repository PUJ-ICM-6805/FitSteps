package com.example.fitsteps.screens

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.fitsteps.ui.theme.Red
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.draw.alpha
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import com.example.fitsteps.R
import com.example.fitsteps.navigation.BOTTOM_NAVIGATION_ROUTE
import com.example.fitsteps.navigation.HOME_ROUTE
import com.example.fitsteps.navigation.MAIN_SCREEN_ROUTE
import com.example.fitsteps.navigation.Screen
import kotlinx.coroutines.delay

@Composable
fun AnimatedSplashScreen(navController: NavHostController) {
    var startAnimation by remember {
        mutableStateOf(false)
    }
    var alphaAnim = animateFloatAsState(
        targetValue = if(startAnimation) 1f else 0f,
        animationSpec = tween(
            durationMillis = 2000,
        )
    )
    LaunchedEffect(key1 = true) {
        startAnimation = true
        delay(2000)
        navController.navigate(
            MAIN_SCREEN_ROUTE,
            builder = {
                popUpTo(route = Screen.SplashScreen.route) {
                    inclusive = true
                }
            }
        )

    }
    Splash(alpha = alphaAnim.value)
}

@Composable
fun Splash(alpha: Float) {
    Box(
        modifier = Modifier
            .background(Red)
            .fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Icon(
            modifier = Modifier
                .size(120.dp)
                .alpha(alpha),
            painter = painterResource(id = R.drawable.ic_launcher_foreground),
            contentDescription = "Logo",
            tint = Color.White
        )
    }
}

