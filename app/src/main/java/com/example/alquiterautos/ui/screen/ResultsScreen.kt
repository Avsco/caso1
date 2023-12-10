package com.example.alquiterautos.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.alquiterautos.R
import com.example.alquiterautos.data.Probability
import com.example.alquiterautos.model.CarRental
import com.example.alquiterautos.ui.component.InfoCell

@Composable
fun ResultsScreen(
    viewModel: CarRental = hiltViewModel(),
    modifier: Modifier = Modifier,
    onGenerateReport: () -> Unit = {},
    onSeeResultsPerCar: () -> Unit = {},
    onEndSimulation: () -> Unit = {},
    onSeeGraphic: () -> Unit = {},
) {
    val dataState by viewModel.dataState.collectAsState()
    val maxBalance = dataState.balance.maxOrNull() ?: 0
    val indexMaxBalance = dataState.balance.indexOf(maxBalance)

    val rentAccumulated = dataState.daysInformation[indexMaxBalance]
        .map { it.daysRented }
        .reduce { acc, i -> (i * Probability.COST_PER_RENT) + acc }

    val penaltyAccumulated = dataState.daysInformation[indexMaxBalance]
        .map { it.penalty }
        .reduce { acc, i -> (i * Probability.IDLE) + acc }

    val noRentCars = dataState.daysInformation[indexMaxBalance]
        .map { it.carsNoRent }
        .reduce { acc, i -> (i * Probability.COST_PER_NO_RENT) + acc }

    val maxCar = dataState.typeCars.maxBy { it.accBalance }

    Column(modifier = modifier.padding(42.dp).verticalScroll(rememberScrollState())) {
        Text(text = "La cantidad óptima de automóviles a comprar son ${indexMaxBalance + 1} los cuales nos genera el siguiente resultado financiero: ")
        Column(modifier = modifier.padding(all = 30.dp)) {
            InfoCell(
                title = stringResource(id = R.string.simulated_time),
                text = stringResource(id = R.string.custom_days, 365),
            )
            InfoCell(
                title = stringResource(id = R.string.total_revenue),
                text = stringResource(id = R.string.custom_currency, rentAccumulated),
            )
            InfoCell(
                title = stringResource(id = R.string.total_cost),
                text = stringResource(id = R.string.custom_currency, noRentCars + penaltyAccumulated),
            )
            Column(modifier = modifier.padding(start = 30.dp)){
                InfoCell(
                    title = stringResource(id = R.string.input_leisure_cost),
                    text = stringResource(id = R.string.custom_currency, noRentCars),
                )
                InfoCell(
                    title = stringResource(id = R.string.input_available_cost),
                    text = stringResource(id = R.string.custom_currency, penaltyAccumulated),
                )
            }
            InfoCell(
                title = stringResource(id = R.string.utility),
                text = stringResource(id = R.string.custom_currency, maxBalance),
            )
        }
        Text(text = "El auto que más rentabilidad genero es: " + maxCar.model + ", con " + maxCar.accBalance.toString())
        Spacer(modifier = modifier.height(10.dp))
        Text(text = "Los modelos rentados son:  ")
        Spacer(modifier = modifier.height(5.dp))
        LazyColumn(modifier = modifier.height(120.dp)){
            items(dataState.typeCars, key = { it.model }) {
                Text(text = it.model)
            }
        }
        Column (modifier = modifier.fillMaxWidth()) {
            Row(modifier = modifier.fillMaxWidth()) {
                Button(onClick = { onSeeResultsPerCar() }, modifier = modifier.weight(1f)) {
                    Text(text = "Información\nindividual")
                }
                Spacer(modifier = modifier.width(20.dp))
                Button(onClick = { onSeeGraphic() }, modifier = modifier.weight(1f)) {
                    Text(text = "Gráfica\ncomparativa")
                }
            }
            Spacer(modifier = modifier.height(15.dp))
            Row(modifier = modifier.fillMaxWidth()) {
                Button(onClick = { onGenerateReport() }, modifier = modifier.weight(1f)) {
                    Text(text = "Generar\nreporte")
                }
                Spacer(modifier = modifier.width(20.dp))
                Button(onClick = { onEndSimulation() }, modifier = modifier.weight(1f)) {
                    Text(text = "Terminar\nsimulacion")
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewResultsScreen(){
    Surface {
        ResultsScreen()
    }
}