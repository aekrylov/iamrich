package com.github.aekrylov.iamrich.service

import com.github.aekrylov.iamrich.domain.TransactionDto
import com.github.aekrylov.iamrich.repository.TransactionRepository
import org.springframework.stereotype.Component
import java.time.OffsetDateTime

/**
 * @see com.github.aekrylov.iamrich.repository.mapper.TransactionMapper
 */
@Component
class TransactionService(private val repository: TransactionRepository) {

    suspend fun insert(data: TransactionDto) = repository.insert(data)

    suspend fun getTotalSatoshiForPeriod(dateStart: OffsetDateTime, dateEndExclusive: OffsetDateTime) =
            repository.getTotalSatoshiForPeriod(dateStart, dateEndExclusive)
}