package com.example.aqiapp.ui.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.aqiapp.ui.theme.AQIAppTheme

@Composable
fun AqiDataRow(aqi: Triple<String, Double, String>, modifier: Modifier) {
  val rangeColor = aqiRangeColor(aqi.second)
  Row(
    modifier = modifier,
  ) {
    Divider(
      modifier = Modifier
        .height(32.dp)
        .width(3.dp)
        .background(rangeColor)
    )
    Divider(
      modifier = Modifier
        .width(16.dp)
        .height(1.5.dp)
        .background(rangeColor)
        .align(Alignment.CenterVertically)
    )
    Text(
      text = aqi.first,
      style = MaterialTheme.typography.subtitle1.copy(fontWeight = FontWeight.Medium),
      modifier = Modifier
        .padding(horizontal = 4.dp)
        .align(Alignment.CenterVertically)
    )
    Divider(
      modifier = Modifier
        .weight(1f)
        .height(1.5.dp)
        .background(rangeColor)
        .align(Alignment.CenterVertically)
    )
    Card(
      elevation = 0.dp,
      backgroundColor = rangeColor,
      shape = RoundedCornerShape(corner = CornerSize(16.dp)),
      modifier = Modifier
        .align(Alignment.CenterVertically)
        .width(70.dp)
    ) {
      Column(
        modifier = Modifier
          .padding(horizontal = 8.dp, vertical = 1.dp)
      ) {
        Text(
          style = MaterialTheme.typography.subtitle2.copy(
            color = Color.Black
          ),
          text = String.format("%.2f", aqi.second),
          modifier = Modifier.fillMaxWidth(),
          textAlign = TextAlign.Center
        )
      }
    }
    Text(
      style = MaterialTheme.typography.caption,
      text = aqi.third,
      textAlign = TextAlign.Center,
      fontStyle = FontStyle.Italic,
      modifier = Modifier
        .padding(start = 8.dp)
        .width(75.dp)
        .align(Alignment.CenterVertically)
    )
  }
}

fun aqiRangeColor(aqi: Double): Color {
  when {
    aqi <= 50 -> {
      return Color(0xFF55A84F)
    }
    aqi > 50 && aqi <= 100 -> {
      return Color(0xFFA3C853)
    }
    aqi > 100 && aqi <= 200 -> {
      return Color(0xFFFFF833)
    }
    aqi > 200 && aqi <= 300 -> {
      return Color(0xFFF29C33)
    }
    aqi > 300 && aqi <= 400 -> {
      return Color(0xFFE93F33)
    }
    else -> return Color(0xFFAF2D24)
  }
}

@Preview(showBackground = true)
@Composable
fun AqiDataRowPreview_Light() {
  AQIAppTheme {
    Surface(
      color = MaterialTheme.colors.background
    ) {
      AqiDataRow(
        Triple("Mumbai", 230.03343, "20 sec. ago"),
        Modifier.clickable {

        },
      )
    }
  }
}