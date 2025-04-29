package com.example.nebulatest.features.transaction.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.nebulatest.R
import com.example.nebulatest.core.formatDoubleToString
import com.example.nebulatest.core.formatLongToTime
import com.example.nebulatest.features.transaction.domain.model.TransactionCategory
import com.example.nebulatest.features.transaction.domain.model.TransactionType
import com.example.nebulatest.features.transaction.presentation.model.TransactionPresentationModel
import com.example.nebulatest.features.transaction.presentation.model.toPresentation
import com.example.nebulatest.ui.theme.Dimens
import com.example.nebulatest.ui.theme.ExpenseRed
import com.example.nebulatest.ui.theme.IncomeGreen
import com.example.nebulatest.ui.theme.NebulaTestTheme
import com.example.nebulatest.ui.theme.Typography

@Composable
fun TransactionItem(
    modifier: Modifier = Modifier,
    transaction: TransactionPresentationModel
) {
    Row(
        modifier = modifier.padding(vertical = Dimens.paddingSmall),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Icon(
            modifier = Modifier
                .clip(
                    RoundedCornerShape(Dimens.cornerRadius)
                )
                .background(transaction.category.color)
                .padding(Dimens.paddingMedium)
                .size(Dimens.iconSize),
            imageVector = transaction.category.icon,
            contentDescription = null
        )

        Spacer(Modifier.width(Dimens.paddingMedium))

        Column {
            Text(
                text = stringResource(transaction.category.displayText),
                style = Typography.bodyMedium
            )

            Text(
                text = formatLongToTime(transaction.timestamp),
                style = Typography.labelSmall
            )
        }

        Spacer(Modifier.weight(1f))

        Text(
            text = stringResource(R.string.amount_in_btc, formatDoubleToString(transaction.amount)),
            color = if (transaction.amount > 0) IncomeGreen else ExpenseRed,
            style = Typography.bodyMedium
        )
    }
}

@Preview(showBackground = true)
@Composable
fun TransactionItemPreview() {
    NebulaTestTheme {
        TransactionItem(
            modifier = Modifier.fillMaxWidth(),
            transaction = TransactionPresentationModel(
                1,
                0.5,
                TransactionCategory.HEALTH.toPresentation(),
                TransactionType.EXPENSE,
                50009998789898900L
            )
        )
    }
}