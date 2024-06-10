package com.example.myapplication.screens.financeScreens

import FinanceViewModel
import TransactionUiState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.myapplication.R
import com.example.myapplication.data.Prevod
import com.example.myapplication.screens.MyViewModelProvider
import com.example.myapplication.screens.navigation.AppTopBar
import com.example.myapplication.screens.navigation.Screens
import com.example.myapplication.screens.navigation.TransactionScreens
import java.text.NumberFormat


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FinanceScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    viewModel: FinanceViewModel = viewModel(factory = MyViewModelProvider.Factory),
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            AppTopBar(
                title = stringResource(R.string.title_finance),
                canNavigateBack = false,
                scrollBehavior = scrollBehavior
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {navController.navigate(Screens.AddTransactionScreen.name) },
                modifier = Modifier.padding(20.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = stringResource(R.string.add_item)
                )
            }
        }) { innerPadding ->
        FinanceStastistika(
            transactionUiState = viewModel.transactionUiState,
            navController = navController,
            modifier = modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .fillMaxWidth(),
        )
    }
}

@Composable
fun FinanceStastistika(
    modifier: Modifier = Modifier,
    transactionUiState: TransactionUiState,
    navController: NavHostController
) {
    Column(
        modifier = modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
) {

    Text(
        text = stringResource(R.string.prijmy),
        style = MaterialTheme.typography.headlineMedium,
        fontWeight = FontWeight.Bold,
        color = MaterialTheme.colorScheme.primary,
        modifier = Modifier
            .fillMaxWidth()
    )
        if (!transactionUiState.prijmy.isEmpty()) {
            FinanceItem(
                stringResource(R.string.prijmy),
                transactionUiState.celkovePrijmy,
                navController,
                transactionUiState.prijmy)
        } else {
            Text(
                text = stringResource(R.string.bez_prijmov_button_plus),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.secondary,
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentWidth(Alignment.CenterHorizontally)
            )
        }

    Text(
        text = stringResource(R.string.vydavky),
        style = MaterialTheme.typography.headlineMedium,
        fontWeight = FontWeight.Bold,
        color = MaterialTheme.colorScheme.error,
        modifier = Modifier
            .fillMaxWidth()
    )
        if (!transactionUiState.vydaje.isEmpty()) {
            FinanceItem(stringResource(R.string.vydavky),
                transactionUiState.celkoveVydaje,
                navController,
                transactionUiState.vydaje)
        } else {
            Text(
                text = stringResource(R.string.bez_vydavkov_button_plus),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.secondary,
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentWidth(Alignment.CenterHorizontally)
            )
        }
}
}

@Composable
private fun FinanceItem(
    title: String,
    total: Double,
    navController: NavHostController,
    transactions: List<Prevod>
) {
    Column {
        Text(
            text = "Celková suma: ${total.formattedPrice()}",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.secondary,
            modifier = Modifier.fillMaxWidth()
        )

        val transacationType = stringResource(R.string.vydavky)
        val isIncome = title != transacationType

        val cardColors = if (isIncome) {
            CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                contentColor = MaterialTheme.colorScheme.onPrimaryContainer
            )
        } else {
            CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.errorContainer,
                contentColor = MaterialTheme.colorScheme.onErrorContainer,
            )
        }



        Card(
            colors = cardColors,
            modifier = Modifier
                .padding(top = 12.dp)
                .clickable {
                    val isIncome = isIncome// or false, based on your logic
                    navController.navigate(TransactionScreens.transactionsDetailsRoute(isIncome))
                }

        ) {
            transactions.forEach { prevod ->
                val formattedPrice = if (title == transacationType) {
                    "-${prevod.hodnota.formattedPrice()}"
                } else {
                    prevod.hodnota.formattedPrice()
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            top = 6.dp,
                            start = 16.dp,
                            bottom = 6.dp,
                            end = 16.dp
                        ),
                    Arrangement.SpaceBetween
                ) {
                    Text(
                        text = prevod.nazov,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.secondary
                    )
                    Text(
                        text = formattedPrice,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.secondary
                    )
                }
            }
        }
    }
}


fun Double.formattedPrice(): String {
    return NumberFormat.getCurrencyInstance().format(this)
}