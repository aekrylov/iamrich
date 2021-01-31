package com.github.aekrylov.iamrich.web

import java.math.BigDecimal
import java.time.OffsetDateTime

data class BalanceResponse(
        val datetime: OffsetDateTime,
        val amount: BigDecimal
)