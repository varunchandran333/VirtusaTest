package com.training.programmingtest.ui.screens

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.training.programmingtest.data.wrapper.NetworkResult
import com.training.programmingtest.ui.navigation.WeatherScreens
import com.training.programmingtest.ui.widgets.HumidityWindPressureRow
import com.training.programmingtest.ui.widgets.SunsetSunRiseRow
import com.training.programmingtest.ui.widgets.WeatherAppBar
import com.training.programmingtest.ui.widgets.WeatherStateImage
import com.training.programmingtest.utils.formatDate
import com.training.programmingtest.utils.formatDecimals
import com.training.programmingtest.viewmodel.weather.WeatherViewModel
import data.model.WeatherApiResponse
import org.koin.androidx.compose.koinViewModel
import org.koin.core.logger.KOIN_TAG

@Composable
fun DashBoardScreen(
    navController: NavController,
    weatherViewModel: WeatherViewModel = koinViewModel<WeatherViewModel>(),
    city: String
) {
    ShowData(weatherViewModel, navController, city)
}

@Composable
fun ShowData(
    weatherViewModel: WeatherViewModel,
    navController: NavController,
    city: String
) {
    weatherViewModel.updateCity(city)
    val weatherData = weatherViewModel.weatherData.collectAsState().value
    Render(uiState = weatherData, navController = navController)
}

@Composable
private fun Render(
    navController: NavController,
    uiState: NetworkResult<WeatherApiResponse>
) {
    when (uiState) {
        is NetworkResult.Loading -> {
            Log.d(KOIN_TAG, "Loading  -->$uiState")
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CircularProgressIndicator()
            }
        }

        is NetworkResult.Success -> {
            MainScaffold(uiState.data, navController)
            Log.d(KOIN_TAG, "Success  -->" + uiState.data)
        }

        is NetworkResult.Error -> {
            Log.d(KOIN_TAG, "Error  -->" + uiState.message.toString())
        }

        is NetworkResult.Exception -> {
            Log.d(KOIN_TAG, "Exception  -->" + uiState.e.message.toString())

        }
    }
}

@Composable
fun MainScaffold(
    weather: WeatherApiResponse, navController: NavController
) {

    Scaffold(topBar = {
        WeatherAppBar(
            title = weather.name + " ,${weather.sys.country}",
            navController = navController,
            onAddButtonClicked = {
                navController.navigate(WeatherScreens.SearchScreen.name)


            },
            elevation = 5.dp
        ) {
            Log.d("TAG", "MainScaffold: Button Clicked")
        }

    }) { padding ->
        MainContent(modifier = Modifier.padding(padding), data = weather)
    }
}

@Composable
fun MainContent(modifier: Modifier = Modifier, data: WeatherApiResponse) {
    val imageUrl = "https://openweathermap.org/img/wn/${data.weather[0].icon}.png"

    Column(
        modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = formatDate(data.dt),
            style = MaterialTheme.typography.headlineMedium,
            color = Color.Black,
            fontWeight = FontWeight.SemiBold,
            modifier = modifier.padding(6.dp)
        )

        Surface(
            modifier = Modifier
                .padding(4.dp)
                .size(200.dp),
            shape = CircleShape,
            color = MaterialTheme.colorScheme.inversePrimary
        ) {

            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                WeatherStateImage(imageUrl = imageUrl)
                Text(
                    text = formatDecimals(data.main.temp) + "º",
                    style = MaterialTheme.typography.displayMedium,
                    fontWeight = FontWeight.ExtraBold
                )
                Text(
                    text = data.weather[0].main,
                    fontStyle = FontStyle.Italic
                )
            }
        }
        HumidityWindPressureRow(weather = data)
        HorizontalDivider()
        SunsetSunRiseRow(weather = data)

    }

}
