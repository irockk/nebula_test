package com.example.nebulatest.ui.components

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.launch

@Composable
fun SnackbarHandler(content: @Composable (SnackbarHostState, (String) -> Unit) -> Unit) {
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    content(snackbarHostState) { message ->
        coroutineScope.launch {
            snackbarHostState.showSnackbar(
                message = message
            )
        }
    }
}
