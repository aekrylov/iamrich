package com.github.aekrylov.iamrich.domain

import java.math.BigDecimal
import java.time.OffsetDateTime

const val SATOSHI_IN_BTC = 100_000_000

data class Transaction(
        val id: Long,
        val date: OffsetDateTime,
        val amountSatoshi: Long
)

fun BigDecimal.toSatoshi(): Long = longValueExact() * SATOSHI_IN_BTC