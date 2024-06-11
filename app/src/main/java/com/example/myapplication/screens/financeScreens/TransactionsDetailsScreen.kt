package com.example.myapplication.screens.financeScreens

import TransactionUiState
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
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
import com.example.myapplication.screens.navigation.TransactionScreens


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TransactionsDetailsScreen(
    modifier: Modifier = Modifier,
    viewModel: TransactionDetailsViewModel = viewModel(factory = MyViewModelProvider.Factory),
    navigateBack: () -> Unit,
    navController: NavHostController,
    isIncome: Boolean
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    var deleteConfirmationRequired by rememberSaveable { mutableStateOf(mapOf<Int, Boolean>()) }

    val onDelete: (Prevod) -> Unit = { prevod ->
        viewModel.deleteTransaction(prevod)
    }

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            AppTopBar(
                title = stringResource(R.string.title_finance),
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = navigateBack
            )
        }) { innerPadding ->
        TransactionDetailsBody(
            transactionUiState = viewModel.transactionUiState,
            modifier = modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .fillMaxWidth(),
            isIncome = isIncome,
            onDelete = onDelete,
            navController = navController,
            deleteConfirmationRequired = deleteConfirmationRequired,
            onDeleteConfirmationChange = { prevodId, confirmation ->
                // Update the value of deleteConfirmationRequired for the given investment
                deleteConfirmationRequired = deleteConfirmationRequired.toMutableMap().apply {
                    this[prevodId] = confirmation
                }
            }
        )
    }
}


@Composable
private fun TransactionDetailsBody(
    transactionUiState: TransactionUiState,
    modifier: Modifier = Modifier,
    isIncome: Boolean,
    onDelete: (Prevod) -> Unit,
    navController: NavHostController,
    deleteConfirmationRequired: Map<Int, Boolean>,
    onDeleteConfirmationChange: (Int, Boolean) -> Unit
) {
    Column(
        modifier = modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {

        TransactionDetails(
            transactionUiState = transactionUiState,
            modifier = Modifier.fillMaxWidth(),
            onDelete = onDelete,
            isIncome = isIncome,
            navController =  navController,
            deleteConfirmationRequired = deleteConfirmationRequired,
            onDeleteConfirmationChange = onDeleteConfirmationChange
        )
    }
}

@Composable
fun TransactionDetails(
    transactionUiState: TransactionUiState,
    modifier: Modifier = Modifier,
    isIncome: Boolean,
    onDelete: (Prevod) -> Unit,
    navController: NavHostController,
    deleteConfirmationRequired: Map<Int, Boolean>,
    onDeleteConfirmationChange: (Int, Boolean) -> Unit
) {

    val transactions = if (isIncome) {
        transactionUiState.prijmy
    } else {
        transactionUiState.vydaje
    }

    // Nastavení barev karty, tlačítek a outlined tlačítek na základě isIncome
    val cardColors = CardDefaults.cardColors(
        containerColor = if (isIncome) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.errorContainer,
        contentColor = if (isIncome) MaterialTheme.colorScheme.onPrimaryContainer else MaterialTheme.colorScheme.onErrorContainer
    )

    val buttonColors = ButtonDefaults.buttonColors(
        containerColor = if (isIncome) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.error,
        contentColor = if (isIncome) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onError
    )

    val outlinedButtonColors = ButtonDefaults.outlinedButtonColors(
        containerColor = if (isIncome) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.errorContainer,
        contentColor = if (isIncome) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.error
    )

    transactions.forEach { prevod ->
    Card(
        modifier = modifier,
        colors = cardColors
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(
                12.dp
            )
        ) {
            // Zobrazit název
            TransactionDetailsRow(
                labelResID = R.string.nazov,
                transactionDetail = prevod.nazov,
                modifier = Modifier.padding(
                    horizontal = 12.dp
                )
            )

            // Zobrazit hodnotu
            TransactionDetailsRow(
                labelResID = R.string.suma,
                transactionDetail = prevod.hodnota.formattedPrice(),
                modifier = Modifier.padding(
                    horizontal = 12.dp
                )
            )


            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                // Tlačítko pro odstranění transakce
                Button(
                    onClick = { onDeleteConfirmationChange(prevod.id, true) },
                    shape = MaterialTheme.shapes.small,
                    enabled = true,
                    colors = buttonColors
                ) {
                    Icon(
                        imageVector = Icons.Default.Clear,
                        contentDescription = stringResource(R.string.delete),
                    )
                }

                // Tlačítko pro úpravu transakce
                OutlinedButton(
                    onClick = { navController.navigate(TransactionScreens.transactionsEditRoute(prevod.id))},
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

            if ((deleteConfirmationRequired[prevod.id] == true)) {
                DeleteConfirmationDialog(
                    onDeleteConfirm = {
                        onDeleteConfirmationChange(prevod.id, false)
                    },
                    onDeleteCancel = { onDeleteConfirmationChange(prevod.id, false)},
                    onDelete = onDelete,
                    prevod = prevod,
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
    }
    }
}


@Composable
private fun TransactionDetailsRow(
    @StringRes labelResID: Int, transactionDetail: String, modifier: Modifier = Modifier
) {
    Row(modifier = modifier) {
        Text(stringResource(labelResID))
        Spacer(modifier = Modifier.weight(1f))
        Text(text = transactionDetail, fontWeight = FontWeight.Bold)
    }
}

@Composable
private fun DeleteConfirmationDialog(
    onDeleteConfirm: () -> Unit,
    onDeleteCancel: () -> Unit,
    onDelete: (Prevod) -> Unit,
    modifier: Modifier = Modifier,
    prevod: Prevod
) {
    AlertDialog(
        onDismissRequest = { /* Do nothing */ },
        title = { Text(stringResource(R.string.upozornenie)) },
        text = { Text(stringResource(R.string.otazky_vymazanie)) },
        modifier = modifier,
        dismissButton = {
            TextButton(onClick = onDeleteCancel) {
                Text(stringResource(R.string.nie))
            }
        },
        confirmButton = {
            TextButton(onClick = {
                onDeleteConfirm()
                onDelete(prevod)
            }) {
                Text(stringResource(R.string.ano))
            }
        }
    )
}