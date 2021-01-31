package com.github.aekrylov.iamrich.repository.impl

import com.github.aekrylov.iamrich.domain.TransactionDto
import com.github.aekrylov.iamrich.domain.toSatoshi
import com.github.aekrylov.iamrich.repository.TransactionRepository
import com.github.aekrylov.iamrich.repository.mapper.TransactionMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.springframework.stereotype.Component
import java.time.OffsetDateTime

@Component
class TransactionRepositoryImpl(val mapper: TransactionMapper) : TransactionRepository {

    override suspend fun insert(data: TransactionDto) = withContext(Dispatchers.IO) {
        mapper.insert(data.datetime, data.amount.toSatoshi())
    }

    override suspend fun getTotalSatoshiForPeriod(dateStart: OffsetDateTime, dateEndExclusive: OffsetDateTime) =
            withContext(Dispatchers.IO) {
                mapper.getTotalSatoshiForPeriod(dateStart, dateEndExclusive)
            }
}