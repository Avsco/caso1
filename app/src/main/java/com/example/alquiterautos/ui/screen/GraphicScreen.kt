package com.example.alquiterautos.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.alquiterautos.model.CarRental
import com.example.alquiterautos.ui.component.BarChartComposable

@Composable
fun GraphicScreen(
    modifier: Modifier = Modifier,
    onBack: () -> Unit = {},
    viewModel: CarRental = hiltViewModel()
) {
    val dataState by viewModel.dataState.collectAsState()

    val maxBalance = dataState.balance.maxOrNull() ?: 0
    val indexMaxBalance = dataState.balance.indexOf(maxBalance)

    Column(modifier = modifier.padding(vertical = 10.dp, horizontal = 54.dp).verticalScroll(
        rememberScrollState()
    )) {
        Text("Esta gráfica facilita el análisis de los resultados obtenidos para cada cantidad simulada de autos. Permite identificar claramente la cantidad óptima de autos a comprar. A continuación se muestra la gráfica:  ")
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(bottom = 20.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment =  Alignment.CenterVertically)
        {
            BarChartComposable(balances = dataState.balance)
        }
        Text(text = "Observando la gráfica, se determina que la cantidad óptima de autos a adquirir es ${indexMaxBalance + 1}.")
        Button(onClick = { onBack() }, modifier = modifier
            .align(Alignment.CenterHorizontally)
            .padding(top = 10.dp)) {
            Text(text = "Aceptar")
        }
    }
}

@Preview
@Composable
fun PreviewGraphicScreen() {
    Surface {
        GraphicScreen()
    }
}