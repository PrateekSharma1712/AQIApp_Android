package com.example.aqiapp.ui.composables

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.example.aqiapp.R
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet

@Composable
fun Chart(cityData: List<Pair<Double, Long>>) {
  val axisColor = MaterialTheme.colors.primaryVariant.hashCode()
  val chartColor = MaterialTheme.colors.onSurface.hashCode()
  val label = stringResource(R.string.aqi_header)
  AndroidView(factory = {
    val chart = LineChart(it, null)
    val description = Description()
    description.text = ""

    chart.apply {
      setDescription(description)
      setNoDataText("Insufficient data to plot")
      axisRight.isEnabled = false
      xAxis.apply {
        position = XAxis.XAxisPosition.BOTTOM
        setDrawGridLines(false)
        textColor = axisColor
        granularity = 1f
        setAvoidFirstLastClipping(false)
      }
      axisLeft.apply {
        setDrawGridLines(false)
        textColor = axisColor
        setLabelCount(6, true)
      }
    }

    chart
  },
    modifier = Modifier
      .fillMaxWidth()
      .height(200.dp),
    update = {
      val dataPointsList = mutableListOf<Entry>()
      cityData.takeLast(10).forEachIndexed { index, entry ->
        dataPointsList.add(Entry(index.toFloat(), entry.first.toFloat()))
      }

      val dataset = LineDataSet(dataPointsList, label)
      dataset.apply {
        color = chartColor
        valueTextColor = chartColor
        setCircleColor(chartColor)
      }

      val data = LineData(dataset)
      data.isHighlightEnabled = false
      it.data = data
      it.invalidate()
    })
}

@Composable
fun ChartDialog(
  city: String,
  cityData: List<Pair<Double, Long>>,
  onDismissRequest: () -> Unit
) {
  AlertDialog(
    onDismissRequest = onDismissRequest,
    confirmButton = {
      Button(onClick = onDismissRequest) {
        Text("Close")
      }
    },
    text = {
      Chart(cityData)
    },
    title = {
      Text("Real-time chart for $city")
    }
  )
}