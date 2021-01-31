package com.github.aekrylov.iamrich.repository.mapper

import com.github.aekrylov.iamrich.domain.SATOSHI_IN_BTC
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.time.OffsetDateTime

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
        mapper.insert(OffsetDateTime.now(), 1)

        //todo check its inserted
    }

    @Test
    fun `insert works with big amounts`() {
        // total BTC supply
        val amount = 21_000_000L * SATOSHI_IN_BTC

        mapper.insert(OffsetDateTime.now(), amount)

        //todo check its inserted
    }
}