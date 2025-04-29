package com.example.nebulatest.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.example.nebulatest.ui.theme.Dimens
import com.example.nebulatest.ui.theme.PrimaryBlue
import com.example.nebulatest.ui.theme.Typography

@Composable
fun TransactionTextButton(
    modifier: Modifier = Modifier,
    text: String,
    onClick: () -> Unit
) {
    TextButton(
        modifier = modifier.border(
            Dimens.borderWidth,
            PrimaryBlue,
            RoundedCornerShape(Dimens.cornerRadius)
        ),
        shape = RoundedCornerShape(Dimens.cornerRadius),
        onClick = onClick,
        colors = ButtonDefaults.textButtonColors(
            contentColor = PrimaryBlue,
            containerColor = Color.Transparent
        )
    ) {
        Text(
            text = text,
            style = Typography.bodyMedium.copy(color = PrimaryBlue)
        )
    }
}

@Preview
@Composable
fun TransactionTextButtonPreview() {
    TransactionTextButton(
        text = "Add Transaction",
        onClick = {}
    )
}