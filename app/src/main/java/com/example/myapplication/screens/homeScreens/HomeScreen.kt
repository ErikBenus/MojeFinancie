package com.example.myapplication.screens.homeScreens


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.myapplication.R
import com.example.myapplication.screens.MyViewModelProvider
import com.example.myapplication.screens.financeScreens.formattedPrice
import com.example.myapplication.screens.navigation.AppTopBar
import com.example.myapplication.screens.navigation.Screens


/**
 * Domovská obrazovka aplikácie. Zobrazuje štatistiky a poskytuje navigáciu k rôznym častiam aplikácie.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Home(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = viewModel(factory = MyViewModelProvider.Factory),
    navController: NavHostController
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            AppTopBar(
                title = stringResource(R.string.title_home),
                canNavigateBack = false,
                scrollBehavior = scrollBehavior
            )
        }) { innerPadding ->
        Statistic(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            homeUiState = viewModel.homeUiState,
            navController
        )
    }
}


@Composable
fun Statistic(
    modifier: Modifier = Modifier,
    homeUiState: HomeUiState,
    navController: NavHostController
) {

    /**
     * Pre každú položku vlastná karta, využitá je funckia CoverToCard
     */
    LazyColumn(
        modifier = modifier.padding(top = 6.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally

    ) {
        item {
            Text(
                text = stringResource(R.string.celkovy_prijem),
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.secondary
            )
            
            CoverToCard(
                homeUiState.celkovePrijmy,
                navController = navController,
                destination = Screens.FinanceScreen.name
            )
            
        }
        item {
            Text(
                text = stringResource(R.string.celkov_vydavky),
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.secondary
            )

            CoverToCard(
                homeUiState.celkoveVydaje,
                navController = navController,
                destination = Screens.FinanceScreen.name
            )
            
        }
        item {
            Text(
                text = stringResource(R.string.hodnota_akcii),
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.secondary
            )

            CoverToCard(
                homeUiState.hodnotaAkcii,
                navController = navController,
                destination = Screens.StocksScreen.name
            )

        }
        item {
            Text(
                text = stringResource(R.string.zisk_z_akcii),
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.secondary
            )

            CoverToCard(
                homeUiState.ziskZAkcii,
                navController = navController,
                destination = Screens.StocksScreen.name
            )

        }
        item {
            Text(
                text = stringResource(R.string.hodnota_kryptomien),
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.secondary
            )

            CoverToCard(
                homeUiState.hodnotaKryptomien,
                navController = navController,
                destination = Screens.CryptoScreen.name
            )

        }
        item {
            Text(
                text = stringResource(R.string.zisk_z_kryptomien),
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.secondary
            )


            CoverToCard(
                homeUiState.ziskZKryptomien,
                navController = navController,
                destination = Screens.CryptoScreen.name
            )
        }
    }
}

@Composable
fun CoverToCard(
    amount: Double,
    modifier: Modifier = Modifier,
    navController: NavHostController,
    destination: String,
) {
    /**
     * Výber farieb pre kartu na základe hodnoty
     */
    val cardColors = when {
        amount == 0.0 -> CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer,
            contentColor = MaterialTheme.colorScheme.onSecondaryContainer
        )
        amount > 0 -> CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            contentColor = MaterialTheme.colorScheme.onPrimaryContainer
        )
        else -> CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.errorContainer,
            contentColor = MaterialTheme.colorScheme.onErrorContainer
        )
    }

    /**
     * Clickable, navigácia posiela na obrazovku, ktorú priajal v argumente
     */
    Card(
        colors = cardColors,
        modifier = modifier.clickable {
        navController.navigate(destination)
        }
    ) {
        Text(
            modifier = Modifier.padding(
                top = 12.dp,
                start = 32.dp,
                bottom = 12.dp,
                end = 32.dp
            ),
            text = amount.formattedPrice(),
            style = MaterialTheme.typography.headlineSmall
        )
    }
}