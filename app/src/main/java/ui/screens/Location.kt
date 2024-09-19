package com.training.programmingtest.ui.screens


import android.Manifest
import android.content.Context
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import com.google.android.gms.location.LocationServices
import com.training.programmingtest.ui.navigation.WeatherScreens

@Composable
fun LocationScreen(
    navController: NavController
) {
    val defaultCity = "San Diego"
    val context = LocalContext.current
    val location by remember { mutableStateOf("Your location") }
    val locationPermissionsAlreadyGranted = ContextCompat.checkSelfPermission(
        context,
        Manifest.permission.ACCESS_FINE_LOCATION
    ) == PackageManager.PERMISSION_GRANTED
    if (locationPermissionsAlreadyGranted) {
        getCurrentLocation(context) { lat, long ->
            navController.navigate(WeatherScreens.DashBoardScreen.name + "/$defaultCity/${lat.toFloat()}/${long.toFloat()}")
        }
    }
    val requestPermissionLauncher =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.RequestPermission(),
            onResult = { isGranted: Boolean ->
                if (isGranted) {
                    // Permission granted, update the location
                    getCurrentLocation(context) { lat, long ->
                        navController.navigate(WeatherScreens.DashBoardScreen.name + "/$defaultCity/${lat.toFloat()}/${long.toFloat()}")
                    }
                }
            })
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            onClick = {
                if (hasLocationPermission(context)) {
                    // Permission already granted, update the location
                    getCurrentLocation(context) { lat, long ->
                        navController.navigate(WeatherScreens.DashBoardScreen.name + "/$defaultCity/${lat.toFloat()}/${long.toFloat()}")
                    }
                } else {
                    // Request location permission
                    requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
                }
            }
        ) {
            Text(text = "Allow")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = location)

    }
}

private fun hasLocationPermission(context: Context): Boolean {
    return ContextCompat.checkSelfPermission(
        context,
        Manifest.permission.ACCESS_FINE_LOCATION
    ) == PackageManager.PERMISSION_GRANTED
}

private fun getCurrentLocation(context: Context, callback: (Double, Double) -> Unit) {
    val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
    if (ActivityCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) != PackageManager.PERMISSION_GRANTED
    ) {
        return
    }
    fusedLocationClient.lastLocation
        .addOnSuccessListener { location ->
            if (location != null) {
                val lat = location.latitude
                val long = location.longitude
                callback(lat, long)
            }
        }
        .addOnFailureListener {
            callback(0.0, 0.0)
        }
}