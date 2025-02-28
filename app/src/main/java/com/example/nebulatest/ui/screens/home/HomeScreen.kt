package com.example.nebulatest.ui.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.nebulatest.R
import com.example.nebulatest.core.formatDouble
import com.example.nebulatest.core.formatLongToTime
import com.example.nebulatest.features.transaction.model.TransactionCategory
import com.example.nebulatest.features.transaction.model.TransactionType
import com.example.nebulatest.features.transaction.model.presentation.TransactionPresentationModel
import com.example.nebulatest.ui.theme.NebulaTestTheme

@Composable
fun HomeScreen(
    uiState: HomeState,
    goToTransaction: () -> Unit
) {
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(12.dp)) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            Text(text = uiState.exchangeRate.toString())
        }

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
                        .padding(vertical = 4.dp),
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
    Row(
        modifier = modifier
            .border(2.dp, Color.DarkGray, RoundedCornerShape(6.dp))
            .background(
                (if (transaction.transactionType == TransactionType.INCOME) Color.Green else Color.Red).copy(
                    0.1f
                )
            )
            .padding(12.dp)
    ) {
        Text(
            text = formatLongToTime(transaction.timestamp),
            color = Color.Gray,
            fontSize = 12.sp,
        )

        Spacer(Modifier.width(12.dp))

        Text(
            text = transaction.category?.name ?: stringResource(R.string.income_text),
            fontSize = 16.sp,
            color = Color.Black
        )

        Spacer(Modifier.weight(1f))

        Text(text = formatDouble(transaction.amount))
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    NebulaTestTheme {
        HomeScreen(
            uiState = HomeState(
                balance = 100, exchangeRate = 840000.24, transactions = listOf(
                    TransactionPresentationModel(
                        1, -1.03323, TransactionCategory.TAXI, TransactionType.EXPENSE
                    ), TransactionPresentationModel(
                        2, 1.03323, null, TransactionType.INCOME
                    )
                )
            ),
            goToTransaction = {}
        )
    }
}