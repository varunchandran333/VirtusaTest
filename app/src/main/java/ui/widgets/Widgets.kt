package com.training.programmingtest.ui.widgets

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.training.programmingtest.utils.formatDate
import com.training.programmingtest.utils.formatDateTime
import com.training.programmingtest.utils.formatDecimals
import data.model.Weather
import data.model.WeatherApiResponse


@Composable
fun SunsetSunRiseRow(weather: WeatherApiResponse) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 15.dp, bottom = 6.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
//        Row {
//            Image(painter = painterResource(id = R.drawable.sunrise),
//                contentDescription = "sunrise",
//                modifier = Modifier.size(30.dp))
//            Text(text = formatDateTime(weather.sys.sunrise),
//                style = MaterialTheme.typography.headlineMedium)
//
//        }

        Row {

            Text(
                text = formatDateTime(weather.sys.sunset),
                style = MaterialTheme.typography.headlineMedium
            )
//            Image(painter = painterResource(id = R.drawable.sunset),
//                contentDescription = "sunset",
//                modifier = Modifier.size(30.dp))


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
        Row(modifier = Modifier.padding(4.dp)) {
//            Icon(painter = painterResource(id = R.drawable.humidity),
//                contentDescription = "humidity icon",
//                modifier = Modifier.size(20.dp))
            Text(
                text = "${weather.main.humidity}%",
                style = MaterialTheme.typography.headlineMedium
            )

        }

        Row() {
//            Icon(painter = painterResource(id = R.drawable.pressure),
//                contentDescription = "pressure icon",
//                modifier = Modifier.size(20.dp))
            Text(
                text = "${weather.main.pressure} psi",
                style = MaterialTheme.typography.headlineMedium
            )

        }

        Row() {
//            Icon(painter = painterResource(id = R.drawable.wind),
//                contentDescription = "wind icon",
//                modifier = Modifier.size(20.dp))
            Text(
                text = "${formatDecimals(weather.wind.speed)} " + "mph",
                style = MaterialTheme.typography.headlineMedium
            )

        }

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
