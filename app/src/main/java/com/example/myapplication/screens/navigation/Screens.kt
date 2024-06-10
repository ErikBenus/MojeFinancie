package com.example.myapplication.screens.navigation

enum class Screens {
    HomeScreen,
    FinanceScreen,
    StocksScreen,
    CryptoScreen,
    AddTransactionScreen
}

object TransactionScreens {
    const val TransactionsDetailsScreenRoute = "transactions_details_screen/{isIncome}"
    fun transactionsDetailsRoute(isIncome: Boolean) = "transactions_details_screen/$isIncome"

    const val TransactionsEditScreenRoute = "transactions_edit_screen/{prevodId}"
    fun transactionsEditRoute(prevodId: Int) = "transactions_edit_screen/$prevodId"

}
