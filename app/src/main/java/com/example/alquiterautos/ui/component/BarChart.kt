package com.example.alquiterautos.ui.component

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import co.yml.charts.axis.AxisData
import co.yml.charts.common.model.Point
import co.yml.charts.ui.barchart.BarChart
import co.yml.charts.ui.barchart.models.BarChartData
import co.yml.charts.ui.barchart.models.BarData

@Composable
fun BarChartComposable(modifier: Modifier = Modifier, balances: List<Int>) {
    val getBalance = balances.maxOrNull() ?: 0

    val barsData = listOf(
        BarData(
            label = "",
            point = Point(1f, 0f),
            description = "asd",
            color = MaterialTheme.colorScheme.primary
        ),
        BarData(
            label = "1 auto",
            point = Point(2f, balances.get(0).toFloat()),
            description = "asd",
            color = MaterialTheme.colorScheme.primary
        ),
        BarData(
            label = "2 autos",
            point = Point(3f, balances.get(1).toFloat()),
            description = "asd",
            color = MaterialTheme.colorScheme.secondary
        ),
        BarData(
            label = "3 autos",
            point = Point(4f, balances.get(2).toFloat()),
            description = "asd",
            color = MaterialTheme.colorScheme.tertiary
        ),
        BarData(
            label = "4 autos",
            point = Point(5f, balances.get(3).toFloat()),
            description = "asd",
            color = MaterialTheme.colorScheme.errorContainer
        ),
    )

    val xAxisData =
        AxisData.Builder()
            .axisStepSize(100.dp)
            .steps(barsData.size - 1)
            .bottomPadding(40.dp)
            .startPadding(40.dp)
            .axisLabelAngle(20f)
            .labelData { index -> barsData[index].label }
            .axisLineColor(MaterialTheme.colorScheme.primary)
            .axisLabelColor(MaterialTheme.colorScheme.tertiary)
            .axisLabelDescription { "Cantidad autos" }
            .build()


    val yAxisData =
        AxisData.Builder()
            .steps(getBalance/100000)
            .axisOffset(20.dp)
            .labelData { index -> (index * 100000).toString() }
            .labelAndAxisLinePadding(40.dp)
            .axisLineColor(MaterialTheme.colorScheme.primary)
            .axisLabelColor(MaterialTheme.colorScheme.tertiary)
            .axisLabelDescription { "Utilidad neta" }
            .build()


    val barsDataTemp = BarChartData(
        xAxisData = xAxisData,
        yAxisData = yAxisData,
        backgroundColor = MaterialTheme.colorScheme.surface,
        chartData = barsData
    )

    BarChart(
        modifier = modifier
            .height(350.dp)
            .padding(top = 8.dp),
        barChartData = barsDataTemp
    )
}