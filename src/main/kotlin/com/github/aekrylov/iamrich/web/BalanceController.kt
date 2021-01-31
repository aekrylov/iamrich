package com.github.aekrylov.iamrich.web

import com.github.aekrylov.iamrich.domain.satoshiToBitcoin
import com.github.aekrylov.iamrich.service.BalanceService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
@RequestMapping("/balance")
class BalanceController(private val balanceService: BalanceService) {

    @PostMapping("/history")
    suspend fun getBalanceHistory(@Valid @RequestBody request: GetBalanceHistoryRequest) =
            balanceService.getBalancesForRange(request.startDatetime, request.endDatetime)
                    .map { BalanceResponse(it.date, it.balanceSatoshi.satoshiToBitcoin()) }
}