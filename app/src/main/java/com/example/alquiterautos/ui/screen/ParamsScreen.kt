package com.example.alquiterautos.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.alquiterautos.R
import com.example.alquiterautos.data.InfoCar
import com.example.alquiterautos.model.CarRental
import com.example.alquiterautos.ui.component.CustomInput
import com.example.alquiterautos.ui.component.SimpleDialog

@Composable
fun ParamsScreen(modifier: Modifier = Modifier, onNextScreen: () -> Unit = {}, viewModel: CarRental = hiltViewModel()) {
    var isOpenDialog by remember { mutableStateOf(false) }
    val dataState by viewModel.dataState.collectAsState()

    when {
        isOpenDialog -> {
            SimpleDialog(
                onDismissRequest = { isOpenDialog = false },
                onConfirmation = {
                    isOpenDialog = false
                    onNextScreen()
                },
                withDismissButton = true,
                title = "Advertencia",
                text = "El orden en el que ingresó la información de cada uno de los modelos, es el orden en el que se realizará la simulación",
            )
        }
    }

    Column(modifier = modifier
        .padding(34.dp)
        .fillMaxWidth()
        .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = "Ingrese el modelo y los precios:")
        ParamsItemCar(number = 0, data = dataState.typeCars) { it: InfoCar ->
            viewModel.updateTypeCar(0, it)
        }
        Spacer(modifier = modifier.height(41.dp))
        ParamsItemCar(number = 1, data = dataState.typeCars){ it: InfoCar ->
            viewModel.updateTypeCar(1, it)
        }
        Spacer(modifier = modifier.height(41.dp))
        ParamsItemCar(number = 2, data = dataState.typeCars){ it: InfoCar ->
            viewModel.updateTypeCar(2, it)
        }
        Spacer(modifier = modifier.height(41.dp))
        ParamsItemCar(number = 3, data = dataState.typeCars){ it: InfoCar ->
            viewModel.updateTypeCar(3, it)
        }
        Spacer(modifier = modifier.height(41.dp))
        Text(text = "Los datos que llevan “*” al inicio deben ser llenados de manera obligatoria")
        Spacer(modifier = modifier.height(64.dp))
        Button(onClick = { isOpenDialog = true }) {
            Text(text = "Enviar datos")
        }
    }
}

@Composable
fun ParamsItemCar(
    modifier: Modifier = Modifier,
    number: Int,
    data: List<InfoCar>,
    updateCar: (InfoCar) -> Unit
) {
    Column(modifier = modifier.fillMaxWidth()) {
        CustomInput(
            label = stringResource(id = R.string.input_model, number + 1),
            value = data[number].model,
            onValueChange = { updateCar(
                InfoCar(
                availableCost = data[number].availableCost,
                leisureCost = data[number].leisureCost,
                model = it,
                price = data[number].price
            )) },
            modifier = modifier,
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next,
            )
        )
        CustomInput(
            label =  stringResource(id = R.string.input_price),
            value = data[number].price.toString(),
            onValueChange = { updateCar(InfoCar(
                availableCost = data[number].availableCost,
                leisureCost = data[number].leisureCost,
                model = data[number].model,
                price = it.toInt()
            )) },
            modifier = modifier,
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next,
            )
        )
        CustomInput(
            label = stringResource(id = R.string.input_leisure_cost),
            value = data[number].leisureCost.toString(),
            onValueChange = { updateCar(InfoCar(
                availableCost = data[number].availableCost,
                leisureCost = it.toInt(),
                model = data[number].model,
                price = data[number].price
            )) },
            modifier = modifier,
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next,
            )
        )
        CustomInput(
            label = stringResource(id = R.string.input_available_cost),
            value = data[number].availableCost.toString(),
            onValueChange = { updateCar(InfoCar(
                availableCost = it.toInt(),
                leisureCost = data[number].leisureCost,
                model = data[number].model,
                price = data[number].price
            )) },
            modifier = modifier,
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done,
            )
        )
    }
}


@Preview
@Composable
fun PreviewParamsScreen() {
    Surface {
        ParamsScreen()
    }
}