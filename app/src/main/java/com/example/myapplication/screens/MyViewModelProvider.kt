package com.example.myapplication.screens

import FinanceViewModel
import TransactionsEditViewModel
import android.app.Application
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.myapplication.MyFinanceApp
import com.example.myapplication.screens.financeScreens.TransactionDetailsViewModel
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

        initializer {
            TransactionDetailsViewModel(
                myFinanceApp().container.prevodRepository)
        }

        initializer {
            TransactionsEditViewModel(
                myFinanceApp().container.prevodRepository,
                this.createSavedStateHandle()
            )
        }
    }
}

fun CreationExtras.myFinanceApp(): MyFinanceApp =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as MyFinanceApp)