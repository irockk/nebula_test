package com.example.nebulatest.transaction.data

import android.content.Context
import androidx.paging.PagingSource
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.nebulatest.features.transaction.data.local.TransactionDao
import com.example.nebulatest.features.transaction.data.local.TransactionDataBase
import com.example.nebulatest.features.transaction.data.local.model.TransactionEntity
import com.example.nebulatest.features.transaction.domain.model.TransactionCategory
import com.example.nebulatest.features.transaction.domain.model.TransactionType
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class TransactionDaoTest {

    private lateinit var database: TransactionDataBase
    private lateinit var transactionDao: TransactionDao

    @Before
    fun setupDatabase() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        database = Room.inMemoryDatabaseBuilder(
            context, TransactionDataBase::class.java
        ).build()
        transactionDao = database.transactionDao()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun insert_transaction_and_saves_data_correctly() = runTest {
        val transaction = TransactionEntity(
            id = 1,
            amount = 100.0,
            category = TransactionCategory.TAXI,
            transactionType = TransactionType.INCOME,
            timestamp = 1000L
        )
        transactionDao.insertTransaction(transaction)

        advanceUntilIdle()

        val pager = transactionDao.getTransactionsPaged()
        val result = pager.load(PagingSource.LoadParams.Refresh(0, 100, false))
        val data = (result as PagingSource.LoadResult.Page).data
        assert(data.contains(transaction))
    }

    @Test
    fun getTransactionsPaged_returns_data_in_descending_timestamp_order() = runTest {
        val transactions = listOf(
            TransactionEntity(
                1,
                100.0,
                TransactionCategory.GROCERIES,
                TransactionType.EXPENSE,
                timestamp = 1000L
            ),
            TransactionEntity(
                2,
                200.0,
                TransactionCategory.TAXI,
                TransactionType.EXPENSE,
                timestamp = 2000L
            ),
            TransactionEntity(
                3,
                300.0,
                TransactionCategory.OTHER,
                TransactionType.INCOME,
                timestamp = 3000L
            )
        )

        transactions.forEach { transactionDao.insertTransaction(it) }

        val pagingSource = transactionDao.getTransactionsPaged()

        val result = pagingSource.load(
            PagingSource.LoadParams.Refresh(
                key = null,
                loadSize = 10,
                placeholdersEnabled = false
            )
        )

        val expected = listOf(transactions[2], transactions[1], transactions[0])

        assertTrue(result is PagingSource.LoadResult.Page)
        val page = result as PagingSource.LoadResult.Page
        assertEquals(expected, page.data)
    }

    @After
    fun tearDown() {
        database.close()
    }
}