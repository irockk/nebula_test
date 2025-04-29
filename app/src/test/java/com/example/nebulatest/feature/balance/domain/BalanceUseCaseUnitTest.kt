package com.example.nebulatest.feature.balance.domain

import com.example.nebulatest.features.balance.data.BalanceLocalDataSource
import com.example.nebulatest.features.balance.domain.BalanceUseCase
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.verifyNoMoreInteractions
import org.mockito.kotlin.whenever

class BalanceUseCaseUnitTest {

    private lateinit var useCase: BalanceUseCase
    private val balanceLocalDataSource: BalanceLocalDataSource = mock()

    @BeforeEach
    fun setup() {
        useCase = BalanceUseCase(balanceLocalDataSource)
    }

    @Test
    fun `observe balance return flow test`() {
        val mockFlow = flowOf(100.0)
        whenever(balanceLocalDataSource.observeBalance()).thenReturn(mockFlow)

        val result = useCase.observeBalance()

        assert(result == mockFlow)
        verify(balanceLocalDataSource).observeBalance()
    }

    @Test
    fun `update balance test`() = runTest {
        val newTransactionAmount = 50.0

        useCase.updateBalance(newTransactionAmount)

        verify(balanceLocalDataSource).updateBalance(newTransactionAmount)
    }

    @AfterEach
    fun tearDown() {
       verifyNoMoreInteractions(balanceLocalDataSource)
    }
}