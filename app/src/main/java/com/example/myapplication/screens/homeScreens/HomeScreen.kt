package com.example.myapplication.screens.homeScreens


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.example.myapplication.R
import com.example.myapplication.screens.navigation.AppTopBar


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Home(
    modifier: Modifier = Modifier,
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            AppTopBar(
                title = stringResource(R.string.title_home),
                canNavigateBack = false,
                scrollBehavior = scrollBehavior
            )
        }) { innerPadding ->
        // Apply innerPadding to the content
        Statistika(contentPadding = innerPadding)
    }

}


@Composable
fun Statistika(
    contentPadding: PaddingValues = PaddingValues(0.dp),
) {
    val context = LocalContext.current
    val viewModel: HomeViewModel =
        ViewModelProvider(context as ViewModelStoreOwner).get(HomeViewModel::class.java)

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(contentPadding),
        verticalArrangement = Arrangement.SpaceAround,
        horizontalAlignment = Alignment.CenterHorizontally

    ) {
        item {
            Text(
                text = stringResource(R.string.celkovy_prijem),
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.secondary
            )
            
            ObalStringDoKarty(text = viewModel.getCelkovyPrijem())
            
        }
        item {
            Text(
                text = stringResource(R.string.celkov_vydavky),
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.secondary
            )

            ObalStringDoKarty(text = viewModel.getCelkoveVydavky(), true)
            
        }
        item {
            Text(
                text = stringResource(R.string.hodnota_akcii),
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.secondary
            )

            ObalStringDoKarty(text = viewModel.getHodnotaAkcii())

        }
        item {
            Text(
                text = stringResource(R.string.zisk_z_akcii),
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.secondary
            )

            ObalStringDoKarty(text = viewModel.getZiskZAkcii(), viewModel.jeZaporny(viewModel.ziskZAkcii))

        }
        item {
            Text(
                text = stringResource(R.string.hodnota_kryptomien),
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.secondary
            )

            ObalStringDoKarty(text = viewModel.getHodnotaKryptomien())

        }
        item {
            Text(
                text = stringResource(R.string.zisk_z_kryptomien),
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.secondary
            )


            ObalStringDoKarty(text = viewModel.getZiskZKryptomien(), (viewModel.jeZaporny(viewModel.ziskZKryptomien)))

        }
    }
}

@Composable
fun ObalStringDoKarty(
    text: String,
    zaporny: Boolean = false
) {
    if (!zaporny) {
        Card(
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                contentColor = MaterialTheme.colorScheme.onPrimaryContainer
            )
        ) {
            Text(
                modifier = Modifier.padding(
                    top = 12.dp,
                    start = 32.dp,
                    bottom = 12.dp,
                    end = 32.dp
                ),
                text = text,
                style = MaterialTheme.typography.headlineLarge
            )
        }
    } else {
        Card(
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.errorContainer,
                contentColor = MaterialTheme.colorScheme.onErrorContainer
            )
        ) {
            Text(
                modifier = Modifier.padding(
                    top = 12.dp,
                    start = 32.dp,
                    bottom = 12.dp,
                    end = 32.dp
                ),
                text = text,
                style = MaterialTheme.typography.headlineLarge
            )
        }
    }
}


