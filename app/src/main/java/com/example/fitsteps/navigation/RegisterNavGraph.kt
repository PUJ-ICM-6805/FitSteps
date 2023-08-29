package com.example.fitsteps.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.fitsteps.screens.RegisterScreen1


@Composable
fun RegisterNavGraph(
    navController: NavHostController,
    mainNavController: NavHostController,
){
    NavHost(
        navController = navController,
        route = REGISTER_ROUTE,
        startDestination = Screen.RegisterScreen1.route
    ) {
        composable(route = Screen.RegisterScreen1.route) {
            RegisterScreen1(navController = navController, mainNavController = mainNavController)
        }
        composable(route = Screen.RegisterScreen2.route) {
            RegisterScreen1(navController = navController, mainNavController = mainNavController)
        }
        composable(route = Screen.RegisterScreen3.route) {
            RegisterScreen1(navController = navController, mainNavController = mainNavController)
        }
        composable(route = Screen.RegisterScreen4.route) {
            RegisterScreen1(navController = navController, mainNavController = mainNavController)
        }
        composable(route = Screen.RegisterScreen5.route) {
            RegisterScreen1(navController = navController, mainNavController = mainNavController)
        }
    }
}