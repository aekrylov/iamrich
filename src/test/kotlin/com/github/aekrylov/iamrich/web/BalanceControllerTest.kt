package com.github.aekrylov.iamrich.web

import com.github.aekrylov.iamrich.service.BalanceService
import io.mockk.coEvery
import io.mockk.mockk
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.web.reactive.function.BodyInserters
import java.time.OffsetDateTime

@WebFluxTest(controllers = [BalanceController::class])
internal class BalanceControllerTest {

    @Autowired
    private lateinit var webClient: WebTestClient

    @ParameterizedTest
    @MethodSource("invalidData")
    fun `validates the request`(request: RequestDto) {
        webClient.post().uri("/balance/history")
                .body(BodyInserters.fromValue(request))
                .exchange()
                .expectStatus().isBadRequest
    }

    @Test
    fun `handles empty lists`() {
        request(RequestDto(OffsetDateTime.now().minusHours(1), OffsetDateTime.now()))
                .expectStatus().isOk
                .expectBody()
                .jsonPath("$").isArray
    }

    private fun request(request: RequestDto) = webClient.post().uri("/balance/history")
            .body(BodyInserters.fromValue(request))
            .exchange()

    data class RequestDto(
            val startDatetime: OffsetDateTime?,
            val endDatetime: OffsetDateTime?
    )

    companion object {
        @JvmStatic
        @Suppress("unused")
        fun invalidData() = listOf(
                Arguments.of(RequestDto(OffsetDateTime.now(), null)),
                Arguments.of(RequestDto(null, OffsetDateTime.now())),
                Arguments.of(RequestDto(null, null)),
                Arguments.of(RequestDto(OffsetDateTime.now(), OffsetDateTime.now().minusHours(1)))
        )
    }

    @Configuration
    class TestConfiguration {

        @Bean
        fun balanceService() = mockk<BalanceService> {
            coEvery {
                getBalancesForRange(any(), any())
            } returns listOf()
        }

        @Bean
        fun controller() = BalanceController(balanceService())
    }
}