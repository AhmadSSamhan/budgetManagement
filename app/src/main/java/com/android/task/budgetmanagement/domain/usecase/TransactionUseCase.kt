package com.android.task.budgetmanagement.domain.usecase

import com.android.task.budgetmanagement.data.database.entity.TransactionEntity
import com.android.task.budgetmanagement.data.database.entity.TransactionWithSets
import com.android.task.budgetmanagement.domain.repository.TransactionRepository
import javax.inject.Inject

/**
 * You can use this UseCase inside your viewModel to get your data ,
 * the below fun connected with live example to use API service.
 */

class TransactionUseCase @Inject constructor(private val repo: TransactionRepository) {

    suspend fun transactionEntityList(transactionId: String): List<TransactionWithSets> {
        return repo.transactionEntityList(transactionId)
    }

    suspend fun getTransactionsByMonthYearId(transactionId: String): List<TransactionEntity> {
        return repo.getTransactionsByMonthYearId(transactionId.toLong())
    }

    suspend fun save(transaction: TransactionEntity) {
        return repo.saveAccount(transaction)
    }

    suspend fun updateTransaction(transaction: TransactionEntity) {
        return repo.updateTransaction(transaction)
    }

    suspend fun delete(transaction: TransactionEntity) {
        return repo.deleteAccount(transaction)
    }

}