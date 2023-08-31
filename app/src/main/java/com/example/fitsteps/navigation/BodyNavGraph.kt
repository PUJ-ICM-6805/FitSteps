package com.example.fitsteps.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.fitsteps.screens.BodyScreen
import com.example.fitsteps.screens.BodyScreen2

fun NavGraphBuilder.bodyNavGraph(
    navController: NavHostController
) {
    navigation(
        startDestination = Screen.BodyScreen.route,
        route = BottomBarScreen.Body.route
    ){
        composable(
            route = Screen.BodyScreen.route
        ) {
            BodyScreen(navController = navController)
        }
        composable(
            route = Screen.BodyScreen2.route
        ) {
            BodyScreen2(navController = navController)
        }
    }
}