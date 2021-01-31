package com.github.aekrylov.iamrich.service

import com.github.aekrylov.iamrich.domain.Balance
import com.github.aekrylov.iamrich.repository.BalanceRepository
import com.github.aekrylov.iamrich.repository.TransactionRepository
import io.mockk.*
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import java.time.OffsetDateTime
import java.time.temporal.ChronoUnit

internal class BalanceServiceTest {

    private val balanceRepository = mockk<BalanceRepository> {
        coEvery {
            insert(any(), any())
        } just Runs
    }
    private val transactionRepository = mockk<TransactionRepository>()

    private val service = BalanceService(balanceRepository, transactionRepository)

    private val now = OffsetDateTime.now().plusMinutes(1)
    private val lastPeriodEnd = now.truncatedTo(ChronoUnit.HOURS)

    @Test
    fun `calculateBalances works when everything is up to date`() = runBlocking {
        coEvery {
            balanceRepository.getLatest()
        } returns Balance(lastPeriodEnd.plusHours(1), 123)

        service.calculateBalances(now)

        coVerify(exactly = 0) {
            balanceRepository.insert(any(), any())
        }
    }

    @Test
    fun `calculateBalances works for multiple hours`() = runBlocking {
        coEvery {
            balanceRepository.getLatest()
        } returns Balance(lastPeriodEnd.minusHours(2), 123)

        coEvery {
            transactionRepository.getTotalSatoshiForPeriod(any(), any())
        } returns 100

        service.calculateBalances(now)

        coVerifySequence {
            transactionRepository.getTotalSatoshiForPeriod(lastPeriodEnd.minusHours(2), lastPeriodEnd.minusHours(1))
            transactionRepository.getTotalSatoshiForPeriod(lastPeriodEnd.minusHours(1), lastPeriodEnd)
        }

        coVerifyOrder {
            balanceRepository.insert(lastPeriodEnd.minusHours(1), 223)
            balanceRepository.insert(lastPeriodEnd, 323)
        }
    }
}