package com.example.myapplication.screens.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Star
import androidx.compose.ui.graphics.vector.ImageVector

data class NavItem(
    val label: String,
    val icon: ImageVector,
    val route: String
)

val listopfNavItems = listOf(
    NavItem(
        label = "Domov",
        icon = Icons.Default.Home,
        route = Screens.HomeScreen.name
    ),

    NavItem(
        label = "Financie",
        icon = Icons.Default.AddCircle,
        route = Screens.FinanceScreen.name
    ),

    NavItem(
        label = "Portfólio",
        icon = Icons.Default.ShoppingCart,
        route = Screens.StocksScreen.name
    ),

    NavItem(
        label = "Kryptomeny",
        icon = Icons.Default.Star,
        route = Screens.CryptoScreen.name
    ),
)