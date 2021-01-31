package com.github.aekrylov.iamrich.service

import com.github.aekrylov.iamrich.domain.Balance
import com.github.aekrylov.iamrich.repository.BalanceRepository
import com.github.aekrylov.iamrich.repository.TransactionRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.fold
import kotlinx.coroutines.flow.map
import org.springframework.stereotype.Component
import java.time.OffsetDateTime

@Component
class BalanceService(
        private val balanceRepository: BalanceRepository,
        private val transactionRepository: TransactionRepository
) {

    companion object {
        const val BUFFER_SIZE = 20
    }

    /**
     * Get balances for each hour's end
     */
    suspend fun getBalancesForRange(dateStart: OffsetDateTime, dateEnd: OffsetDateTime): List<Balance> {
        //todo optimize this
        calculateBalances(dateEnd)

        return balanceRepository.getInRange(dateStart, dateEnd)
    }

    /**
     * Calculate balances up to a certain point and store them in the database
     */
    suspend fun calculateBalances(dateEnd: OffsetDateTime) {
        val latest = balanceRepository.getLatest()
        if (latest.date >= dateEnd) {
            return
        }

        coroutineScope {
            generateSequence(latest.date.plusHours(1)) { it.plusHours(1) }
                    .takeWhile { it <= dateEnd }
                    .asFlow()
                    .map {
                        async {
                            it to transactionRepository.getTotalSatoshiForPeriod(it.minusHours(1), it)
                        }
                    }
                    .buffer(BUFFER_SIZE)
                    .map { it.await() }
                    .fold(latest.balanceSatoshi) { acc, (date, hourTotal) ->
                        val balance = acc + hourTotal
                        balanceRepository.insert(date, balance)
                        balance
                    }

        }

    }
}
