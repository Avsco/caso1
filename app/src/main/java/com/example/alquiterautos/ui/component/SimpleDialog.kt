package com.example.alquiterautos.ui.component

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable

@Composable
fun SimpleDialog(
    title: String = "",
    text: String = "",
    content: @Composable () -> Unit = { Text("Contenido") },
    onConfirmation: () -> Unit = {},
    onDismissRequest: () -> Unit = {},
    withDismissButton: Boolean = false
) {
    AlertDialog(
        title = {
            Text(text = title)
        },
        text = {
            if(text.isEmpty()) {
                   content()

            } else {
                Text(text = text)
            }
        },

        onDismissRequest = {
            onDismissRequest()
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onConfirmation()
                }
            ) {
                Text("Aceptar")
            }
        },
        dismissButton = {
            if (withDismissButton) {
                TextButton(
                    onClick = {
                        onDismissRequest()
                    }
                ) {
                    Text("Cancelar")
                }
            } else {
            }
        }
    )
}