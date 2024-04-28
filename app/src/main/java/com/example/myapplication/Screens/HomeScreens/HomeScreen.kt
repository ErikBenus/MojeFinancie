package com.example.myapplication.Screens.HomeScreens

import android.view.View
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.example.compose.primaryLight
import com.example.myapplication.R


@Composable
fun Home(
    modifier: Modifier = Modifier,

    ) {
    val context = LocalContext.current
    val viewModel: HomeViewModel =
        ViewModelProvider(context as ViewModelStoreOwner).get(HomeViewModel::class.java)

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceAround,
        horizontalAlignment = Alignment.CenterHorizontally

    ) {
        item {
            Text(
                text = stringResource(R.string.celkovy_prijem),
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.secondary
            )

            Text(
                text = viewModel.getCelkovyPrijem(),
                modifier = modifier
                    .background(MaterialTheme.colorScheme.primary)
                    .padding(top = 12.dp, start = 32.dp, bottom = 12.dp, end = 32.dp),

                style = MaterialTheme.typography.headlineLarge,
                color = MaterialTheme.colorScheme.onPrimary
            )
        }
        item {
            Text(
                text = stringResource(R.string.celkov_vydavky),
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.secondary
            )

            Text(
                text = viewModel.getCelkoveVydavky(),
                modifier = modifier
                    .background(MaterialTheme.colorScheme.error)
                    .padding(top = 12.dp, start = 32.dp, bottom = 12.dp, end = 32.dp),

                style = MaterialTheme.typography.headlineLarge,
                color = MaterialTheme.colorScheme.onError
            )
        }
        item {
            Text(
                text = stringResource(R.string.hodnota_akcii),
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.secondary
            )

            Text(
                text = viewModel.getHodnotaAkcii(),
                modifier = modifier
                    .background(MaterialTheme.colorScheme.primary)
                    .padding(top = 12.dp, start = 32.dp, bottom = 12.dp, end = 32.dp),

                style = MaterialTheme.typography.headlineLarge,
                color = MaterialTheme.colorScheme.onPrimary
            )
        }
        item {
            Text(
                text = stringResource(R.string.zisk_z_akcii),
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.secondary
            )

            val colorZiskAkcie = if (viewModel.jeZaporny(viewModel.ziskZAkcii)) {
                MaterialTheme.colorScheme.error
            } else {
                MaterialTheme.colorScheme.primary
            }

            Text(
                text = viewModel.getZiskZAkcii(),
                modifier = modifier
                    .background(colorZiskAkcie)
                    .padding(top = 12.dp, start = 32.dp, bottom = 12.dp, end = 32.dp),

                style = MaterialTheme.typography.headlineLarge,
                color = MaterialTheme.colorScheme.onPrimary
            )
        }
        item {
            Text(
                text = stringResource(R.string.hodnota_kryptomien),
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.secondary
            )

            Text(
                text = viewModel.getHodnotaKryptomien(),
                modifier = modifier
                    .background(MaterialTheme.colorScheme.primary)
                    .padding(top = 12.dp, start = 32.dp, bottom = 12.dp, end = 32.dp),

                style = MaterialTheme.typography.headlineLarge,
                color = MaterialTheme.colorScheme.onPrimary
            )
        }
        item {
            Text(
                text = stringResource(R.string.zisk_z_kryptomien),
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.secondary
            )

            val colorZiskKryptomeny = if (viewModel.jeZaporny(viewModel.ziskZKryptomien)) {
                MaterialTheme.colorScheme.error
            } else {
                MaterialTheme.colorScheme.primary
            }

            Text(
                text = viewModel.getZiskZKryptomien(),
                modifier = modifier
                    .background(colorZiskKryptomeny)
                    .padding(top = 12.dp, start = 32.dp, bottom = 12.dp, end = 32.dp),

                style = MaterialTheme.typography.headlineLarge,
                color = MaterialTheme.colorScheme.onPrimary
            )
        }
    }
}