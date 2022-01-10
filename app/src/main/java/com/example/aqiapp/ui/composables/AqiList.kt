package com.example.aqiapp.ui.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.aqiapp.R
import com.example.aqiapp.ui.theme.AQIAppTheme

@Composable
fun AqiList(aqiData: List<Triple<String, Double, String>>, onItemClick: (String) -> Unit) {

  val headerStyle = MaterialTheme.typography.h6.copy(
    fontWeight = FontWeight.Light,
    color = MaterialTheme.colors.onSurface,
    letterSpacing = 2.sp
  )

  Column(
    modifier = Modifier
      .padding(16.dp)
      .fillMaxWidth()
  ) {
    Row(
      horizontalArrangement = Arrangement.SpaceBetween,
      modifier = Modifier
        .fillMaxWidth()
        .padding(bottom = 8.dp, start = 22.dp)
    ) {
      Text(
        text = stringResource(R.string.cities_header),
        style = headerStyle,
      )
      Text(
        text = stringResource(R.string.aqi_header),
        style = headerStyle,
        modifier = Modifier
          .weight(1f)
          .padding(end = 32.dp),
        textAlign = TextAlign.End
      )
      Text(
        text = stringResource(R.string.when_header),
        style = headerStyle,
      )
    }
    Spacer(modifier = Modifier.height(8.dp))
    LazyColumn {
      items(aqiData) {
        AqiDataRow(
          aqi = it,
          modifier = Modifier
            .clickable {
              onItemClick(it.first)
            }
            .height(40.dp)
            .fillMaxWidth()
            .padding(vertical = 8.dp)
        )
      }
    }
  }
}

@Preview(showBackground = true)
@Composable
fun AqiListPreview_Light() {
  AQIAppTheme {
    Surface(
      color = MaterialTheme.colors.background
    ) {
      AqiList(
        aqiData = listOf(
          Triple("Mumbai", 230.03343, "20 sec. ago"),
          Triple("Bangalore", 30.03343, "now"),
          Triple("Pune", 130.03343, "yesterday")
        )
      ) {

      }
    }
  }
}

@Preview(showBackground = true)
@Composable
fun AqiListPreview_Dark() {
  AQIAppTheme(darkTheme = true) {
    Surface(
      color = MaterialTheme.colors.background
    ) {
      AqiList(
        aqiData = listOf(
          Triple("Mumbai", 230.03343, "20 sec. ago"),
          Triple("Bangalore", 30.03343, "now"),
          Triple("Pune", 130.03343, "yesterday")
        )
      ) { }
    }
  }
}