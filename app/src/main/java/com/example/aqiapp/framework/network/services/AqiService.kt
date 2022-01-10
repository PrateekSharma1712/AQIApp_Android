package com.example.aqiapp.framework.network.services

import com.example.aqiapp.framework.network.models.AirQualityIndexDTO
import com.tinder.scarlet.ws.Receive
import kotlinx.coroutines.flow.Flow

interface AqiService {
  @Receive
  fun observeAqiData(): Flow<List<AirQualityIndexDTO>>
}