package com.example.fitsteps.navigation

import com.example.fitsteps.R

sealed class BottomBarScreen(
    val route: String,
    val title: String,
    val icon: Int,
    val screens: List<String> = listOf(route),
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
        icon = R.drawable.ic_running,
        screens = listOf(
            "running",
            Screen.RunningMapScreen.route,
            Screen.RunningRouteDetails.route,
            Screen.RunningRouteDetails2.route,
        )
    )

    object Exercise : BottomBarScreen(
        route = "exercise",
        title = "Entreno",
        icon = R.drawable.ic_exercise,
        screens = listOf(
            "exercise",
            Screen.CustomRoutineMain.route,
            Screen.CustomRoutineScreen1.route,
            Screen.CustomRoutineScreen2.route,
            Screen.CustomRoutineScreen3.route,
            Screen.CustomRoutineScreen4.route,
            Screen.CustomRoutineScreen5.route,
            Screen.CustomRoutineScreen6.route,
            Screen.PlanScreen.route,
            Screen.RoutineScreen.route,
            Screen.AddExerciseScreen.route,
            Screen.AddSpecificExerciseScreen.route,
        )
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
