package com.github.aekrylov.iamrich.repository.mapper

import org.apache.ibatis.annotations.Mapper
import org.springframework.stereotype.Repository
import java.time.OffsetDateTime

@Repository
@Mapper
interface TransactionMapper {

    fun insert(date: OffsetDateTime, amountSatoshi: Long)

    fun getTotalSatoshiForPeriod(dateStart: OffsetDateTime, dateEndExclusive: OffsetDateTime): Long

    @Deprecated("Must not be used outside tests")
    fun truncate()
}