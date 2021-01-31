package com.github.aekrylov.iamrich.repository.mapper

import com.github.aekrylov.iamrich.domain.SATOSHI_IN_BTC
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.time.Duration
import java.time.OffsetDateTime
import kotlin.time.hours

@SpringBootTest
internal class TransactionMapperTest {

    @Autowired
    lateinit var mapper: TransactionMapper

    @BeforeEach
    fun setUp() {
        @Suppress("deprecation")
        mapper.truncate()
    }

    @Test
    fun `insert works`() {
        val now = OffsetDateTime.now()
        mapper.insert(now, 1)

        mapper.getTotalSatoshiForPeriod(now.minusHours(1), now.plusHours(1)) shouldBe 1
    }

    @Test
    fun `insert works with big amounts`() {
        val now = OffsetDateTime.now()
        // total BTC supply
        val amount = 21_000_000L * SATOSHI_IN_BTC

        mapper.insert(now, amount)

        mapper.getTotalSatoshiForPeriod(now.minusHours(1), now.plusHours(1)) shouldBe amount
    }

    @Test
    fun `getTotalSatoshiForPeriod does not include the right boundary`() {
        val now = OffsetDateTime.now()

        mapper.insert(now - Duration.ofMinutes(2), 100)
        mapper.insert(now - Duration.ofMinutes(1), 200)
        mapper.insert(now, 400)

        mapper.getTotalSatoshiForPeriod(now - Duration.ofMinutes(2), now) shouldBe 300
    }

    @Test
    fun `getTotalSatoshiForPeriod works when no transactions in period`() {
        val now = OffsetDateTime.now()

        mapper.getTotalSatoshiForPeriod(now - Duration.ofMinutes(1), now) shouldBe 0
    }
}