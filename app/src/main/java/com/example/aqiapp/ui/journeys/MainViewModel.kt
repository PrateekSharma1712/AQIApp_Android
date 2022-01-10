package com.example.aqiapp.ui.journeys

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aqiapp.framework.network.services.AqiService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.util.*
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(aqiService: AqiService) : ViewModel() {

  private val _aqiData = MutableLiveData<List<Triple<String, Double, String>>>()
  val aqiData: LiveData<List<Triple<String, Double, String>>> = _aqiData

  private val _cityData = MutableLiveData<List<Pair<Double, Long>>>()
  val cityData: LiveData<List<Pair<Double, Long>>> = _cityData

  private val _showLoader = MutableLiveData<Boolean>()
  val showLoader: LiveData<Boolean> = _showLoader

  private val cityAqiMap = mutableMapOf<String, MutableList<Pair<Double, Long>>>()

  init {
    fetchData(aqiService)
  }

  private fun fetchData(aqiService: AqiService) {
    _showLoader.postValue(true)
    aqiService.observeAqiData()
      .flowOn(Dispatchers.IO)
      .onEach {
        //1. get the current timestamp in millis
        val currentTimeInMillis = Calendar.getInstance().timeInMillis

        //2. traverse through all cities data and insert in map
        it.forEach { data ->
          val existingEntry = cityAqiMap.getOrElse(data.city, { mutableListOf() })
          existingEntry.add(Pair(data.aqi, currentTimeInMillis))
          cityAqiMap[data.city] = existingEntry
        }

        //3. traverse map entries to form a presentable list in UI
        val cityAqiList = cityAqiMap.entries.map { entry ->
          val lastAqiPair = entry.value.last()
          Triple(entry.key, lastAqiPair.first, calculateWhenString(lastAqiPair.second))
        }

        //4. update live data with sorted list
        _aqiData.postValue(cityAqiList.sortedBy { triple ->
          triple.second
        })
        _showLoader.postValue(false)
      }
      .launchIn(viewModelScope)
  }

  private fun calculateWhenString(entryTimestamp: Long): String {
    val currentTime = Calendar.getInstance()
    val diffInSeconds = (currentTime.timeInMillis - entryTimestamp) / 1000

    return when {
      diffInSeconds <= 2 -> "now"
      diffInSeconds in 3..10 -> "3 sec. ago"
      diffInSeconds in 11..30 -> "10 sec. ago"
      diffInSeconds in 31..60 -> "30 min. ago"
      diffInSeconds in 61..120 -> "1 min. ago"
      diffInSeconds in 301..600 -> "5 min. ago"

      else -> "long time back"
    }
  }

  fun onCitySelected(city: String) {
    _cityData.postValue(cityAqiMap[city])
  }
}