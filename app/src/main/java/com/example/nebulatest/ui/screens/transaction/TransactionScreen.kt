package com.example.nebulatest.ui.screens.transaction

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.nebulatest.ui.theme.NebulaTestTheme

@Composable
fun TransactionScreen(goBack: () -> Unit) {
    Column(modifier = Modifier.fillMaxSize()) {
        Text(text = "transaction")
    }
}

@Preview(showBackground = true)
@Composable
fun TransactionScreenPreview() {
    NebulaTestTheme {
        TransactionScreen(goBack = {})
    }
}