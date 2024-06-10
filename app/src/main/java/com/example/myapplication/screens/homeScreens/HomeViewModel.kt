package com.example.myapplication.screens.homeScreens

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.Prevod
import com.example.myapplication.data.PrevodRepository
import com.example.myapplication.data.TypPrevodu
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.collect
import java.text.NumberFormat

class HomeViewModel(val prevodRepository: PrevodRepository) : ViewModel(
) {

    var homeUiState by mutableStateOf(HomeUiState())
        private set


    init {
        viewModelScope.launch {
            prevodRepository.getAllPrevodyByTypeStream(TypPrevodu.PRIJEM).combine(
                prevodRepository.getAllPrevodyByTypeStream(TypPrevodu.VYDAJ)
            ) { prijmy, vydaje ->
                val celkovePrijmy = prijmy.sumOf { it.hodnota }
                val celkoveVydaje = vydaje.sumOf { -it.hodnota }
                homeUiState = homeUiState.copy(
                    celkovePrijmy = celkovePrijmy,
                    celkoveVydaje = celkoveVydaje

                )
            }.collect()
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
