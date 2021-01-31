package com.github.aekrylov.iamrich.repository.mapper

import com.github.aekrylov.iamrich.domain.Balance
import io.kotest.matchers.should
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.time.OffsetDateTime

@SpringBootTest
class BalanceMapperTest {

    @Autowired
    lateinit var mapper: BalanceMapper

    @BeforeEach
    fun setUp() {
        @Suppress("deprecation")
        mapper.truncate()
    }

    @Test
    fun `insert works`() {
        val balance = Balance(OffsetDateTime.now(), 123)

        mapper.insert(balance.date, balance.balanceSatoshi)

        mapper.getLatest() shouldBe balance
    }

    @Test
    fun `getInRange has correct borders`() {
        val date1 = OffsetDateTime.now().minusMinutes(1)
        val date2 = OffsetDateTime.now().plusMinutes(1)

        mapper.insert(date1, 123)
        mapper.insert(date2, 123)

        mapper.getInRange(date1, date2) should {
            it.size shouldBe 2
            it[0] shouldBe Balance(date1, 123)
            it[1] shouldBe Balance(date2, 123)
        }
    }
}