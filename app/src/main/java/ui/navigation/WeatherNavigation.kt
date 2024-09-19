package com.training.programmingtest.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.training.programmingtest.ui.screens.DashBoardScreen
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
        val route = WeatherScreens.DashBoardScreen.name
        composable(
            "$route/{city}",
            arguments = listOf(
                navArgument(name = "city") {
                    type = NavType.StringType
                })
        ) { navBack ->
            navBack.arguments?.getString("city").let { city ->

                val viewModel = koinViewModel<WeatherViewModel>()
                DashBoardScreen(
                    navController = navController, viewModel,
                    city = city!!
                )
            }


        }
        composable(WeatherScreens.SearchScreen.name) {
            SearchScreen(navController)
        }
    }
}