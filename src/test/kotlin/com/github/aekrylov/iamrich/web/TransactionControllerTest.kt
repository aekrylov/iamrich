package com.github.aekrylov.iamrich.web

import com.github.aekrylov.iamrich.service.TransactionService
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.just
import io.mockk.mockk
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.web.reactive.function.BodyInserters
import java.math.BigDecimal
import java.math.BigInteger
import java.time.OffsetDateTime

@WebFluxTest(controllers = [TransactionController::class])
internal class TransactionControllerTest {

    @Autowired
    private lateinit var webClient: WebTestClient

    @Test
    fun `returns success on valid data`() {
        val request = TestTransactionDto(OffsetDateTime.now(), 1.1.toBigDecimal())

        webClient.post().uri("/transaction")
                .body(BodyInserters.fromValue(request))
                .exchange()
                .expectStatus().isOk
    }

    @ParameterizedTest
    @MethodSource("invalidData")
    fun `does not allow invalid values`(request: TestTransactionDto) {
        webClient.post().uri("/transaction")
                .body(BodyInserters.fromValue(request))
                .exchange()
                .expectStatus().isBadRequest
    }

    data class TestTransactionDto(
            val date: OffsetDateTime?,
            val amount: BigDecimal?
    )

    companion object {

        @JvmStatic
        @Suppress("unused")
        fun invalidData() = listOf(
                Arguments.of(TestTransactionDto(OffsetDateTime.now(), null)),
                Arguments.of(TestTransactionDto(null, 1.0.toBigDecimal())),
                Arguments.of(TestTransactionDto(null, null)),
                Arguments.of(TestTransactionDto(OffsetDateTime.now(), (-1.0).toBigDecimal())),
        )
    }

    @Configuration
    class TestConfiguration {

        @Bean
        fun transactionService() = mockk<TransactionService> {
            coEvery {
                insert(any())
            } just Runs
        }

        @Bean
        fun transactionController() = TransactionController(transactionService())
    }
}