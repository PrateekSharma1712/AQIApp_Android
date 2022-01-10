package com.example.aqiapp.ui.composables

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.aqiapp.R

@Composable
fun AppHeader() {
  TopAppBar(
    backgroundColor = MaterialTheme.colors.surface,
    elevation = 4.dp,
    modifier = Modifier.height(70.dp)
  ) {
    Text(
      text = stringResource(id = R.string.app_bar_title),
      modifier = Modifier.fillMaxWidth(),
      textAlign = TextAlign.Center,
      maxLines = 1,
      style = MaterialTheme.typography.h5,
      fontStyle = FontStyle.Normal
    )
  }
}