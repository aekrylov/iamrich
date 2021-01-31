package com.github.aekrylov.iamrich.domain

import java.math.BigDecimal
import java.time.OffsetDateTime

const val SATOSHI_IN_BTC = 100_000_000
const val SATOSHI_BTC_SCALE = 8

data class Transaction(
        val id: Long,
        val date: OffsetDateTime,
        val amountSatoshi: Long
)

fun BigDecimal.toSatoshi(): Long = this.scaleByPowerOfTen(SATOSHI_BTC_SCALE).longValueExact()

fun Long.satoshiToBitcoin(): BigDecimal = BigDecimal(this).scaleByPowerOfTen(-SATOSHI_BTC_SCALE)