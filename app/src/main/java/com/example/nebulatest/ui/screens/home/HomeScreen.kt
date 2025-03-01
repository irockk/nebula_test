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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.nebulatest.R
import com.example.nebulatest.core.formatDouble
import com.example.nebulatest.core.formatLongToDate
import com.example.nebulatest.core.formatLongToTime
import com.example.nebulatest.features.transaction.model.TransactionType
import com.example.nebulatest.features.transaction.model.presentation.TransactionPresentationModel
import com.example.nebulatest.ui.screens.transaction.components.IncomeDialog
import com.example.nebulatest.ui.theme.Dimens
import com.example.nebulatest.ui.theme.NebulaTestTheme

@Composable
fun HomeScreen(
    uiState: HomeState,
    goToTransaction: () -> Unit,
    addIncome: (amount: Double) -> Unit
) {
    var isIncomeDialogVisible by remember { mutableStateOf(false) }

    IncomeDialog(
        isVisible = isIncomeDialogVisible,
        onDismiss = { isIncomeDialogVisible = false },
        onSave = { addIncome(it) }
    )

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(Dimens.screenPadding),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            Text(text = uiState.exchangeRate.toString())
        }

        Text(
            text = formatDouble(uiState.balance),
            fontSize = 42.sp
        )

        TextButton(onClick = { isIncomeDialogVisible = true }) {
            Text(text = stringResource(R.string.home_add_income_button_text))
        }

        TextButton(onClick = goToTransaction) {
            Text(text = stringResource(R.string.home_add_transaction_button_text))
        }

        val transactions = uiState.transactions.collectAsLazyPagingItems()

        val groupedTransactions = transactions.itemSnapshotList.items.groupBy { transaction ->
            formatLongToDate(transaction.timestamp)
        }

        LazyColumn {
            groupedTransactions.forEach { (date, transactionsForDate) ->
                item {
                    Text(
                        text = date
                    )
                }

                items(transactionsForDate) { transaction ->
                    TransactionItem(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = Dimens.paddingExtraSmall),
                        transaction = transaction
                    )
                }
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
            .border(Dimens.borderWidth, Color.DarkGray, RoundedCornerShape(Dimens.cornerRadius))
            .background(
                (if (transaction.transactionType == TransactionType.INCOME)
                    Color.Green else Color.Red).copy(0.1f)
            )
            .padding(Dimens.paddingMedium)
    ) {
        Text(
            text = formatLongToTime(transaction.timestamp),
            color = Color.Gray,
            fontSize = 12.sp,
        )

        Spacer(Modifier.width(Dimens.paddingMedium))

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
                balance = 100.0, exchangeRate = 840000.24
            ),
            goToTransaction = {},
            addIncome = {}
        )
    }
}