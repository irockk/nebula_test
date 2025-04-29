package com.example.nebulatest.features.home.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.nebulatest.R
import com.example.nebulatest.core.formatDoubleToString
import com.example.nebulatest.core.formatLongToDate
import com.example.nebulatest.features.transaction.presentation.components.TransactionItem
import com.example.nebulatest.ui.components.IncomeDialog
import com.example.nebulatest.ui.components.TransactionTextButton
import com.example.nebulatest.ui.theme.BackgroundLight
import com.example.nebulatest.ui.theme.BackgroundWhite
import com.example.nebulatest.ui.theme.Dimens
import com.example.nebulatest.ui.theme.NebulaTestTheme
import com.example.nebulatest.ui.theme.Typography

@Composable
fun HomeScreen(
    uiState: HomeState,
    goToTransaction: () -> Unit,
    addIncome: (amount: String) -> Unit
) {
    var isIncomeDialogVisible by remember { mutableStateOf(false) }

    IncomeDialog(
        isVisible = isIncomeDialogVisible,
        onDismiss = { isIncomeDialogVisible = false },
        onSave = { addIncome(it) }
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(Dimens.screenPadding)
    ) {
        Text(
            text = stringResource(R.string.balance_title),
            style = Typography.titleMedium
        )

        Text(
            text = stringResource(
                R.string.amount_in_btc,
                formatDoubleToString(uiState.balance)
            ),
            style = Typography.titleLarge
        )

        uiState.exchangeRate?.let { exchangeRate ->
            Text(
                text = stringResource(R.string.btc_to_usd_exchange_rate_text, exchangeRate),
                style = Typography.labelSmall
            )
        }

        Spacer(Modifier.height(Dimens.paddingBig))

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            TransactionTextButton(
                modifier = Modifier.weight(1f),
                text = stringResource(R.string.home_add_income_button_text),
                onClick = { isIncomeDialogVisible = true }
            )

            Spacer(Modifier.width(Dimens.paddingMedium))

            TransactionTextButton(
                modifier = Modifier.weight(1f),
                text = stringResource(R.string.home_add_transaction_button_text),
                onClick = goToTransaction
            )
        }

        Spacer(Modifier.height(Dimens.paddingBig))

        Text(
            modifier = Modifier.padding(vertical = Dimens.paddingSmall),
            text = stringResource(R.string.home_transactions),
            style = Typography.titleMedium
        )

        val transactions = uiState.transactions.collectAsLazyPagingItems()

        val groupedTransactions = transactions.itemSnapshotList.items.groupBy { transaction ->
            formatLongToDate(transaction.timestamp)
        }

        LazyColumn(
            modifier = Modifier
                .weight(2f)
                .clip(RoundedCornerShape(Dimens.cornerRadius))
                .background(BackgroundWhite)
                .padding(Dimens.paddingMedium)
        ) {
            groupedTransactions.forEach { (date, transactionsForDate) ->
                item {
                    Text(
                        modifier = Modifier.padding(top = Dimens.paddingExtraSmall),
                        text = date,
                        style = Typography.bodyMedium
                    )
                }

                itemsIndexed(transactionsForDate) { index, transaction ->
                    TransactionItem(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = Dimens.paddingExtraSmall),
                        transaction = transaction
                    )

                    if (index < transactionsForDate.size - 1) {
                        Box(
                            modifier = Modifier
                                .height(Dimens.borderWidth)
                                .fillMaxWidth()
                                .background(BackgroundLight)
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    NebulaTestTheme {
        HomeScreen(
            uiState = HomeState(balance = 100.0, exchangeRate = 840000.24),
            goToTransaction = {},
            addIncome = {}
        )
    }
}