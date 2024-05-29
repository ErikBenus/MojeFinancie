package com.example.myapplication.screens

import FinanceViewModel
import android.app.Application
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.myapplication.MyFinanceApp
import com.example.myapplication.screens.homeScreens.HomeViewModel

object MyViewModelProvider {
    val Factory = viewModelFactory {

        // Initializer for FinanceViewModel
        initializer {
            FinanceViewModel(myFinanceApp().container.prevodRepository)
        }

        initializer {
            HomeViewModel(myFinanceApp().container.prevodRepository)
        }
    }
}

/**
 * Extension function to queries for [Application] object and returns an instance of
 * [InventoryApplication].
 */
fun CreationExtras.myFinanceApp(): MyFinanceApp =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as MyFinanceApp)