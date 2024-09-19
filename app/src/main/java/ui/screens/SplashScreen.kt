package com.training.programmingtest.ui.screens

import android.view.animation.OvershootInterpolator
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.navOptions
import com.training.programmingtest.ui.navigation.WeatherScreens
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavController) {
    val defaultCity = "San Diego"
    val scale = remember {
        Animatable(0f)
    }
    LaunchedEffect(key1 = true, block = {
        scale.animateTo(
            targetValue = 1f,
            animationSpec = tween(
                durationMillis = 800,
                easing = {
                    OvershootInterpolator(8f).getInterpolation(it)
                })
        )
        delay(2000L)

        navController.navigate(WeatherScreens.DashBoardScreen.name + "/$defaultCity",
            navOptions = navOptions {
                launchSingleTop = true
            })
    })
    Surface(
        modifier = Modifier
            .padding(15.dp)
            .size(300.dp)
            .scale(scale.value),
        border = BorderStroke(
            2.dp,
            color = MaterialTheme.colorScheme.background
        ),
        shape = CircleShape
    ) {
        Column(
            modifier = Modifier.padding(bottom = 12.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
//            Image(
//                modifier = Modifier.size(95.dp),
//                painter = painterResource(id = R.drawable.weather),
//                contentDescription = "Weather Icon",
//                contentScale = ContentScale.Fit
//            )
            Text(
                text = "Weather Today",
                fontStyle = FontStyle.Italic,
                fontFamily = FontFamily.Monospace,
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.tertiary
            )

        }
    }
}