package com.example.myapplication.screens.homeScreens

import androidx.lifecycle.ViewModel
import com.example.myapplication.data.PrevodRepository

class HomeViewModel : ViewModel(
) {
    var celkovyPrijem = 470.50
    var celkoveVydavky = 97.50
    var hodnotaAkcii = 14_350.50
    var ziskZAkcii = 4500.27;
    var hodnotaKryptomien = 400.50
    var ziskZKryptomien = - 27.50

    fun getCelkovyPrijem(): String {
        return "$celkovyPrijem €"
    }

    fun getCelkoveVydavky(): String {
        return "-$celkoveVydavky €"
    }

    fun getHodnotaAkcii(): String {
        return "$hodnotaAkcii €"
    }

    fun getZiskZAkcii(): String {
        return "$ziskZAkcii €"
    }

    fun getHodnotaKryptomien(): String {
        return "$hodnotaKryptomien €"
    }

    fun getZiskZKryptomien(): String {
        return "$ziskZKryptomien €"
    }

    fun jeZaporny(hodnota: Double): Boolean {
        return hodnota < 0
    }

}
