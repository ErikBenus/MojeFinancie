package com.example.myapplication.screens.financeScreens

import TransactionUiState
import TranscactionDetails
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.Prevod
import com.example.myapplication.data.PrevodRepository
import com.example.myapplication.data.TypPrevodu
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import toTransaction

class TransactionDetailsViewModel(private val prevodRepository: PrevodRepository) : ViewModel() {

    var transactionUiState by mutableStateOf(TransactionUiState())
        private set

    fun deleteTransaction(prevod: Prevod) {
        viewModelScope.launch {
            prevodRepository.deletePrevod(prevod)
        }
    }

    init {
        viewModelScope.launch {
            prevodRepository.getAllPrevodyByTypeStream(TypPrevodu.VYDAJ)
                .combine(prevodRepository.getAllPrevodyByTypeStream(TypPrevodu.PRIJEM)) { vydaje, prijmy ->
                    val celkoveVydaje = vydaje.sumOf { -it.hodnota }
                    val celkovePrijmy = prijmy.sumOf { it.hodnota }
                    transactionUiState = transactionUiState.copy(
                        vydaje = vydaje,
                        celkoveVydaje = celkoveVydaje,
                        prijmy = prijmy,
                        celkovePrijmy = celkovePrijmy
                    )
                }
                .collect()

        }
    }
}
