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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.nebulatest.R
import com.example.nebulatest.core.formatDouble
import com.example.nebulatest.core.formatLongToTime
import com.example.nebulatest.features.transaction.model.TransactionType
import com.example.nebulatest.features.transaction.model.presentation.TransactionPresentationModel
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
        .padding(12.dp),
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

        LazyColumn {
            items(transactions.itemCount) { index ->
                transactions[index]?.let { transaction ->
                    TransactionItem(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
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
            .border(2.dp, Color.DarkGray, RoundedCornerShape(6.dp))
            .background(
                (if (transaction.transactionType == TransactionType.INCOME)
                    Color.Green else Color.Red).copy(0.1f)
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

@Composable
private fun IncomeDialog(
    isVisible: Boolean,
    onDismiss: () -> Unit,
    onSave: (Double) -> Unit
) {
    if (isVisible) {
        val amountState = remember { mutableStateOf("") }

        AlertDialog(
            onDismissRequest = { onDismiss() },
            title = { Text(stringResource(R.string.income_dialog_title)) },
            text = {
                TextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = amountState.value,
                    onValueChange = { amountState.value = it },
                    label = { Text(stringResource(R.string.income_dialog_currency)) },
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                )
            },
            confirmButton = {
                Button(
                    onClick = {
                        amountState.value.toDoubleOrNull()?.let { onSave(it) }
                        onDismiss()
                    }
                ) {
                    Text(stringResource(R.string.income_dialog_ok))
                }
            },
            dismissButton = {
                Button(onClick = { onDismiss() }) {
                    Text(stringResource(R.string.income_dialog_cancel))
                }
            }
        )
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