package com.github.aekrylov.iamrich.service

import com.github.aekrylov.iamrich.domain.TransactionDto
import com.github.aekrylov.iamrich.repository.TransactionRepository
import org.springframework.stereotype.Component

/**
 * @see com.github.aekrylov.iamrich.repository.mapper.TransactionMapper
 */
@Component
class TransactionService(private val repository: TransactionRepository) {

    //todo handle old transactions for which balance is already calculated
    suspend fun insert(data: TransactionDto) = repository.insert(data)

}