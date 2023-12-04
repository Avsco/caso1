package com.example.alquiterautos.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun InformationTitle(modifier: Modifier = Modifier, title: String = "", content: @Composable () -> Unit ){
    var isOpenDialog by remember { mutableStateOf(false) }

    when {
        isOpenDialog -> {
            SimpleDialog(
                onDismissRequest = { isOpenDialog = false },
                onConfirmation = {
                    isOpenDialog = false
                },
                title = title,
                content = content,
            )
        }
    }

    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(text = "$title:", fontWeight = FontWeight(700))
        IconButton(onClick = { isOpenDialog = true }) {
            Icon(
                imageVector = Icons.Filled.Info,
                contentDescription = "Informacion: $title"
            )
        }
    }
}

@Preview
@Composable
fun PreviewInformationTitle(){
    Surface {
        InformationTitle(title = "Informacion", content = { Text(text = "Hola") })
    }
}