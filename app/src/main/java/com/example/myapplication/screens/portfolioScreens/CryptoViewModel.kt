package com.example.myapplication.screens.portfolioScreens

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.Investment
import com.example.myapplication.data.InvestmentRepository
import com.example.myapplication.data.InvestmentType
import kotlinx.coroutines.launch

class CryptoViewModel(private val investmentRepository: InvestmentRepository) : ViewModel() {

    var cryptoUiState by mutableStateOf(CryptoUiState())
        private set

    private fun validateInput(uiState: CryptoDetails = cryptoUiState.cryptoDetails): Boolean {
        return with(uiState) {
            name.isNotBlank() && ticker.isNotBlank() && startValue.isNotBlank() && currentValue.isNotBlank()
        }
    }

    fun deleteCrypto(investment: Investment) {
        viewModelScope.launch {
            investmentRepository.deleteInvestment(investment)
        }
    }

    suspend fun saveCrypto() {
        if (validateInput()) {
            investmentRepository.insertInvestment(cryptoUiState.cryptoDetails.toInvestment())
        }
    }

    fun updateUiState(cryptoDetails: CryptoDetails) {
        cryptoUiState = CryptoUiState(cryptoDetails = cryptoDetails, isEntryValid = validateInput(cryptoDetails))
    }

    init {
        viewModelScope.launch {
            investmentRepository.getAllInvestmentsByTypeStream(InvestmentType.Crypto)
                .collect { investments ->
                    val totalStartValue = investments.sumOf { it.startingAmount }
                    val totalCurrentValue = investments.sumOf { it.currentAmount }
                    cryptoUiState = cryptoUiState.copy(
                        cryptos = investments,
                        totalStartValue = totalStartValue,
                        totalCurrentValue = totalCurrentValue,
                    )
                }
        }
    }
}

data class CryptoDetails(
    val id: Int = 0,
    val name: String = "",
    val ticker: String = "",
    val startValue: String = "",
    val currentValue: String = "",
    val type: InvestmentType = InvestmentType.Crypto
)

data class CryptoUiState(
    val cryptoDetails: CryptoDetails = CryptoDetails(),
    val isEntryValid: Boolean = false,
    val cryptos: List<Investment> = emptyList(),
    val totalStartValue: Double = 0.0,
    val totalCurrentValue: Double = 0.0,
)

fun CryptoDetails.toInvestment(): Investment = Investment(
    id = id,
    name = name,
    ticker = ticker,
    startingAmount = startValue.toDoubleOrNull() ?: 0.0,
    currentAmount = currentValue.toDoubleOrNull() ?: 0.0,
    type = type
)

fun Investment.toCryptoUiState(isEntryValid: Boolean = false): CryptoUiState = CryptoUiState(
    cryptoDetails = this.toCryptoDetails(),
    isEntryValid = isEntryValid
)

fun Investment.toCryptoDetails(): CryptoDetails = CryptoDetails(
    id = id,
    name = name,
    ticker = ticker,
    startValue = startingAmount.toString(),
    currentValue = currentAmount.toString(),
    type = type
)
