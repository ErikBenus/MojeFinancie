package com.example.myapplication.screens.portfolioScreens

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.Investment
import com.example.myapplication.data.InvestmentRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class EditInvestmentViewModel(
    private val investmentRepository: InvestmentRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    var investmentUiState by mutableStateOf(StockUiState())
        private set

    private fun validateInput(uiState: StockDetails = investmentUiState.stockDetails): Boolean {
        return with(uiState) {
            name.isNotBlank() && ticker.isNotBlank() && startValue.isNotBlank() && currentValue.isNotBlank()
        }
    }

    fun getInvestmentById(id: Int): Flow<Investment?> {
        return investmentRepository.getInvestmentStream(id)
    }

    suspend fun updateStock() {
        if (validateInput()) {
            investmentRepository.updateInvestment(investmentUiState.stockDetails.toInvestment())
        }
    }

    fun updateUiState(stockDetails: StockDetails) {
        investmentUiState =
            StockUiState(stockDetails = stockDetails, isEntryValid = validateInput(stockDetails))
    }

    init {
        val investmentId = savedStateHandle.get<Int>("investmentId") ?: throw IllegalArgumentException("investmentId is required")
        viewModelScope.launch {
            investmentUiState = investmentRepository.getInvestmentStream(investmentId)
                .filterNotNull()
                .first()
                .toStockUiState(true)
        }
    }
}
