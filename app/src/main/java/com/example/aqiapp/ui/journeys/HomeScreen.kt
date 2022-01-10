package com.example.aqiapp.ui.journeys

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.example.aqiapp.R
import com.example.aqiapp.ui.composables.AqiList
import com.example.aqiapp.ui.composables.ChartDialog

@Composable
fun HomeScreen(mainViewModel: MainViewModel) {
  val aqiData by mainViewModel.aqiData.observeAsState()
  val selectedCityData by mainViewModel.cityData.observeAsState()
  val showLoader by mainViewModel.showLoader.observeAsState()
  var showDialog by remember { mutableStateOf(false) }
  var selectedCity by remember { mutableStateOf("") }

  Box(modifier = Modifier.fillMaxSize()) {
    aqiData?.let {
      AqiList(aqiData = it, onItemClick = { city ->
        selectedCity = city
        mainViewModel.onCitySelected(city)
        showDialog = true
      })
    }
    showLoader?.let {
      if (it)
        Box(modifier = Modifier
          .fillMaxSize()
          .background(Color.LightGray.copy(alpha = 0.5f))) {
          Text(
            text = stringResource(R.string.loading_message),
            modifier = Modifier
              .align(Alignment.Center),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.h5
          )
        }
    }
    if (showDialog)
      selectedCityData?.let {
        ChartDialog(
          city = selectedCity,
          cityData = it,
        ) {
          showDialog = false
        }
      }
  }
}