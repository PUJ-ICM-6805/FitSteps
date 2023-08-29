package com.example.fitsteps.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.fitsteps.screens.RegisterScreen1
import com.example.fitsteps.screens.RegisterScreen2
import com.example.fitsteps.screens.RegisterScreen3
import com.example.fitsteps.screens.RegisterScreen4
import com.example.fitsteps.screens.RegisterScreen5


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
            RegisterScreen2(navController = navController, mainNavController = mainNavController)
        }
        composable(route = Screen.RegisterScreen3.route) {
            RegisterScreen3(navController = navController, mainNavController = mainNavController)
        }
        composable(route = Screen.RegisterScreen4.route) {
            RegisterScreen4(navController = navController, mainNavController = mainNavController)
        }
        composable(route = Screen.RegisterScreen5.route) {
            RegisterScreen5(navController = navController, mainNavController = mainNavController)
        }
    }
}