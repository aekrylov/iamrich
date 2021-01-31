package com.github.aekrylov.iamrich.repository

import com.github.aekrylov.iamrich.domain.Balance
import java.time.OffsetDateTime

interface BalanceRepository {

    suspend fun insert(dateTime: OffsetDateTime, balanceSatoshi: Long)

    suspend fun getLatest(): Balance

    suspend fun getInRange(startInclusive: OffsetDateTime, endInclusive: OffsetDateTime): List<Balance>

}