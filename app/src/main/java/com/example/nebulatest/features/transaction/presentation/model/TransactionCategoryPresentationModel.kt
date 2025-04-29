package com.example.nebulatest.features.transaction.presentation.model

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.DirectionsCar
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FlashOn
import androidx.compose.material.icons.filled.Flight
import androidx.compose.material.icons.filled.Movie
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.nebulatest.R
import com.example.nebulatest.features.transaction.domain.model.TransactionCategory
import com.example.nebulatest.ui.theme.EntertainmentPurple
import com.example.nebulatest.ui.theme.GroceriesGreen
import com.example.nebulatest.ui.theme.HealthPink
import com.example.nebulatest.ui.theme.RestaurantRed
import com.example.nebulatest.ui.theme.SalaryBlue
import com.example.nebulatest.ui.theme.TaxiYellow
import com.example.nebulatest.ui.theme.TravelCyan
import com.example.nebulatest.ui.theme.UtilitiesOrange

data class TransactionCategoryPresentationModel(
    val name: String?,
    @StringRes val displayText: Int,
    val icon: ImageVector,
    val color: Color
)

fun TransactionCategory?.toPresentation(): TransactionCategoryPresentationModel = when (this) {
    TransactionCategory.TAXI -> TransactionCategoryPresentationModel(
        this.name,
        R.string.category_taxi,
        Icons.Filled.DirectionsCar,
        TaxiYellow
    )

    TransactionCategory.GROCERIES -> TransactionCategoryPresentationModel(
        this.name,
        R.string.category_groceries,
        Icons.Filled.ShoppingCart,
        GroceriesGreen
    )

    TransactionCategory.RESTAURANT -> TransactionCategoryPresentationModel(
        this.name,
        R.string.category_restaurant,
        Icons.Filled.Restaurant,
        RestaurantRed
    )

    TransactionCategory.ENTERTAINMENT -> TransactionCategoryPresentationModel(
        this.name,
        R.string.category_entertainment,
        Icons.Filled.Movie,
        EntertainmentPurple
    )

    TransactionCategory.UTILITIES -> TransactionCategoryPresentationModel(
        this.name,
        R.string.category_utilities,
        Icons.Filled.FlashOn,
        UtilitiesOrange
    )

    TransactionCategory.TRAVEL -> TransactionCategoryPresentationModel(
        this.name,
        R.string.category_travel,
        Icons.Filled.Flight,
        TravelCyan
    )

    TransactionCategory.HEALTH -> TransactionCategoryPresentationModel(
        this.name,
        R.string.category_health,
        Icons.Filled.Favorite,
        HealthPink
    )

    else -> TransactionCategoryPresentationModel(
        null,
        R.string.category_income,
        Icons.Filled.AttachMoney,
        SalaryBlue
    )
}

fun TransactionCategoryPresentationModel.toDomain(): TransactionCategory {
    return TransactionCategory.valueOf(name ?: "")
}

fun getCategories(): List<TransactionCategoryPresentationModel> {
    return TransactionCategory.entries.map { it.toPresentation() }
}