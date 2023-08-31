package com.example.fitsteps.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.fitsteps.screens.AnimatedSplashScreen
import com.example.fitsteps.screens.BottomBarScreen
import com.example.fitsteps.screens.LoginScreen
import com.example.fitsteps.screens.MainScreen
import com.example.fitsteps.screens.RegisterScreen

@Composable
fun SetupNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.SplashScreen.route,
        route = ROOT_ROUTE,
    ) {
        composable(
            route = Screen.SplashScreen.route
        ) {
            AnimatedSplashScreen(navController = navController)
        }
        composable(
            route = MAIN_SCREEN_ROUTE
        ) {
            MainScreen(navController = navController)
        }
        composable(
            route = AUTHENTICATION_ROUTE
        ) {
            LoginScreen(navController = navController)
        }
        composable(
            route = BOTTOM_NAVIGATION_ROUTE
        ) {
            BottomBarScreen(rootNavController = navController)
        }
        composable(
            route = REGISTER_NAVIGATION_ROUTE
        ) {
            RegisterScreen(mainNavController = navController)
        }
    }
}