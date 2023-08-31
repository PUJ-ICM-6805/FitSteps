package com.example.fitsteps.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.compose.material.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.isUnspecified
import androidx.compose.ui.unit.sp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.rememberNavController
import com.example.fitsteps.navigation.BottomBarNavGraph
import com.example.fitsteps.navigation.BottomBarScreen
import com.example.fitsteps.navigation.HOME_ROUTE
import com.example.fitsteps.navigation.SetupNavGraph
import com.example.fitsteps.screens.*
import com.example.fitsteps.ui.theme.Blue
import com.example.fitsteps.ui.theme.LightBlue
import com.example.fitsteps.ui.theme.customFontFamily

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun BottomBarScreen(navController: NavHostController = rememberNavController()) {
    Scaffold(
        modifier = Modifier
            .background(MaterialTheme.colors.surface),
        bottomBar = { BottomBar(navController = navController) }
    ) {
        BottomBarNavGraph(navController = navController)
    }
}

@Composable
fun BottomBar(navController: NavHostController) {
    val screens = listOf(
        BottomBarScreen.Summary,
        BottomBarScreen.Running,
        BottomBarScreen.Exercise,
        BottomBarScreen.Body,
        BottomBarScreen.Social,
    )
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    BottomNavigation(
        backgroundColor = MaterialTheme.colors.surface,
        modifier = Modifier
            .fillMaxWidth()
            .height(70.dp)
            .clip(shape = RoundedCornerShape(30.dp, 30.dp, 0.dp, 0.dp)),
    ) {
        screens.forEach { screen ->
            AddItem(
                screen = screen,
                currentDestination = currentDestination,
                navController = navController
            )
        }
    }
}

@Composable
fun RowScope.AddItem(
    screen: BottomBarScreen,
    currentDestination: NavDestination?,
    navController: NavHostController,
    style: TextStyle = TextStyle(
        fontSize = 20.sp,
        fontWeight = FontWeight.Medium,
        fontFamily = customFontFamily,
    )
) {
    val selected = when {
        currentDestination?.hierarchy?.any {
            it.route == screen.route
        } == true -> Blue
        else -> LightBlue
    }
    BottomNavigationItem(
        selected = currentDestination?.hierarchy?.any {
            it.route == screen.route
        } == true,
        onClick = {
            navController.navigate(screen.route) {
                popUpTo(navController.graph.findStartDestination().id)
                launchSingleTop = true
            }
        },
        icon = {
            Icon(
                painter = painterResource(id = screen.icon),
                contentDescription = "Navigation Icon",
                tint = selected,
            )
        },
        label = {
            Text(
                text = screen.title,
                fontWeight = FontWeight.Medium,
                color = Blue,
                softWrap = false,
                style = TextStyle(
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Medium,
                    fontFamily = customFontFamily,
                    fontStyle = FontStyle.Normal,
                ),
            )
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(0.dp, 10.dp)
    )
}

@Composable
@Preview
fun PreviewBottomBar() {
    BottomBarScreen()
}





