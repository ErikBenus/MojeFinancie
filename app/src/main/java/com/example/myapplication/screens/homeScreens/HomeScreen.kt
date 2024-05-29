package com.example.myapplication.screens.homeScreens


import TransactionUiState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myapplication.R
import com.example.myapplication.screens.MyViewModelProvider
import com.example.myapplication.screens.financeScreens.formattedPrice
import com.example.myapplication.screens.navigation.AppTopBar
import java.text.NumberFormat


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Home(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = viewModel(factory = MyViewModelProvider.Factory)
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
        Statistika(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            homeUiState = viewModel.homeUiState
        )
    }
}


@Composable
fun Statistika(
    modifier: Modifier = Modifier,
    homeUiState: HomeUiState
) {

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
            
            ObalStringDoKarty(homeUiState.celkovePrijmy)
            
        }
        item {
            Text(
                text = stringResource(R.string.celkov_vydavky),
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.secondary
            )

            ObalStringDoKarty(homeUiState.celkoveVydaje)
            
        }
        item {
            Text(
                text = stringResource(R.string.hodnota_akcii),
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.secondary
            )

            ObalStringDoKarty(homeUiState.hodnotaAkcii)

        }
        item {
            Text(
                text = stringResource(R.string.zisk_z_akcii),
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.secondary
            )

            ObalStringDoKarty(homeUiState.ziskZAkcii)

        }
        item {
            Text(
                text = stringResource(R.string.hodnota_kryptomien),
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.secondary
            )

            ObalStringDoKarty(homeUiState.hodnotaKryptomien)

        }
        item {
            Text(
                text = stringResource(R.string.zisk_z_kryptomien),
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.secondary
            )


            ObalStringDoKarty(homeUiState.ziskZKryptomien)

        }
    }
}

@Composable
fun ObalStringDoKarty(
    hodnota: Double
) {
    val cardColors = when {
        hodnota == 0.0 -> CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer,
            contentColor = MaterialTheme.colorScheme.onSecondaryContainer
        )
        hodnota > 0 -> CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            contentColor = MaterialTheme.colorScheme.onPrimaryContainer
        )
        else -> CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.errorContainer,
            contentColor = MaterialTheme.colorScheme.onErrorContainer
        )
    }

    Card(
        colors = cardColors
    ) {
        Text(
            modifier = Modifier.padding(
                top = 12.dp,
                start = 32.dp,
                bottom = 12.dp,
                end = 32.dp
            ),
            text = "${hodnota.formattedPrice()}",
            style = MaterialTheme.typography.headlineSmall
        )
    }
}

fun Double.formattedPrice(): String {
    return NumberFormat.getCurrencyInstance().format(this)
}