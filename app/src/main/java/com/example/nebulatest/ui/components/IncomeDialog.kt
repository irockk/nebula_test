package com.example.nebulatest.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import com.example.nebulatest.R
import com.example.nebulatest.core.Constants

@Composable
fun IncomeDialog(
    isVisible: Boolean,
    onDismiss: () -> Unit,
    onSave: (String) -> Unit
) {
    if (isVisible) {
        val amountState = remember { mutableStateOf(Constants.EMPTY_STRING) }

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
                TransactionTextButton(
                    text = stringResource(R.string.income_dialog_ok),
                    onClick = {
                        onSave(amountState.value)
                        onDismiss()
                    }
                )
            },
            dismissButton = {
                TransactionTextButton(
                    text = stringResource(R.string.income_dialog_cancel),
                    onClick = onDismiss
                )
            }
        )
    }
}