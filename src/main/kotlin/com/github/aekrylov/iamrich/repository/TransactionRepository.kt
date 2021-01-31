package com.github.aekrylov.iamrich.repository

import com.github.aekrylov.iamrich.domain.TransactionDto
import java.time.OffsetDateTime

interface TransactionRepository {

    suspend fun insert(data: TransactionDto)

    suspend fun getTotalSatoshiForPeriod(dateStart: OffsetDateTime, dateEndExclusive: OffsetDateTime): Long
}