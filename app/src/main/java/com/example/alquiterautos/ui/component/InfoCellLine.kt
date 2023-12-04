package com.example.alquiterautos.ui.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun InfoCellLine(title: String, text: String, modifier: Modifier = Modifier) {
    Row() {
            Text(
                text = "$title:",
                style = MaterialTheme.typography.labelMedium,
                fontWeight = FontWeight(700),
                        modifier = Modifier.wrapContentWidth().wrapContentHeight()
            )
            Text(
                text = text,
                style = MaterialTheme.typography.labelMedium,
                modifier = Modifier.wrapContentWidth().wrapContentHeight()
            )
    }
}

@Preview
@Composable
fun PreviewInfoCellLine(){
    Surface {
        InfoCellLine(title = "Tiempo simulado", text = "xxxxxxasdasdasdasdasdasdasdasdasdasdasdasdsaxxxx dias")
    }
}