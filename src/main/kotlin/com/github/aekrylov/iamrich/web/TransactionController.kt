package com.github.aekrylov.iamrich.web

import com.github.aekrylov.iamrich.domain.TransactionDto
import com.github.aekrylov.iamrich.service.TransactionService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
@RequestMapping("/transaction")
class TransactionController(
        val transactionService: TransactionService
) {

    @PostMapping
    suspend fun insert(@RequestBody @Valid request: TransactionDto) {
        transactionService.insert(request)
    }
}