package com.training.programmingtest.ui.widgets

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.training.programmingtest.R
import com.training.programmingtest.utils.formatDateTime
import com.training.programmingtest.utils.formatDecimals
import data.model.WeatherApiResponse


@Composable
fun SunsetSunRiseRow(data: WeatherApiResponse) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 15.dp, bottom = 6.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row {
            Image(
                painter = painterResource(id = R.drawable.sunrise),
                contentDescription = "sunrise",
                modifier = Modifier.size(30.dp)
            )
            Text(
                text = formatDateTime(data.sys.sunrise),
                style = MaterialTheme.typography.headlineMedium
            )

        }

        Row {

            Text(
                text = formatDateTime(data.sys.sunset),
                style = MaterialTheme.typography.headlineMedium
            )
            Image(
                painter = painterResource(id = R.drawable.sunset),
                contentDescription = "sunset",
                modifier = Modifier.size(30.dp)
            )


        }

    }


}

@Composable
fun HumidityWindPressureRow(
    weather: WeatherApiResponse
) {
    Row(
        modifier = Modifier
            .padding(12.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        Text(
            text = "${weather.main.humidity}%",
            style = MaterialTheme.typography.headlineMedium
        )




        Text(
            text = "${weather.main.pressure} psi",
            style = MaterialTheme.typography.headlineMedium
        )



        Text(
            text = "${formatDecimals(weather.wind.speed)} " + "mph",
            style = MaterialTheme.typography.headlineMedium
        )


    }

}

@Composable
fun WeatherStateImage(imageUrl: String) {
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(imageUrl)
            .crossfade(true)
            .build(),
        contentDescription = "icon image",
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .size(80.dp)
            .clip(CircleShape)
    )
}
