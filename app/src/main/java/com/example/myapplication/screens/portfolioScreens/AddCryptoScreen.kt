package com.example.myapplication.screens.portfolioScreens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myapplication.R
import com.example.myapplication.screens.MyViewModelProvider
import com.example.myapplication.screens.navigation.AppTopBar
import kotlinx.coroutines.launch
import java.util.Currency
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddCryptoScreen(
    modifier: Modifier = Modifier,
    navigateBack: () -> Unit,
    viewModel: CryptoViewModel = viewModel(factory = MyViewModelProvider.Factory)
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            AppTopBar(
                title = stringResource(R.string.pridaj_kryptomenu),
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = navigateBack
            )
        },
    )

    { innerPadding ->
        CryptoBody(
            cryptoUiState = viewModel.cryptoUiState,
            onCryptoValueChange = viewModel::updateUiState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.saveCrypto()
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
fun CryptoBody(
    modifier: Modifier = Modifier,
    onSaveClick: () -> Unit,
    cryptoUiState: CryptoUiState,
    onCryptoValueChange: (CryptoDetails) -> Unit

) {
    Column(
        verticalArrangement = Arrangement.spacedBy(20.dp),
        modifier = modifier
    ) {
        AddCrypto(
            cryptoDetails = cryptoUiState.cryptoDetails,
            onValueChange = onCryptoValueChange
        )
        Button(
            onClick = onSaveClick,
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = 16.dp,
                    end = 16.dp
                ),
            enabled = cryptoUiState.isEntryValid
        ) {
            Text(text = stringResource(R.string.ulozit))
        }
    }
}


@Composable
fun AddCrypto(
    modifier: Modifier = Modifier,
    cryptoDetails: CryptoDetails,
    onValueChange: (CryptoDetails) -> Unit = {}) {

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        OutlinedTextField(
            value = cryptoDetails.name,
            onValueChange = {onValueChange(cryptoDetails.copy(name = it))},
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
            value = cryptoDetails.ticker,
            onValueChange = {onValueChange(cryptoDetails.copy(ticker = it))},
            label = { Text(text = stringResource(R.string.ticker)) },
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                unfocusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                disabledContainerColor = MaterialTheme.colorScheme.secondaryContainer,
            ),
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        OutlinedTextField(
            value = cryptoDetails.startValue,
            onValueChange = {onValueChange(cryptoDetails.copy(startValue = it))},
            label = { stringResource(R.string.investovane) },
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

        OutlinedTextField(
            value = cryptoDetails.currentValue,
            onValueChange = {onValueChange(cryptoDetails.copy(currentValue = it))},
            label = { stringResource(R.string.aktualna_hodnota_investicie) },
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
    }
}

