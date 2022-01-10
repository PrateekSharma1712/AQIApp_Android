package com.example.aqiapp.ui.journeys

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import com.example.aqiapp.ui.composables.AppHeader
import com.example.aqiapp.ui.theme.AQIAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

  private val mainViewModel by viewModels<MainViewModel>()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    mainViewModel.aqiData.observe(this, {})

    setContent {
      AQIAppTheme {
        Surface(
          color = MaterialTheme.colors.background
        ) {
          Scaffold(
            topBar = { AppHeader() }
          ) {
            HomeScreen(mainViewModel)
          }
        }
      }
    }
  }
}