package com.example.nebulatest.features.balance.data

import android.content.Context
import androidx.datastore.preferences.core.doublePreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.map
import org.koin.core.annotation.Singleton

private val Context.dataStore by preferencesDataStore("balance")

@Singleton
class BalanceLocalDataSource(context: Context) {

    companion object {
        private val BALANCE_KEY = doublePreferencesKey("user_balance")
    }

    private val dataStore = context.dataStore

    fun observeBalance() = dataStore.data.map { preferences -> preferences[BALANCE_KEY] ?: 0.0 }

    suspend fun updateBalance(newTransactionAmount: Double) {
        dataStore.edit { preferences ->
            preferences[BALANCE_KEY] = (preferences[BALANCE_KEY] ?: 0.0).plus(newTransactionAmount)
        }
    }
}