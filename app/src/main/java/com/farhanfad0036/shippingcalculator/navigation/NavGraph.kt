package com.farhanfad0036.shippingcalculator.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.farhanfad0036.shippingcalculator.HomeScreen
import com.farhanfad0036.shippingcalculator.InputScreen

@Composable
fun SetupNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        composable(Screen.Home.route) {
            HomeScreen(navController)
        }
        composable(Screen.Input.route) {
            InputScreen(navController)
        }
    }
}