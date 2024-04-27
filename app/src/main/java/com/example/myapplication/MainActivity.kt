package com.example.myapplication

import android.os.Bundle
import android.service.autofill.OnClickAction
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.compose.AppTheme

import androidx.compose.ui.Alignment
import androidx.compose.ui.text.TextStyle
import com.example.compose.primaryLight


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting()
                }
            }
        }
    }
}



@Composable
fun Greeting(modifier: Modifier = Modifier) {

        Text(
            text = "Moje Financie",
            modifier = modifier
                .fillMaxWidth()
                .wrapContentWidth(align = Alignment.CenterHorizontally)
                .wrapContentHeight(align = Alignment.CenterVertically),
            //style = TextStyle(color = primaryLight),
            style = MaterialTheme.typography.headlineLarge,
            color = primaryLight
        )

        Text(
            text = "Moje Financie (Mensie)",
            modifier = modifier
                .fillMaxWidth()
                .padding(top = 60.dp)
                .wrapContentWidth(align = Alignment.CenterHorizontally)
                .wrapContentHeight(align = Alignment.CenterVertically),
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.secondary
        )

        Text(
            text = "Moje Financie (EsteMensie)",
            modifier = modifier
                .fillMaxWidth()
                .padding(top = 160.dp)
                .wrapContentWidth(align = Alignment.CenterHorizontally)
                .wrapContentHeight(align = Alignment.CenterVertically),
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.error
        )

        Text(
            text = "Moje Financie (Body-Large)",
            modifier = modifier
                .fillMaxWidth()
                .padding(top = 200.dp)
                .wrapContentWidth(align = Alignment.CenterHorizontally)
                .wrapContentHeight(align = Alignment.CenterVertically),
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.tertiary
        )

        Text(
            text = "Moje Financie (Body-Small)",
            modifier = modifier
                .fillMaxWidth()
                .padding(top = 240.dp)
                .wrapContentWidth(align = Alignment.CenterHorizontally)
                .wrapContentHeight(align = Alignment.CenterVertically),
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.outline
        )

        Text(
            text = "Moje Financie (Label Large)",
            modifier = modifier
                .fillMaxWidth()
                .padding(top = 280.dp)
                .wrapContentWidth(align = Alignment.CenterHorizontally)
                .wrapContentHeight(align = Alignment.CenterVertically),
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.onBackground
        )

        Text(
            text = "Moje Financie (Body-Small)",
            modifier = modifier
                .fillMaxWidth()
                .padding(top = 320.dp)
                .wrapContentWidth(align = Alignment.CenterHorizontally)
                .wrapContentHeight(align = Alignment.CenterVertically),
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.primaryContainer
        )
}


@Preview(showBackground = true)
@Composable
fun Preview() {
    AppTheme {
        Greeting()
    }
}