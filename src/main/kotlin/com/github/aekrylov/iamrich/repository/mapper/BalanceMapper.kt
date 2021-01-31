package com.github.aekrylov.iamrich.repository.mapper

import com.github.aekrylov.iamrich.domain.Balance
import org.apache.ibatis.annotations.Mapper
import org.springframework.stereotype.Repository
import java.time.OffsetDateTime

@Repository
@Mapper
interface BalanceMapper {

    fun insert(date: OffsetDateTime, balanceSatoshi: Long)

    fun getLatest(): Balance //todo nullability?

    fun getInRange(startInclusive: OffsetDateTime, endInclusive: OffsetDateTime): List<Balance>

    @Deprecated("Must not be used outside tests")
    fun truncate()
}