package com.example.cyberai.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.cyberai.ui.screens.DashboardScreen
import com.example.cyberai.ui.screens.HomeScreen
import com.example.cyberai.ui.screens.UrlScannerScreen

object Routes {
    const val Dashboard = "dashboard"
    const val Home = "home"
    const val UrlScanner = "url_scanner"
}

@Composable
fun AppNavigation(navController: NavHostController = rememberNavController()) {
    NavHost(
        navController = navController,
        startDestination = Routes.Dashboard
    ) {
        composable(Routes.Dashboard) { DashboardScreen() }
        composable(Routes.Home) { HomeScreen(navController) }
        composable(Routes.UrlScanner) { UrlScannerScreen(navController) }
    }
}
