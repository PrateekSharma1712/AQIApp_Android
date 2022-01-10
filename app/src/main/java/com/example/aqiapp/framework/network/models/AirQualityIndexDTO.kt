package com.example.aqiapp.framework.network.models


import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AirQualityIndexDTO(
  val city: String,
  val aqi: Double
)