package com.github.aekrylov.iamrich.domain

import java.time.OffsetDateTime

data class Balance(
        val date: OffsetDateTime,
        val balanceSatoshi: Long
)