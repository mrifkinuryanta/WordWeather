package com.mrndevs.weatherapp.ui.screen.weather.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.unit.dp
import com.mrndevs.weatherapp.data.source.local.model.EmptyStatusEnum
import com.mrndevs.weatherapp.data.source.local.model.WeatherSearchEntity
import com.mrndevs.weatherapp.ui.component.BottomSheet
import com.mrndevs.weatherapp.ui.component.CardItem
import com.mrndevs.weatherapp.ui.component.CircleBackground
import com.mrndevs.weatherapp.ui.component.EmptyState
import com.mrndevs.weatherapp.ui.component.SearchFieldWithIndicator
import com.mrndevs.weatherapp.ui.component.ShimmerEffect
import com.mrndevs.weatherapp.ui.screen.weather.WeatherUiState
import com.mrndevs.weatherapp.ui.theme.SP14
import com.mrndevs.weatherapp.ui.theme.SP16
import com.mrndevs.weatherapp.ui.theme.W400
import com.mrndevs.weatherapp.ui.theme.accordion
import com.mrndevs.weatherapp.util.Constant

@Composable
fun WeatherLocationBottomSheet(
    onDismissRequest: (Boolean) -> Unit,
    uiState: WeatherUiState,
    isShowSheet: Boolean,
    onSearch: (String) -> Unit,
    onClick: (String) -> Unit,
    onResetSearchData: () -> Unit
) {
    var requestFocus by remember { mutableStateOf(false) }
    val spacing = Constant.DEFAULT_SPACING.dp

    val containerColor = MaterialTheme.colorScheme.background

    LaunchedEffect(isShowSheet) {
        requestFocus = uiState.settings?.isFirstRunApp == true
        if (!isShowSheet) {
            onResetSearchData()
        }
    }

    BottomSheet(
        onDismissRequest = { state -> onDismissRequest(state) },
        modifier = Modifier.fillMaxHeight(0.8f),
        containerColor = containerColor,
        confirmValueChange = !uiState.settings?.currentLocation.isNullOrBlank(),
        isShowSheet = isShowSheet
    ) {
        Column(modifier = Modifier.padding(horizontal = 18.dp)) {
            SearchFieldWithIndicator(
                onRemoveQuery = { onSearch(Constant.EMPTY_STRING) },
                onSearchConfirm = { result ->
                    onSearch(result)
                },
                requestFocus = requestFocus
            )
            Spacer(modifier = Modifier.height(spacing))
            if (uiState.weatherData.currentLocation.name.isNotBlank()) {
                val item = WeatherSearchEntity(
                    name = uiState.weatherData.currentLocation.name,
                    country = uiState.weatherData.currentLocation.country
                )
                LocationItem(
                    item = item,
                    weather = uiState.weatherData.currentWeather.code,
                    isDay = uiState.weatherData.currentWeather.isDay,
                    isCurrent = true,
                    onClick = {
                        onClick(item.name)
                        onDismissRequest(false)
                    }
                )
                Spacer(modifier = Modifier.height(spacing))
            }
            when {
                uiState.isLoadingSearch -> {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.spacedBy(spacing)
                    ) {
                        repeat(8) {
                            LocationLoadingItem()
                        }
                    }
                }

                uiState.searchResult.isEmpty() && uiState.searchQuery.isNotBlank() -> {
                    EmptyState(status = EmptyStatusEnum.EMPTY_SEARCH)
                }

                uiState.searchResult.isEmpty() -> {
                    EmptyState(status = EmptyStatusEnum.SEARCH)
                }

                else -> {
                    LazyColumn(verticalArrangement = Arrangement.spacedBy(spacing)) {
                        items(uiState.searchResult.size) {
                            LocationItem(
                                item = uiState.searchResult[it],
                                onClick = {
                                    onClick(uiState.searchResult[it].name)
                                    onDismissRequest(false)
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun LocationItem(
    item: WeatherSearchEntity,
    weather: Int? = null,
    isDay: Boolean? = null,
    isCurrent: Boolean = false,
    onClick: () -> Unit
) {
    val border = if (isCurrent) SolidColor(Color.White.copy(alpha = 0.8f)) else null

    CardItem(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        containerColor = MaterialTheme.colorScheme.primary,
        border = border
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 18.dp, vertical = Constant.DEFAULT_SPACING.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(text = item.name, style = SP16)
                Text(text = item.country, style = SP14.W400, color = accordion)
            }
            Spacer(modifier = Modifier.weight(1f))
            CircleBackground {
                if (weather != null && isDay != null) {
                    WeatherImage(
                        code = weather,
                        isDay = isDay,
                        modifier = Modifier
                            .padding(8.dp)
                            .size(24.dp)
                    )
                }
            }
        }
    }
}

@Composable
private fun LocationLoadingItem() {
    CardItem(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.padding(horizontal = 18.dp, vertical = Constant.DEFAULT_SPACING.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                ShimmerEffect(width = 100.dp)
                Spacer(modifier = Modifier.height(4.dp))
                ShimmerEffect(width = 90.dp, height = 14.dp)
            }
        }
    }
}