package com.github.aekrylov.iamrich.repository.impl

import com.github.aekrylov.iamrich.domain.Balance
import com.github.aekrylov.iamrich.repository.BalanceRepository
import com.github.aekrylov.iamrich.repository.mapper.BalanceMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.springframework.stereotype.Component
import java.time.OffsetDateTime

@Component
class BalanceRepositoryImpl(private val mapper: BalanceMapper) : BalanceRepository {

    override suspend fun insert(dateTime: OffsetDateTime, balanceSatoshi: Long) = withContext(Dispatchers.IO) {
        mapper.insert(dateTime, balanceSatoshi)
    }

    override suspend fun getLatest(): Balance = withContext(Dispatchers.IO) {
        mapper.getLatest()
    }

    override suspend fun getInRange(startInclusive: OffsetDateTime, endInclusive: OffsetDateTime) = withContext(Dispatchers.IO) {
        mapper.getInRange(startInclusive, endInclusive)
    }
}