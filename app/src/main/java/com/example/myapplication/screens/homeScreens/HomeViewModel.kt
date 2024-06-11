package com.example.myapplication.screens.homeScreens

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.InvestmentRepository
import com.example.myapplication.data.InvestmentType
import com.example.myapplication.data.Prevod
import com.example.myapplication.data.PrevodRepository
import com.example.myapplication.data.TypPrevodu
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.toList
import java.text.NumberFormat

class HomeViewModel(
    val prevodRepository: PrevodRepository,
    val investmentRepository: InvestmentRepository
) : ViewModel(
) {

    var homeUiState by mutableStateOf(HomeUiState())
        private set


    init {
        viewModelScope.launch {
            val combinedFlow = combine(
                prevodRepository.getAllPrevodyByTypeStream(TypPrevodu.PRIJEM),
                prevodRepository.getAllPrevodyByTypeStream(TypPrevodu.VYDAJ),
                investmentRepository.getAllInvestmentsByTypeStream(InvestmentType.Stock),
                investmentRepository.getAllInvestmentsByTypeStream(InvestmentType.Crypto)
            ) { prijmy, vydaje, stocks, cryptos ->
                CombinedData(
                    celkovePrijmy = prijmy.sumOf { it.hodnota },
                    celkoveVydaje = vydaje.sumOf { -it.hodnota },
                    hodnotaAkcii = stocks.sumOf { it.currentAmount },
                    ziskZAkcii = stocks.sumOf { it.currentAmount - it.startingAmount },
                    hodnotaKryptomien = cryptos.sumOf { it.currentAmount }, // přidáno pro hodnotu kryptoměn
                    ziskZKryptomien = cryptos.sumOf { it.currentAmount - it.startingAmount } // přidáno pro zisk z kryptoměn
                )
            }

            // Collect combined data and update homeUiState
            combinedFlow.collect { combinedData ->
                homeUiState = homeUiState.copy(
                    celkovePrijmy = combinedData.celkovePrijmy,
                    celkoveVydaje = combinedData.celkoveVydaje,
                    hodnotaAkcii = combinedData.hodnotaAkcii,
                    ziskZAkcii = combinedData.ziskZAkcii,
                    hodnotaKryptomien = combinedData.hodnotaKryptomien,
                    ziskZKryptomien = combinedData.ziskZKryptomien
                )
            }
        }
    }
}

data class HomeUiState(
    val celkovePrijmy: Double = 0.0,
    val celkoveVydaje: Double = 0.0,
    val hodnotaAkcii: Double = 0.0,
    val ziskZAkcii: Double = 0.0,
    val hodnotaKryptomien: Double = 0.0,
    val ziskZKryptomien: Double = 0.0
)

data class CombinedData(
    val celkovePrijmy: Double,
    val celkoveVydaje: Double,
    val hodnotaAkcii: Double,
    val ziskZAkcii: Double,
    val hodnotaKryptomien: Double,
    val ziskZKryptomien: Double
)