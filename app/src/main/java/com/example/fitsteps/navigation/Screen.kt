package com.example.fitsteps.navigation

const val ROOT_ROUTE = "root"
const val AUTHENTICATION_ROUTE = "authentication"
const val HOME_ROUTE = "home"
const val BOTTOM_NAVIGATION_ROUTE = "bottom_navigation"
const val REGISTER_ROUTE = "register"
const val REGISTER_NAVIGATION_ROUTE = "register_nav"
const val MAIN_SCREEN_ROUTE = "main_screen"
const val CUSTOM_ROUTINE_ROUTE = "custom_routine"
const val PLAN_ROUTE = "plan_route"

sealed class Screen(val route: String) {
    object SplashScreen : Screen(route = "splash_screen")
    object RegisterScreen1 : Screen(route = "register_screen_1")
    object RegisterScreen2 : Screen(route = "register_screen_2")
    object RegisterScreen3 : Screen(route = "register_screen_3")
    object RegisterScreen4 : Screen(route = "register_screen_4")
    object RegisterScreen5 : Screen(route = "register_screen_5")
    object CustomRoutineMain: Screen(route = "custom_routine_main")
    object CustomRoutineScreen1: Screen(route = "custom_routine_screen_1")
    object CustomRoutineScreen2: Screen(route = "custom_routine_screen_2")
    object CustomRoutineScreen3: Screen(route = "custom_routine_screen_3")
    object CustomRoutineScreen4: Screen(route = "custom_routine_screen_4")
    object CustomRoutineScreen5: Screen(route = "custom_routine_screen_5")
    object CustomRoutineScreen6: Screen(route = "custom_routine_screen_6")
    object BodyScreen: Screen(route = "body_screen")
    object BodyScreen2: Screen(route = "body_screen_2")
    object BodyScreenEditable: Screen(route = "body_screen_editable")
    object ProfileScreen: Screen(route = "profile_screen")
    object PlanScreen: Screen(route = "plan_screen")
    object RoutineScreen: Screen(route = "routine_screen")
    object AddExerciseScreen: Screen(route = "add_exercise_screen")
    object AddSpecificExerciseScreen: Screen(route = "add_specific_exercise_screen")
    object RoutineExecutionScreen: Screen(route = "routine_execution_screen")
    object RunningMapScreen: Screen(route = "running_map_screen")
    object RunningRouteDetails: Screen(route = "running_route_screen_1")
    object RunningRouteDetails2: Screen(route = "running_route_screen_2")
}