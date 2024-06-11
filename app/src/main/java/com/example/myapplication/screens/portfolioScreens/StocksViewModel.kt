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

class StocksViewModel(private val investmentRepository: InvestmentRepository) : ViewModel() {

    var stockUiState by mutableStateOf(StockUiState())
        private set

    private fun validateInput(uiState: StockDetails = stockUiState.stockDetails): Boolean {
        return with(uiState) {
            name.isNotBlank() && ticker.isNotBlank() && startValue.isNotBlank() && currentValue.isNotBlank()
        }
    }

    fun deleteStock(investment: Investment) {
        viewModelScope.launch {
            investmentRepository.deleteInvestment(investment)
        }
    }

    suspend fun saveStock() {
        if (validateInput()) {
            investmentRepository.insertInvestment(stockUiState.stockDetails.toInvestment())
        }
    }

    fun updateUiState(stockDetails: StockDetails) {
        stockUiState = StockUiState(stockDetails = stockDetails, isEntryValid = validateInput(stockDetails))
    }

    init {
        viewModelScope.launch {
            investmentRepository.getAllInvestmentsByTypeStream(InvestmentType.Stock)
                .collect { investments ->
                    val totalStartValue = investments.sumOf { it.startingAmount }
                    val totalCurrentValue = investments.sumOf { it.currentAmount }
                    stockUiState = stockUiState.copy(
                        stocks = investments,
                        totalStartValue = totalStartValue,
                        totalCurrentValue = totalCurrentValue,
                    )
                }
        }
    }
}

data class StockDetails(
    val id: Int = 0,
    val name: String = "",
    val ticker: String = "",
    val startValue: String = "",
    val currentValue: String = "",
    val type: InvestmentType = InvestmentType.Stock
)

data class StockUiState(
    val stockDetails: StockDetails = StockDetails(),
    val isEntryValid: Boolean = false,
    val stocks: List<Investment> = emptyList(),
    val totalStartValue: Double = 0.0,
    val totalCurrentValue: Double = 0.0,
)

fun StockDetails.toInvestment(): Investment = Investment(
    id = id,
    name = name,
    ticker = ticker,
    startingAmount = startValue.toDoubleOrNull() ?: 0.0,
    currentAmount = currentValue.toDoubleOrNull() ?: 0.0,
    type = type
)

fun Investment.toStockUiState(isEntryValid: Boolean = false): StockUiState = StockUiState(
    stockDetails = this.toStockDetails(),
    isEntryValid = isEntryValid
)

fun Investment.toStockDetails(): StockDetails = StockDetails(
    id = id,
    name = name,
    ticker = ticker,
    startValue = startingAmount.toString(),
    currentValue = currentAmount.toString(),
    type = type
)
