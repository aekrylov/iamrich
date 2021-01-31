package com.github.aekrylov.iamrich.service

import com.github.aekrylov.iamrich.domain.Transaction
import com.github.aekrylov.iamrich.domain.TransactionDto
import java.time.OffsetDateTime

/**
 * @see com.github.aekrylov.iamrich.repository.mapper.TransactionMapper
 */
interface TransactionService {

    suspend fun insert(data: TransactionDto)

    /**
     * Returns a list of transactions between specified date
     *
     * @param dateStart start date, inclusive
     * @param dateEnd end date, exclusive, optional.
     *  If not specified then all transactions since [dateStart] are returned.
     */
    suspend fun getByDateRange(dateStart: OffsetDateTime, dateEnd: OffsetDateTime?): List<Transaction>
}