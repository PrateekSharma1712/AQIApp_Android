package com.example.aqiapp.ui.composables

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign

@Composable
fun OneLineLabel(text: String, textStyle: TextStyle, textAlign: TextAlign) {
  Row(
    modifier = Modifier
      .fillMaxWidth(),
    verticalAlignment = Alignment.CenterVertically
  ) {
    Text(
      text = text,
      modifier = Modifier.fillMaxWidth(),
      textAlign = textAlign,
      maxLines = 1,
      style = textStyle,
      fontStyle = FontStyle.Normal
    )
  }
}