package com.android.task.budgetmanagement.data.database.dao

import androidx.room.*
import com.android.task.budgetmanagement.data.database.entity.MonthYearEntity
import com.android.task.budgetmanagement.data.database.entity.TransactionEntity
import com.android.task.budgetmanagement.data.database.entity.TransactionWithSets

@Dao
interface TransactionDao {

    @Transaction
    @Query("SELECT * FROM month_year_table WHERE monthId = :transactionId")
    suspend fun getTransactionsByMonthYear(transactionId: String): List<TransactionWithSets>

    @Query("SELECT * FROM transaction_table WHERE monthId = :monthYearId")
    suspend fun getTransactionsByMonthYearId(monthYearId: Long): List<TransactionEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTransaction(transaction: TransactionEntity)

    @Delete
    suspend fun deleteTransaction(transaction: TransactionEntity)

    @Update
    suspend fun updateTransaction(transaction: TransactionEntity)
}
