package com.example.nebulatest.ui.screens.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.nebulatest.R
import com.example.nebulatest.ui.theme.NebulaTestTheme

@Composable
fun HomeScreen(
    uiState: HomeState,
    goToTransaction: () -> Unit
) {
    Column(modifier = Modifier.fillMaxSize()) {
        Text(text = "Home")

        Text(text = uiState.exchangeRate.toString())

        TextButton(onClick = goToTransaction) {
            Text(text = stringResource(R.string.home_add_transaction_button_text))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    NebulaTestTheme {
        HomeScreen(
            uiState = HomeState(),
            goToTransaction = {}
        )
    }
}