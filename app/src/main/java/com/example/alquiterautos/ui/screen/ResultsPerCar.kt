@file:OptIn(ExperimentalFoundationApi::class)

package com.example.alquiterautos.ui.screen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.alquiterautos.R
import com.example.alquiterautos.data.InfoCar
import com.example.alquiterautos.model.CarRental
import com.example.alquiterautos.ui.component.InfoCell
import com.example.alquiterautos.ui.component.InfoCellLine
import com.example.alquiterautos.ui.component.InformationTitle

val numberOfCars = arrayListOf(1, 2, 3, 4)

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun LayoutRentCars(
    modifier: Modifier = Modifier,
    viewModel: CarRental = hiltViewModel(),
    onEndSimulation: () -> Unit = {}
) {
    val dataState by viewModel.dataState.collectAsState()
    var day by remember { mutableStateOf("1") }
    var confirmedDay by remember { mutableStateOf("1") }

    Column(modifier = modifier.padding(8.dp)) {
        LazyHorizontalGrid(
            rows = GridCells.Fixed(1),
            verticalArrangement = Arrangement.spacedBy(4.dp),
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            modifier = modifier
                .height(48.dp)
                .align(Alignment.CenterHorizontally)
                .padding(bottom = 8.dp)
        ) {
            items(numberOfCars) {
                Button(
                    onClick = { viewModel.updateFilter(it) }, colors = ButtonDefaults.buttonColors(
                        if (dataState.filter === it) {
                            MaterialTheme.colorScheme.primary
                        } else {
                            MaterialTheme.colorScheme.secondary
                        }
                    )
                ) {
                    Text(
                        text = pluralStringResource(R.plurals.car, it, it),
                        style = MaterialTheme.typography.labelSmall
                    )
                }
            }
        }

        InformationTitle(
            title = stringResource(id = R.string.unit_cost),
            content = { Text("En esta sección se mostrará los precios que se utilizaron para realizar la simulación") }
        )
        LazyColumn(modifier = modifier.fillMaxWidth()) {
            stickyHeader {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = modifier.fillMaxWidth()
                ) {
                    Text("Modelo", modifier = modifier.weight(0.25f))
                    Text("Alquiler", modifier = modifier.weight(0.25f))
                    Text("Ocio", modifier = modifier.weight(0.25f))
                    Text("No disponible", modifier = modifier.weight(0.25f))
                }
            }
            items(
                listOf(
                    InfoCar(
                        availableCost = 10,
                        leisureCost = 10,
                        model = "Modelo 1",
                        price = 10
                    )
                ), key = { it.model }) {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = modifier.fillMaxWidth()
                ) {
                    Text(it.model, modifier = modifier.weight(0.25f))
                    Text(
                        stringResource(id = R.string.custom_currency, it.price),
                        modifier = modifier.weight(0.25f)
                    )
                    Text(
                        stringResource(id = R.string.custom_currency, it.leisureCost),
                        modifier = modifier.weight(0.25f)
                    )
                    Text(
                        stringResource(id = R.string.custom_currency, it.availableCost),
                        modifier = modifier.weight(0.25f)
                    )
                }
            }
        }
        InformationTitle(
            title = "Simulacion",
            content = {
                Column {
                    Text(text = "En esta sección se mostrará la información de la simulación donde:")
                    Spacer(modifier = modifier.height(10.dp))
                    InfoCellLine(
                        title = stringResource(id = R.string.simulated_time),
                        text = "La duración en días de la simulación."
                    )
                    InfoCellLine(
                        title = stringResource(id = R.string.total_revenue),
                        text = "La cantidad total de ingresos generados por el alquiler de autos."
                    )
                    InfoCellLine(
                        title = stringResource(id = R.string.total_cost),
                        text = "La cantidad total de gastos, que incluye pérdidas por autos inactivos y no disponibles."
                    )
                    InfoCellLine(
                        title = stringResource(id = R.string.input_leisure_cost),
                        text = "La cantidad de dinero perdido debido a autos inactivos, es decir, aquellos disponibles, pero no alquilados."
                    )
                    InfoCellLine(
                        title = stringResource(id = R.string.input_available_cost),
                        text = "La cantidad de dinero perdido por la falta de disponibilidad de autos, es decir, cuando un cliente buscaba alquilar un auto, pero no se disponía de uno."
                    )
                    InfoCellLine(
                        title = stringResource(id = R.string.utility),
                        text = " La ganancia neta total, calculada como la suma de los ingresos y la resta de los costos."
                    )
                }
            }
        )
        Column() {
            InfoCell(
                title = stringResource(id = R.string.simulated_time),
                text = stringResource(id = R.string.custom_days, 365),
            )
            InfoCell(
                title = stringResource(id = R.string.total_revenue),
                text = stringResource(id = R.string.custom_currency, 100000),
            )
            InfoCell(
                title = stringResource(id = R.string.total_cost),
                text = stringResource(id = R.string.custom_currency, 100000),
            )
            Column(modifier = modifier.padding(start = 30.dp)) {
                InfoCell(
                    title = stringResource(id = R.string.input_leisure_cost),
                    text = stringResource(id = R.string.custom_currency, 1000),
                )
                InfoCell(
                    title = stringResource(id = R.string.input_available_cost),
                    text = stringResource(id = R.string.custom_currency, 1000),
                )
            }
            InfoCell(
                title = stringResource(id = R.string.utility),
                text = stringResource(
                    id = R.string.custom_currency,
                    dataState.balance.get(dataState.filter - 1)
                ),
            )
        }
        InformationTitle(
            title = "Información por día",
            content = {
                Column {
                    Text(text = "En esta sección se mostrará la información del día seleccionado donde:")
                    Spacer(modifier = modifier.height(10.dp))
                    InfoCellLine(
                        title = "Autos a rentar",
                        text = "Cantidad de autos disponible para alquilar."
                    )
                    InfoCellLine(
                        title = "Autos que se rentaron",
                        text = "Número de autos alquilados en el día seleccionado."
                    )
                    InfoCellLine(
                        title = "Autos no rentados",
                        text = "Cantidad de autos que permanecieron sin alquilar ese día."
                    )
                }
            }
        )
        Row(verticalAlignment = Alignment.CenterVertically) {
            BasicTextField(
                modifier = modifier.padding(10.dp),
                value = day,
                onValueChange = { day = it },
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done,
                )
            )
            Spacer(modifier = modifier.width(8.dp))
            Button(onClick = { confirmedDay = day }, contentPadding = PaddingValues(horizontal = 0.dp), ) {
                Text(text = "Buscar", style = MaterialTheme.typography.labelSmall)
            }
        }
        Column {
            InfoCell(
                title = stringResource(id = R.string.total_revenue),
                text = stringResource(
                    id = R.string.custom_currency,
                    dataState.daysInformation.get(dataState.filter - 1)
                        .get(confirmedDay.toInt()).rentGenerated
                ),
            )
            InfoCell(
                title = stringResource(id = R.string.total_cost),
                text = stringResource(id = R.string.custom_currency, 100000),
            )
            InfoCell(
                title = stringResource(id = R.string.utility),
                text = stringResource(id = R.string.custom_currency, 100000),
            )
            InfoCell(
                title = stringResource(id = R.string.carsRent),
                text = dataState.filter.toString(),
            )
            InfoCell(
                title = stringResource(id = R.string.carsToRent),
                text = dataState.daysInformation.get(dataState.filter - 1)
                    .get(confirmedDay.toInt()).carsToRent.toString(),
            )
            InfoCell(
                title = stringResource(id = R.string.carsNoRent),
                text = dataState.daysInformation.get(dataState.filter - 1)
                    .get(confirmedDay.toInt()).carsNoRent.toString(),
            )
        }
        Button(onClick = { onEndSimulation() }, modifier = modifier.padding(top = 20.dp).align(
            Alignment.CenterHorizontally
        )) {
            Text(text = "Terminar simulación")
        }
    }
}

@Preview
@Composable
fun PreviewLayoutRenderCars() {
    Surface {
        LayoutRentCars()
    }
}