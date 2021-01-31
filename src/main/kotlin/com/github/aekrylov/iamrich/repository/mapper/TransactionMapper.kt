package com.github.aekrylov.iamrich.repository.mapper

import com.github.aekrylov.iamrich.domain.Transaction
import com.github.aekrylov.iamrich.domain.TransactionDto
import org.apache.ibatis.annotations.Mapper
import org.springframework.stereotype.Repository
import java.time.OffsetDateTime

@Repository
@Mapper
interface TransactionMapper {

    fun insert(date: OffsetDateTime, amountSatoshi: Long)

    fun getByDateRange(dateStart: OffsetDateTime, dateEnd: OffsetDateTime?): List<Transaction>

    @Deprecated("Must not be used outside tests")
    fun truncate()
}