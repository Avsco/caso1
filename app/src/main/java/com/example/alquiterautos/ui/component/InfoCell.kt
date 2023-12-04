package com.example.alquiterautos.ui.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun InfoCell(title: String, text: String, modifier: Modifier = Modifier) {
    Row() {
        Text(
            text = "$title:",
            style = MaterialTheme.typography.labelMedium,
            fontWeight = FontWeight(700)
        )
        Spacer(modifier = modifier.width(5.dp))
        Text(
            text = text,
            style = MaterialTheme.typography.labelMedium
        )
    }
}

@Preview
@Composable
fun PreviewInfoCell(){
    Surface {
        InfoCell(title = "Tiempo simulado", text = "xxxxxxxxxx dias")
    }
}