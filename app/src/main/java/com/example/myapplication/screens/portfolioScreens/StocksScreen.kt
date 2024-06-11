package com.example.myapplication.screens.portfolioScreens

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.myapplication.R
import com.example.myapplication.screens.MyViewModelProvider
import com.example.myapplication.screens.navigation.AppTopBar
import com.example.myapplication.screens.navigation.Screens
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize

import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog

import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton

import androidx.compose.ui.Alignment
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import com.example.myapplication.data.Investment
import com.example.myapplication.screens.financeScreens.formattedPrice
import com.example.myapplication.screens.navigation.InvestmentScreens


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StocksScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    viewModel: StocksViewModel = viewModel(factory = MyViewModelProvider.Factory),
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    var deleteConfirmationRequired by rememberSaveable { mutableStateOf(mapOf<Int, Boolean>()) }

    val onDelete: (Investment) -> Unit = { investment ->
        viewModel.deleteStock(investment)
    }

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            AppTopBar(
                title = stringResource(R.string.akciove_portfolio),
                canNavigateBack = false,
                scrollBehavior = scrollBehavior
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate(Screens.AddStockScreen.name) },
                modifier = Modifier.padding(20.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = stringResource(R.string.pridaj_akciu)
                )
            }
        }) { innerPadding ->
        Column(
            modifier = modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            InvestmentBody(
                modifier = modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                stockUiState = viewModel.stockUiState,
                navController = navController,
                onDelete = onDelete,
                deleteConfirmationRequired = deleteConfirmationRequired,
                onDeleteConfirmationChange = { investmentId, confirmation ->
                    deleteConfirmationRequired = deleteConfirmationRequired.toMutableMap().apply {
                        this[investmentId] = confirmation
                    }
                }
            )
        }
    }
}

@Composable
fun InvestmentBody(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    stockUiState: StockUiState,
    onDelete: (Investment) -> Unit,
    deleteConfirmationRequired: Map<Int, Boolean>,
    onDeleteConfirmationChange: (Int, Boolean) -> Unit
) {


    val investments = stockUiState.stocks

    if (investments.isEmpty()) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        ) {
            Text(
                text = stringResource(R.string.prazdne_portofolio_upozornenie),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.secondary,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp, vertical = 16.dp)
                    .wrapContentSize(Alignment.Center)
            )
        }
    }

    investments.forEach { investment ->

        val income = investment.currentAmount - investment.startingAmount

        val cardColors = CardDefaults.cardColors(
            containerColor = if (income >= 0) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.errorContainer,
            contentColor = if (income >= 0) MaterialTheme.colorScheme.onPrimaryContainer else MaterialTheme.colorScheme.onErrorContainer
        )

        val buttonColors = ButtonDefaults.buttonColors(
            containerColor = if (income >= 0) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.error,
            contentColor = if (income >= 0) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onError
        )

        val outlinedButtonColors = ButtonDefaults.outlinedButtonColors(
            containerColor = if (income >= 0) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.errorContainer,
            contentColor = if (income >= 0) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.error
        )

        Card(
            modifier = modifier,
            colors = cardColors
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Show name
                InvestmentDetailsRow(
                    labelResID = R.string.nazov,
                    investmentDetail = investment.name,
                    modifier = Modifier.padding(horizontal = 12.dp)
                )

                InvestmentDetailsRow(
                    labelResID = R.string.ticker,
                    investmentDetail = investment.ticker,
                    modifier = Modifier.padding(horizontal = 12.dp)
                )

                InvestmentDetailsRow(
                    labelResID = R.string.hodnota_pri_zakupeni,
                    investmentDetail = investment.startingAmount.formattedPrice(),
                    modifier = Modifier.padding(horizontal = 12.dp)
                )

                InvestmentDetailsRow(
                    labelResID = R.string.aktualna_cena,
                    investmentDetail = investment.currentAmount.formattedPrice(),
                    modifier = Modifier.padding(horizontal = 12.dp)
                )

                InvestmentDetailsRow(
                    labelResID = R.string.zisk,
                    investmentDetail = (investment.currentAmount - investment.startingAmount).formattedPrice(),
                    modifier = Modifier.padding(horizontal = 12.dp)
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Button(
                        onClick = { onDeleteConfirmationChange(investment.id, true) },
                        shape = MaterialTheme.shapes.small,
                        enabled = true,
                        colors = buttonColors
                    ) {
                        Icon(
                            imageVector = Icons.Default.Clear,
                            contentDescription = stringResource(R.string.delete),
                        )
                    }

                    OutlinedButton(
                        onClick = {
                            navController.navigate(InvestmentScreens.editInvestmentRoute(investment.id))
                        },
                        shape = MaterialTheme.shapes.small,
                        enabled = true,
                        colors = outlinedButtonColors
                    ) {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = stringResource(R.string.edit),
                        )
                    }
                }

                if (deleteConfirmationRequired[investment.id] == true) {
                    DeleteConfirmationDialog(
                        onDeleteConfirm = {
                            onDeleteConfirmationChange(investment.id, false)
                        },
                        onDeleteCancel = { onDeleteConfirmationChange(investment.id, false)},
                        onDelete = onDelete,
                        investment = investment,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }
        }
    }
}


@Composable
private fun InvestmentDetailsRow(
    @StringRes labelResID: Int, investmentDetail: String, modifier: Modifier = Modifier
) {
    Row(modifier = modifier) {
        Text(stringResource(labelResID))
        Spacer(modifier = Modifier.weight(1f))
        Text(text = investmentDetail, fontWeight = FontWeight.Bold)
    }
}

@Composable
private fun DeleteConfirmationDialog(
    onDeleteConfirm: () -> Unit,
    onDeleteCancel: () -> Unit,
    onDelete: (Investment) -> Unit,
    modifier: Modifier = Modifier,
    investment: Investment
) {
    AlertDialog(
        onDismissRequest = { },
        title = { Text(stringResource(R.string.upozornenie)) },
        text = { Text(stringResource(R.string.otazky_vymazanie)) },
        modifier = modifier,
        dismissButton = {
            TextButton(onClick = {
                onDeleteCancel()
            }) {
                Text(stringResource(R.string.nie))
            }
        },
        confirmButton = {
            TextButton(onClick = {
                onDeleteConfirm()
                onDelete(investment)
            }) {
                Text(stringResource(R.string.ano))
            }
        }
    )
}
