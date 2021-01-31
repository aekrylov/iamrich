package com.github.aekrylov.iamrich.service.impl

import com.github.aekrylov.iamrich.domain.TransactionDto
import com.github.aekrylov.iamrich.domain.toSatoshi
import com.github.aekrylov.iamrich.repository.mapper.TransactionMapper
import com.github.aekrylov.iamrich.service.TransactionService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.springframework.stereotype.Component
import java.time.OffsetDateTime

@Component
class TransactionServiceImpl(private val mapper: TransactionMapper) : TransactionService {

    override suspend fun insert(data: TransactionDto) = withContext(Dispatchers.IO) {
        mapper.insert(data.date, data.amount.toSatoshi())
    }

    override suspend fun getByDateRange(dateStart: OffsetDateTime, dateEnd: OffsetDateTime?) =
            withContext(Dispatchers.IO) {
                mapper.getByDateRange(dateStart, dateEnd)
            }
}