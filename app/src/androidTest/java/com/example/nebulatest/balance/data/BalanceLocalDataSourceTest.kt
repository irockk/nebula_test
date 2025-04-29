package com.example.nebulatest.balance.data

import android.content.Context
import androidx.datastore.preferences.core.doublePreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.nebulatest.features.balance.data.BalanceLocalDataSource
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

private val Context.testDataStore by preferencesDataStore("test_balance")

@RunWith(AndroidJUnit4::class)
class BalanceLocalDataSourceInstrumentedTest {

    private val balanceKey = doublePreferencesKey("user_balance")
    private lateinit var context: Context
    private lateinit var dataSource: BalanceLocalDataSource

    @Before
    fun setup() {
        context = ApplicationProvider.getApplicationContext()
        dataSource = BalanceLocalDataSource(context.testDataStore)
    }

    @Test
    fun observeBalanceReturnDefaultBalanceWhenNoValueIsSet() = runTest {
        val result = dataSource.observeBalance().first()
        assertEquals(0.0, result, 0.0)
    }

    @Test
    fun observeBalanceReturnStoredBalance() = runTest {
        val expectedBalance = 100.0
        context.testDataStore.edit { it[balanceKey] = expectedBalance }

        val result = dataSource.observeBalance().first()
        assertEquals(expectedBalance, result, 0.0)
    }

    @Test
    fun updateBalanceAddNewAmountToBalance() = runTest {
        val expectedAmount = 50.0
        dataSource.updateBalance(expectedAmount)

        val result = dataSource.observeBalance().first()
        assertEquals(expectedAmount, result, 0.0)
    }

    @After
    fun tearDown() = runTest {
        context.testDataStore.edit { it.clear() }
    }
}