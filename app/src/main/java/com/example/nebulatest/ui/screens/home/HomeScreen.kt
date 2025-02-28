package com.example.nebulatest.ui.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.nebulatest.R
import com.example.nebulatest.core.formatLongToDate
import com.example.nebulatest.features.transaction.model.TransactionType
import com.example.nebulatest.features.transaction.model.presentation.TransactionPresentationModel
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

        LazyColumn {
            items(
                items = uiState.transactions,
                key = { it.id }) {
                TransactionItem(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(2.dp),
                    transaction = it
                )
            }
        }
    }
}

@Composable
private fun TransactionItem(
    modifier: Modifier = Modifier,
    transaction: TransactionPresentationModel
) {
    Column(modifier = modifier.background(if (transaction.transactionType == TransactionType.INCOME) Color.Green else Color.Red)) {
        Text(text = transaction.amount.toString())
        transaction.category?.let { Text(text = it.name) }
        Text(text = formatLongToDate(transaction.timestamp))
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