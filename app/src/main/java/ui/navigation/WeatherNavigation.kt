package com.training.programmingtest.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.training.programmingtest.ui.screens.DashBoardScreen
import com.training.programmingtest.ui.screens.LocationScreen
import com.training.programmingtest.ui.screens.SearchScreen
import com.training.programmingtest.ui.screens.SplashScreen
import com.training.programmingtest.viewmodel.weather.WeatherViewModel
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun WeatherNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = WeatherScreens.SplashScreen.name) {
        composable(WeatherScreens.SplashScreen.name) {
            SplashScreen(navController)
        }
        composable(WeatherScreens.LocationScreen.name) {
            LocationScreen(navController)
        }
        val route = WeatherScreens.DashBoardScreen.name
        composable(
            "$route/{city}/{lat}/{lon}",
            arguments = listOf(
                navArgument(name = "city") {
                    type = NavType.StringType
                },
                navArgument(name = "lat") {
                    defaultValue = 0.0
                    type = NavType.FloatType
                },
                navArgument(name = "lon") {
                    defaultValue = 0.0
                    type = NavType.FloatType
                }
            )
        ) { navBack ->
            val city = navBack.arguments?.getString("city")
            val lat = navBack.arguments?.getFloat("lat")
            val lon = navBack.arguments?.getFloat("lon")
            val viewModel = koinViewModel<WeatherViewModel>()
            if (lat != null && lon != null) {
                DashBoardScreen(
                    navController = navController, viewModel,
                    city = city!!, lat.toDouble(), lon.toDouble()
                )
            }


        }
        composable(WeatherScreens.SearchScreen.name) {
            SearchScreen(navController)
        }
    }
}