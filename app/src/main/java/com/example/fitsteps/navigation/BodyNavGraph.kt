package com.example.fitsteps.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.fitsteps.firebaseData.firebaseBodyMeasuresData.MeasuresViewModel
import com.example.fitsteps.screens.body.BodyScreen
import com.example.fitsteps.screens.body.BodyScreen2
import com.example.fitsteps.screens.body.BodyScreen2Edit

fun NavGraphBuilder.bodyNavGraph(
    navController: NavHostController,
    rootNavController: NavHostController,
) {
    val measuresViewModel = MeasuresViewModel()
    navigation(
        startDestination = Screen.BodyScreen.route,
        route = BottomBarScreen.Body.route
    ){
        composable(
            route = Screen.BodyScreen.route
        ) {
            BodyScreen(navController = navController, rootNavController = rootNavController, measuresViewModel = measuresViewModel)
        }
        composable(
            route = Screen.BodyScreen2.route
        ) {
            BodyScreen2(navController = navController, rootNavController = rootNavController, measuresViewModel = measuresViewModel)
        }
        composable(
            route = Screen.BodyScreenEditable.route
        ) {
            BodyScreen2Edit(navController = navController, rootNavController = rootNavController, measuresViewModel = measuresViewModel)
        }
    }
}