package com.example.myapplication.screens.navigation

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.ExperimentalMaterial3Api


import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.navArgument
import com.example.myapplication.R
import com.example.myapplication.screens.financeScreens.AddTransactionScreen
import com.example.myapplication.screens.financeScreens.FinanceScreen
import com.example.myapplication.screens.financeScreens.TransactionsDetailsScreen
import com.example.myapplication.screens.financeScreens.TransactionsEditScreen
import com.example.myapplication.screens.homeScreens.Home
import com.example.myapplication.screens.portfolioScreens.AddCryptoScreen
import com.example.myapplication.screens.portfolioScreens.AddStockScreen
import com.example.myapplication.screens.portfolioScreens.CryptoScreen
import com.example.myapplication.screens.portfolioScreens.EditInvestmentScreen
import com.example.myapplication.screens.portfolioScreens.StocksScreen

/**
 * Navigačná zložka aplikácie, ktorá obsahuje hornú lištu a navigačný panel.
 * Vykresľuje rôzne obrazovky aplikácie v závislosti na aktuálnej destinácii.
 */
@Composable
fun AppNavigation() {
    val navController: NavHostController = rememberNavController()


    Scaffold(
        bottomBar = {
            NavigationBar {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination: NavDestination? = navBackStackEntry?.destination

                listofNavItems.forEach { navItem ->
                    NavigationBarItem(
                        selected = currentDestination?.hierarchy?.any { it.route == navItem.route } == true,
                        onClick = {
                            if (currentDestination?.route != navItem.route) {
                                navController.navigate(navItem.route) {
                                    if (navItem.route == Screens.HomeScreen.name) {
                                        popUpTo(navController.graph.findStartDestination().id) {
                                            inclusive = true
                                        }
                                    }
                                    launchSingleTop = true
                                }
                            }
                        },
                        icon = {
                            Icon(
                                imageVector = navItem.icon,
                                contentDescription = null
                            )
                        },
                        label = {
                            Text(text = navItem.label)
                        }
                    )
                }
            }
        }
    ) { paddingValues: PaddingValues ->
        NavHost(
            navController = navController,
            startDestination = Screens.HomeScreen.name,
            modifier = Modifier
                .padding(paddingValues)
        ) {
            composable(route = Screens.HomeScreen.name) {
                Home(navController = navController)
            }
            composable(route = Screens.FinanceScreen.name) {
                FinanceScreen(navController = navController)
            }
            composable(route = Screens.StocksScreen.name) {
                StocksScreen(navController = navController)
            }
            composable(route = Screens.CryptoScreen.name) {
                CryptoScreen(navController = navController)
            }
            composable(route = Screens.AddTransactionScreen.name) {
                AddTransactionScreen(navigateBack = { navController.navigateUp() }) //
            }
            composable(
                route = TransactionScreens.TransactionsDetailsScreenRoute,
                arguments = listOf(navArgument("isIncome") { type = NavType.BoolType })
            ) { backStackEntry ->
                val isIncome = backStackEntry.arguments?.getBoolean("isIncome") ?: false
                TransactionsDetailsScreen(
                    navigateBack = { navController.navigateUp() },
                    isIncome = isIncome,
                    navController = navController // pridane kvoli Editu
                )
            }
            composable(
                route = TransactionScreens.TransactionsEditScreenRoute,
                arguments = listOf(navArgument("prevodId") { type = NavType.IntType })
            ) {
                TransactionsEditScreen(
                    navigateBack = { navController.navigateUp() },
                )
            }
            composable(route = Screens.AddStockScreen.name) {
                AddStockScreen(navigateBack = { navController.navigateUp() }) //
            }
            composable(
                route = InvestmentScreens.EditInvestmentScreenRoute,
                arguments = listOf(navArgument("investmentId") { type = NavType.IntType })
            ) {
                EditInvestmentScreen(
                    navigateBack = { navController.navigateUp() }
                )
            }
            composable(route = Screens.AddCryptoScreen.name) {
                AddCryptoScreen(navigateBack = { navController.navigateUp() }) //
            }

        }
    }
}

/**
 * Horná lišta aplikácie s možnosťou navigácie späť a názvom obrazovky.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTopBar(
    title: String,
    canNavigateBack: Boolean,
    modifier: Modifier = Modifier,
    scrollBehavior: TopAppBarScrollBehavior? = null,
    navigateUp: () -> Unit = {}
) {
    Column {

        CenterAlignedTopAppBar(
            title = {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onPrimary,
                )
                    },
            modifier = modifier,
            scrollBehavior = scrollBehavior,
            colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                containerColor = MaterialTheme.colorScheme.primary,
                navigationIconContentColor = MaterialTheme.colorScheme.onPrimary,
                actionIconContentColor = MaterialTheme.colorScheme.onPrimary
            ),
            navigationIcon = {
                if (canNavigateBack) {
                    IconButton(onClick = navigateUp) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.back_button)
                        )
                    }
                }
            }
        )
        Divider(
            modifier = Modifier.fillMaxWidth(),
            color = MaterialTheme.colorScheme.secondary,
            thickness = 0.5.dp
        )
    }
}