package com.github.aekrylov.iamrich.domain

import java.math.BigDecimal
import java.time.OffsetDateTime
import javax.validation.constraints.NotNull
import javax.validation.constraints.Positive

data class TransactionDto(
        @field:NotNull
        val datetime: OffsetDateTime,

        @field:NotNull
        @field:Positive
        val amount: BigDecimal
)