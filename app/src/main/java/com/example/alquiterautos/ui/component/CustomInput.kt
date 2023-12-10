package com.example.alquiterautos.ui.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp

@Composable
fun CustomInput(
    modifier: Modifier = Modifier,
    value: String = "",
    onValueChange: (it: String) -> Unit = {},
    keyboardOptions: KeyboardOptions =  KeyboardOptions.Default,
    label: String)
{
    Row (verticalAlignment = Alignment.CenterVertically) {
        Text(
            text = "$label:",
            style = MaterialTheme.typography.labelMedium
        )
        BasicTextField(
            value = value,
            onValueChange = { onValueChange(it) },
            modifier = modifier
                .fillMaxWidth()
                .padding(0.dp),
            keyboardOptions = keyboardOptions,
            textStyle = TextStyle(color = MaterialTheme.colorScheme.tertiary)
        )
    }

}