package com.example.myapplication.screens.financeScreens

import FinanceViewModel
import TransactionUiState
import TranscactionDetails
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myapplication.R
import com.example.myapplication.data.TypPrevodu
import com.example.myapplication.screens.MyViewModelProvider
import com.example.myapplication.screens.homeScreens.HomeViewModel
import com.example.myapplication.screens.navigation.AppTopBar
import kotlinx.coroutines.launch
import java.util.Currency
import java.util.Locale
import androidx.compose.material3.Text as Text

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTransactionScreen(
    modifier: Modifier = Modifier,
    navigateBack: () -> Unit,
    viewModel: FinanceViewModel = viewModel(factory = MyViewModelProvider.Factory)
    ) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            AppTopBar(
                title = stringResource(R.string.pridaj_transakciu),
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = navigateBack
            )
        },
        )

{ innerPadding ->
        Body(
            transactionUiState = viewModel.transactionUiState,
            onTransactionValueChange = viewModel::updateUiState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.saveTransaction()
                    navigateBack()
                }
            },
            modifier = modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .fillMaxWidth()
        )
    }
}

@Composable
fun Body(
    modifier: Modifier = Modifier,
    onSaveClick: () -> Unit,
    transactionUiState: TransactionUiState,
    onTransactionValueChange: (TranscactionDetails) -> Unit

) {
    Column(
        verticalArrangement = Arrangement.spacedBy(20.dp),
        modifier = modifier
    ) {
        AddTransaction(
            transcationDetails = transactionUiState.transactionDetails,
            onValueChange = onTransactionValueChange
        )
        Button(
            onClick = onSaveClick,
            modifier = Modifier.fillMaxWidth(),
            enabled = transactionUiState.isEntryValid
        ) {
            Text(text = stringResource(R.string.ulozit))
        }
    }
}


@Composable
fun AddTransaction(
    modifier: Modifier = Modifier,
    transcationDetails: TranscactionDetails,
    onValueChange: (TranscactionDetails) -> Unit = {}) {

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        OutlinedTextField(
            value = transcationDetails.nazov,
            onValueChange = {onValueChange(transcationDetails.copy(nazov = it))},
            label = { Text(text = stringResource(R.string.zadaj_nazov)) },
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                unfocusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                disabledContainerColor = MaterialTheme.colorScheme.secondaryContainer,
            ),
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        OutlinedTextField(
            value = transcationDetails.hodnota,
            onValueChange = {onValueChange(transcationDetails.copy(hodnota = it))},
            label = { Text(text = stringResource(R.string.zadaj_sumu)) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                unfocusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                disabledContainerColor = MaterialTheme.colorScheme.secondaryContainer,
            ),
            leadingIcon = { Text(Currency.getInstance(Locale.getDefault()).symbol) },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        var isVydajSelected by remember { mutableStateOf(false) }
        var isPrijemSelected by remember { mutableStateOf(false) }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {


            Checkbox(
                checked = isPrijemSelected,
                onCheckedChange = {
                    isPrijemSelected = it
                    if (it) {
                        isVydajSelected = false // Pri zvolení príjmu, výdaj musí byť zrušený
                        onValueChange(transcationDetails.copy(typ = TypPrevodu.PRIJEM))
                    }
                },
                modifier = Modifier.padding(end = 8.dp)
            )
            Text(text = stringResource(R.string.prijem))

            Checkbox(
                checked = isVydajSelected,
                onCheckedChange = {
                    isVydajSelected = it
                    if (it) {
                        isPrijemSelected = false // Pri zvolení výdaju, príjem musí byť zrušený
                        onValueChange(transcationDetails.copy(typ = TypPrevodu.VYDAJ))
                    }
                },
                modifier = Modifier.padding(start = 8.dp)
            )
            Text(text = stringResource(R.string.vydaj))
        }
    }
}