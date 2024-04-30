package com.example.myapplication.screens.financeScreens

import FinanceViewModel
import android.content.Context
import androidx.activity.ComponentActivity

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModelStoreOwner
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource

import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.myapplication.R
import com.example.myapplication.data.DatabazaPrevodov
import com.example.myapplication.data.Prevod
import com.example.myapplication.data.PrevodRepository
import com.example.myapplication.data.TypPrevodu
import com.example.myapplication.screens.homeScreens.HomeViewModel
import com.example.myapplication.screens.homeScreens.Statistika
import com.example.myapplication.screens.navigation.AppTopBar
import com.example.myapplication.screens.navigation.Screens
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FinanceScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            AppTopBar(
                title = stringResource(R.string.title_finance),
                canNavigateBack = false,
                scrollBehavior = scrollBehavior
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {navController.navigate(Screens.AddTransactionScreen.name) },
                modifier = Modifier.padding(20.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = stringResource(R.string.add_item)
                )
            }
        }) { innerPadding ->
        // Apply innerPadding to the content
        FinanceStastistika(contentPadding = innerPadding)
    }
}

@Composable
fun FinanceStastistika(
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp),
//    viewModel: FinanceViewModel = viewModel()
) {
    //    val context = LocalContext.current
//    val viewModel: FinanceViewModel =
//        ViewModelProvider(context as ViewModelStoreOwner).get(FinanceViewModel::class.java)

//    val prijmy by viewModel.prijmy.collectAsState(initial = emptyList())
//    val vydaje by viewModel.vydaje.collectAsState(initial = emptyList())
//    val celkovePrijmy by viewModel.celkovePrijmy.collectAsState(initial = 0.0)
//    val celkoveVydaje by viewModel.celkoveVydaje.collectAsState(initial = 0.0)

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(contentPadding),
        verticalArrangement = Arrangement.spacedBy(16.dp)
) {
//        FinanceItem("Príjmy:", celkovePrijmy, prijmy)
//        FinanceItem("Výdaje:", celkoveVydaje, vydaje)

    Text(
        text = "Príjmy:",
        style = MaterialTheme.typography.headlineMedium,
        color = MaterialTheme.colorScheme.primary,
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentWidth(Alignment.CenterHorizontally)
    )


    Text(
        text = "Výdaje:",
        style = MaterialTheme.typography.headlineMedium,
        color = MaterialTheme.colorScheme.error,
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentWidth(Alignment.CenterHorizontally)
    )
}
}

//@Composable
//private fun FinanceItem(
//    title: String,
//    total: Double,
//    items: List<Prevod>,
//) {
//    Column {
//        Text(
//            text = title,
//            style = MaterialTheme.typography.headlineMedium,
//            color = MaterialTheme.colorScheme.primary,
//            modifier = Modifier
//                .fillMaxWidth()
//        )
//
//        Text(
//            text = "Celková suma: $total",
//            style = MaterialTheme.typography.titleMedium,
//            color = MaterialTheme.colorScheme.secondary,
//            modifier = Modifier.fillMaxWidth()
//        )
//
//        items.forEach { prevod ->
//            Text(
//                text = "${prevod.nazov} - ${prevod.hodnota}",
//                style = MaterialTheme.typography.bodyMedium,
//                color = MaterialTheme.colorScheme.secondary,
//                modifier = Modifier.fillMaxWidth()
//            )
//        }
//    }
//}



//@Composable
//fun FinanceScreen(
//    modifier: Modifier = Modifier,
//
//    ) {
//    Column {
//        Text(
//            text = "Príjmy:",
//            style = MaterialTheme.typography.headlineMedium,
//            color = MaterialTheme.colorScheme.primary,
//            modifier = Modifier
//                .fillMaxWidth()
//                .wrapContentWidth(Alignment.CenterHorizontally)
//        )
//
//
//        Text(
//            text = "Výdaje:",
//            style = MaterialTheme.typography.headlineMedium,
//            color = MaterialTheme.colorScheme.error,
//            modifier = Modifier
//                .fillMaxWidth()
//                .wrapContentWidth(Alignment.CenterHorizontally)
//        )
//    }
//}