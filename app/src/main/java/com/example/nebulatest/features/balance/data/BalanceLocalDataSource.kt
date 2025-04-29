package com.example.nebulatest.features.balance.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.doublePreferencesKey
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.map
import org.koin.core.annotation.Singleton

@Singleton
class BalanceLocalDataSource(private val dataStore: DataStore<Preferences>) {

    companion object {
        private val BALANCE_KEY = doublePreferencesKey("user_balance")
    }

    fun observeBalance() = dataStore.data.map { preferences -> preferences[BALANCE_KEY] ?: 0.0 }

    suspend fun updateBalance(newTransactionAmount: Double) {
        dataStore.edit { preferences ->
            preferences[BALANCE_KEY] = (preferences[BALANCE_KEY] ?: 0.0).plus(newTransactionAmount)
        }
    }
}