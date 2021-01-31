package com.github.aekrylov.iamrich.web

import java.time.OffsetDateTime
import javax.validation.constraints.AssertTrue
import javax.validation.constraints.NotNull

data class GetBalanceHistoryRequest(
        @field:NotNull
        val startDatetime: OffsetDateTime,

        @field:NotNull
        val endDatetime: OffsetDateTime
) {

    @AssertTrue(message = "endDatetime time must not be before startDatetime")
    private val datesValid = endDatetime >= startDatetime //NB validation does not work when using a method
}