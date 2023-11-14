package com.example.alquiterautos

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.alquiterautos.model.CarRental
import com.example.alquiterautos.ui.theme.AlquiterAutosTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AlquiterAutosTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    LayoutRentCars()
                }
            }
        }
    }
}

val numberOfCars = arrayListOf<Int>(1, 2, 3, 4)

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun LayoutRentCars(modifier: Modifier = Modifier, viewModel: CarRental = hiltViewModel()) {
    val dataState by viewModel.dataState.collectAsState()
    var day by remember { mutableStateOf("1") }
    var confirmedDay by remember { mutableStateOf("1") }

    Column(modifier = modifier.padding(8.dp)) {
        Text(
            text = stringResource(id = R.string.title),
            modifier = modifier.padding(bottom = 8.dp),
            style = MaterialTheme.typography.titleMedium
        )
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
        Text(
            text = stringResource(id = R.string.rentGenerated, dataState.balance.toString()),
            style = MaterialTheme.typography.bodyLarge,
            modifier = modifier.padding(bottom = 64.dp)
        )
        Text(
            text = "Información por dia",
            style = MaterialTheme.typography.titleMedium,
        )
        Row(verticalAlignment = Alignment.CenterVertically) {
            TextField(
                label = { Text("Información del dia") },
                value = day,
                onValueChange = { day = it },
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done,
                )
            )
            Spacer(modifier = modifier.width(8.dp))
            Button(onClick = { confirmedDay = day }) {
                Text(text = "Buscar", style = MaterialTheme.typography.labelSmall)
            }
        }
        Column {
            Text(
                text = stringResource(
                    R.string.rentGenerated,
                    dataState.daysInformation.get(confirmedDay.toInt()).rentGenerated
                )
            )
            Spacer(modifier = modifier.height(8.dp))
            Text(
                text = stringResource(
                    R.string.carsRent,
                    dataState.filter
                )
            )
            Spacer(modifier = modifier.height(8.dp))
            Text(
                text = stringResource(
                    R.string.carsToRent,
                    dataState.daysInformation.get(confirmedDay.toInt()).carsToRent
                )
            )
            Spacer(modifier = modifier.height(8.dp))
            Text(
                text = stringResource(
                    R.string.carsNoRent,
                    dataState.daysInformation.get(confirmedDay.toInt()).carsNoRent
                )
            )
            Spacer(modifier = modifier.height(8.dp))
            Text(
                text = stringResource(
                    R.string.daysRent,
                    dataState.daysInformation.get(confirmedDay.toInt()).daysRented
                )
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    AlquiterAutosTheme {
        LayoutRentCars()
    }
}