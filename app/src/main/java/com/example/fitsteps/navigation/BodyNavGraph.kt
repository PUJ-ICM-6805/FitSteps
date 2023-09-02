package com.example.fitsteps.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.fitsteps.screens.BodyScreen
import com.example.fitsteps.screens.BodyScreen2
import com.example.fitsteps.screens.BodyScreen2Edit

fun NavGraphBuilder.bodyNavGraph(
    navController: NavHostController,
    rootNavController: NavHostController,
) {
    navigation(
        startDestination = Screen.BodyScreen.route,
        route = BottomBarScreen.Body.route
    ){
        composable(
            route = Screen.BodyScreen.route
        ) {
            BodyScreen(navController = navController, rootNavController = rootNavController)
        }
        composable(
            route = Screen.BodyScreen2.route
        ) {
            BodyScreen2(navController = navController, rootNavController = rootNavController)
        }
        composable(
            route = Screen.BodyScreenEditable.route
        ) {
            BodyScreen2Edit(navController = navController, rootNavController = rootNavController)
        }
    }
}