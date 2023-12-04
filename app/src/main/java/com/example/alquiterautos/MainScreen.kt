package com.example.alquiterautos

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.alquiterautos.model.CarRental
import com.example.alquiterautos.ui.screen.GraphicScreen
import com.example.alquiterautos.ui.screen.LayoutRentCars
import com.example.alquiterautos.ui.screen.ParamsScreen
import com.example.alquiterautos.ui.screen.ResultsScreen
import com.example.alquiterautos.ui.screen.StartedScreen

/**
 * enum values that represent the screens in the app
 */
enum class Caso1Screen(@StringRes val title: Int) {
    Start(title = R.string.app_name),
    Simulation(title = R.string.simulation),
    Params(title = R.string.params),
    Results(title = R.string.results),
    Graphic(title = R.string.graphic)
}

/**
 * Composable that displays the topBar and displays back button if back navigation is possible.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Case1AppBar(
    currentScreen: Caso1Screen,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier,
    showReload: Boolean
) {
    TopAppBar(
        title = { Text(stringResource(currentScreen.title)) },
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        ),
        modifier = modifier,
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.back_button)
                    )
                }
            }
        },
        actions = {
            if (showReload) {
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(
                        imageVector = Icons.Filled.Refresh,
                        contentDescription = "Volver a simular"
                    )
                }
            }
        }
    )
}

@Composable
fun Case1App(
    viewModel: CarRental = hiltViewModel(),
    navController: NavHostController = rememberNavController()
) {
    // Get current back stack entry
    val backStackEntry by navController.currentBackStackEntryAsState()
    // Get the name of the current screen
    val currentScreen = Caso1Screen.valueOf(
        backStackEntry?.destination?.route ?: Caso1Screen.Start.name
    )

    Scaffold(
        topBar = {
            Case1AppBar(
                currentScreen = currentScreen,
                canNavigateBack = navController.previousBackStackEntry != null,
                navigateUp = { navController.navigateUp() },
                showReload = currentScreen.name == Caso1Screen.Results.name
            )
        }
    ) { innerPadding ->

        NavHost(
            navController = navController,
            startDestination = Caso1Screen.Start.name,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(route = Caso1Screen.Start.name) {
                StartedScreen(
                    onNext = {
                        navController.navigate(Caso1Screen.Params.name)
                    },
                )
            }
            composable(route = Caso1Screen.Params.name) {
                ParamsScreen(
                    viewModel = viewModel,
                    onNextScreen = {
                        navController.navigate(Caso1Screen.Results.name)
                    },
                )
            }
            composable(route = Caso1Screen.Results.name) {
                ResultsScreen(
                    viewModel = viewModel,
                    onGenerateReport = {
                        // TODO
                    },
                    onSeeResultsPerCar = {
                        navController.navigate(Caso1Screen.Simulation.name)
                    },
                    onSeeGraphic = {
                        navController.navigate(Caso1Screen.Graphic.name)
                    },
                    onEndSimulation = {
                        navController.navigate(Caso1Screen.Start.name){
                            popUpTo(navController.graph.id) {
                                inclusive = true
                            }
                        }
                    }
                )
            }
            composable(route = Caso1Screen.Simulation.name) {
                LayoutRentCars(viewModel = viewModel, onEndSimulation = {
                    navController.navigate(Caso1Screen.Start.name){
                        popUpTo(navController.graph.id) {
                            inclusive = true
                        }
                    }
                })
            }
            composable(route = Caso1Screen.Graphic.name) {
                GraphicScreen(viewModel = viewModel, onBack = {
                    navController.navigateUp()
                })
            }
        }
    }
}