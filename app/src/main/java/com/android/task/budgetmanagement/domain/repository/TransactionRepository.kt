package com.android.task.budgetmanagement.domain.repository

import com.android.task.budgetmanagement.data.database.dao.TransactionDao
import com.android.task.budgetmanagement.data.database.entity.TransactionEntity
import javax.inject.Inject

class TransactionRepository @Inject constructor(private val dao: TransactionDao) {
    suspend fun saveAccount(transaction: TransactionEntity) = dao.insertTransaction(transaction)
    suspend fun deleteAccount(transaction: TransactionEntity) = dao.deleteTransaction(transaction)
    suspend fun updateTransaction(transaction: TransactionEntity) = dao.updateTransaction(transaction)
    suspend fun transactionEntityList(transactionId: String) = dao.getTransactionsByMonthYear(transactionId)
    suspend fun getTransactionsByMonthYearId(transactionId: Long) = dao.getTransactionsByMonthYearId(transactionId)
}
