package com.example.alquiterautos.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.alquiterautos.R

@Composable
fun StartedScreen(onNext: () -> Unit = {}, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(56.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = stringResource(id = R.string.welcome),
            style = MaterialTheme.typography.titleLarge
        )
        Spacer(modifier = modifier.height(20.dp))
        Text(
            text = stringResource(id = R.string.app_name),
            style = MaterialTheme.typography.titleMedium
        )
        Spacer(modifier = modifier.height(71.dp))
        Text(
            text = stringResource(id = R.string.description),
            style = MaterialTheme.typography.bodyMedium
        )
        Spacer(modifier = modifier.height(80.dp))
        Button(onClick = { onNext() }) {
            Text(text = stringResource(id = R.string.init_simulation))
        }
    }
}

@Preview
@Composable
fun StartedScreenPreview() {
    Surface {
        StartedScreen()
    }
}