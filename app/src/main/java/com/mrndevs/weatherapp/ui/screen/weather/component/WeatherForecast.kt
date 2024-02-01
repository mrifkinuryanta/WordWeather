package com.mrndevs.weatherapp.ui.screen.weather.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.mrndevs.weatherapp.R
import com.mrndevs.weatherapp.ui.theme.SP18
import com.mrndevs.weatherapp.ui.theme.SP20
import com.mrndevs.weatherapp.ui.theme.W600
import com.mrndevs.weatherapp.ui.theme.W700
import com.mrndevs.weatherapp.ui.theme.grey
import com.mrndevs.weatherapp.ui.theme.onBackgroundLight

@Composable
fun WeatherForecast() {
    Card(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = onBackgroundLight)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp)
        ) {
            Row {
                Text(
                    text = "Next Forecast",
                    style = SP20.W600,
                    color = Color.White
                )
                Spacer(modifier = Modifier.weight(1f))
                WeatherIcon(painterRes = R.drawable.ic_calendar_event_line_24)
            }
            Spacer(modifier = Modifier.size(12.dp))
            repeat(3) {
                ItemForecast()
            }
        }
    }
}

@Composable
private fun ItemForecast() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = "Monday", style = SP18.W700, color = Color.White)
        WeatherImage(image = "")
        Row {
            Text(text = "13°C", style = SP18, color = Color.White)
            Spacer(modifier = Modifier.size(10.dp))
            Text(
                text = "10°C",
                style = SP18,
                color = grey
            )
        }
    }
}