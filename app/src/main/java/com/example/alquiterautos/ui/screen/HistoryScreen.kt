package com.example.alquiterautos.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.alquiterautos.model.CarRental

@Composable
fun HistoryScreen(modifier: Modifier = Modifier, viewModel: CarRental = hiltViewModel()) {
    val dataState by viewModel.dataState.collectAsState()

    Column {
        if (dataState.persistenceDB.isEmpty()) {
            Text(text = "No hay simulaciones registradas")
        } else {
            LazyColumn(
                modifier = modifier
                    .verticalScroll(rememberScrollState())
                    .height(400.dp)
            ) {
                items(dataState.persistenceDB, key = { it.balances[3] + it.bestCar.accBalance }) {
                    Text(
                        text = "Simulacion ${dataState.persistenceDB.indexOf(it) + 1}",
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Text(
                        text = "Balances por numero de autos \n ${
                            it.balances.mapIndexed { index, i -> "${index + 1} auto${if (index == 0) "" else "s"} $i" }
                                .joinToString(separator = "\n ")
                        }"
                    )
                    Text(
                        text = "El auto que mayor rentabilidad genero es ${it.bestCar.model}",
                        modifier = modifier.padding(bottom = 15.dp)
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun HistoryScreenPreview() {
    HistoryScreen()
}