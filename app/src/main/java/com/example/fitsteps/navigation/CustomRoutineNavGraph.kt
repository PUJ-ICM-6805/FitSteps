package com.example.fitsteps.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.fitsteps.R
import com.example.fitsteps.screens.AutoRoutineMainScreen
import com.example.fitsteps.screens.CustomRoutineScreen
import com.example.fitsteps.screens.CustomRoutineScreenSteps

fun NavGraphBuilder.customRoutineNavGraph(
    navController: NavHostController
){
    navigation(
        startDestination = Screen.CustomRoutineMain.route,
        route = CUSTOM_ROUTINE_ROUTE
    ){
        composable(
            route = Screen.CustomRoutineMain.route
        ) {
            AutoRoutineMainScreen(navController = navController)
        }
        composable(
            route = Screen.CustomRoutineScreen1.route
        ) {
            CustomRoutineScreen(navController = navController)
        }
        composable(
            route = Screen.CustomRoutineScreen2.route
        ) {
            CustomRoutineScreenSteps(
                navController = navController,
                progress = 0.2f,
                items = stringArrayResource(id = R.array.muscles_groups),
                onClick = {navController.navigate(Screen.CustomRoutineScreen3.route)},
                mainText = stringResource(id = R.string.what_muscle_group),
                multipleItems = true,
            )
        }
        composable(
            route = Screen.CustomRoutineScreen3.route
        ) {
            CustomRoutineScreenSteps(
                navController = navController,
                progress = 0.4f,
                items = stringArrayResource(id = R.array.training_sites),
                onClick = {navController.navigate(Screen.CustomRoutineScreen4.route)},
                mainText = stringResource(id = R.string.where_to_train)
            )
        }
        composable(
            route = Screen.CustomRoutineScreen4.route
        ) {
            CustomRoutineScreenSteps(
                navController = navController,
                progress = 0.6f,
                items = stringArrayResource(id = R.array.yes_no),
                onClick = {navController.navigate(Screen.CustomRoutineScreen5.route)},
                mainText = stringResource(id = R.string.yes_no_cardio)
            )
        }
        composable(
            route = Screen.CustomRoutineScreen5.route
        ) {
            CustomRoutineScreenSteps(
                navController = navController,
                progress = 0.8f,
                items = stringArrayResource(id = R.array.training_days),
                onClick = {navController.navigate(Screen.CustomRoutineScreen6.route)},
                mainText = stringResource(id = R.string.what_days),
                multipleItems = true,
            )
        }
        composable(
            route = Screen.CustomRoutineScreen6.route
        ) {
            CustomRoutineScreenSteps(
                navController = navController,
                progress = 1.0f,
                items = stringArrayResource(id = R.array.preferences),
                onClick = {},
                mainText = stringResource(id = R.string.preferences_profiles),
                nextOptionText = stringResource(id = R.string.create),
                multipleItems = true,
                showCompletedFrame = true,
                onClose = {navController.navigate(
                    BottomBarScreen.Exercise.route,
                    builder = {
                        popUpTo(route = Screen.CustomRoutineScreen6.route) {
                            inclusive = true
                        }
                    }
                )}
            )
        }
    }
}