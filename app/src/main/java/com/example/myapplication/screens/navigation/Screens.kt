package com.example.myapplication.screens.navigation

enum class Screens {
    HomeScreen,
    FinanceScreen,
    StocksScreen,
    CryptoScreen,
    AddTransactionScreen,
    AddStockScreen,
    AddCryptoScreen,
}

object TransactionScreens {
    const val TransactionsDetailsScreenRoute = "transactions_details_screen/{isIncome}"
    fun transactionsDetailsRoute(isIncome: Boolean) = "transactions_details_screen/$isIncome"

    const val TransactionsEditScreenRoute = "transactions_edit_screen/{prevodId}"
    fun transactionsEditRoute(prevodId: Int) = "transactions_edit_screen/$prevodId"

}

object InvestmentScreens {
    const val EditInvestmentScreenRoute = "edit_investment_screen/{investmentId}"
    fun editInvestmentRoute(investmentId: Int) = "edit_investment_screen/$investmentId"
}
