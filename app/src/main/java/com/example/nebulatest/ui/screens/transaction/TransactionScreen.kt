package com.example.nebulatest.ui.screens.transaction

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.nebulatest.R
import com.example.nebulatest.features.transaction.model.TransactionCategory
import com.example.nebulatest.ui.theme.NebulaTestTheme

@Composable
fun TransactionScreen(
    uiState: TransactionState,
    setSelectedCategory: (category: TransactionCategory) -> Unit,
    saveExpanse: (amount: Double) -> Unit,
    goBack: () -> Unit
) {
    val amountState = remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        Spacer(Modifier.weight(1f))

        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = amountState.value,
            onValueChange = { amountState.value = it },
            label = { Text(stringResource(R.string.income_dialog_currency)) },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
        )

        TransactionCategory.entries.forEach { category ->
            CategoryButton(
                category = category,
                setSelectedCategory = { setSelectedCategory(category) },
                isSelected = uiState.selectedCategory == category
            )
        }

        Spacer(Modifier.weight(1f))

        Row(modifier = Modifier.fillMaxWidth()) {
            TextButton(onClick = goBack) {
                Text(text = stringResource(R.string.transaction_goback_button_text))
            }

            Spacer(Modifier.weight(1f))

            TextButton(
                onClick = {
                    amountState.value.toDoubleOrNull()?.let { saveExpanse(it) }
                    goBack()
                },
                colors = ButtonDefaults.textButtonColors(
                    containerColor = Color.Blue,
                    contentColor = Color.White
                )
            ) {
                Text(text = stringResource(R.string.transition_add_transition_button_text))
            }
        }
    }
}

@Composable
private fun CategoryButton(
    modifier: Modifier = Modifier,
    category: TransactionCategory,
    setSelectedCategory: () -> Unit,
    isSelected: Boolean
) {
    TextButton(
        modifier = modifier,
        onClick = setSelectedCategory,
        colors = ButtonDefaults.textButtonColors(
            containerColor = if (isSelected) Color.Blue else Color.LightGray,
            contentColor = if (isSelected) Color.White else Color.Black
        ),
        shape = RoundedCornerShape(12.dp)
    ) {
        Text(text = category.name)
    }
}

@Preview(showBackground = true)
@Composable
fun TransactionScreenPreview() {
    NebulaTestTheme {
        TransactionScreen(
            uiState = TransactionState(),
            setSelectedCategory = {},
            goBack = {},
            saveExpanse = {}
        )
    }
}