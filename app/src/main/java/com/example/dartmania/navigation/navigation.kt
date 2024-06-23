package com.example.dartmania.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.dartmania.screens.HomeScreen
import com.example.dartmania.screens.PlayerScreen
import com.example.dartmania.viewmodels.PlayerViewModel

sealed class Screen(val route: String) {

    object Home : Screen("homescreen")

}

@Composable
fun Navigation(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Screen.Home.route) {
        composable(Screen.Home.route) {
            PlayerScreen(viewModel = PlayerViewModel(), navController)
        }
        composable(Screen.Home.route) {
            PlayerScreen(viewModel = PlayerViewModel(), navController)

        }
        composable(Screen.Home.route){
            PlayerScreen(viewModel = PlayerViewModel(), navController)

        }
    }
}