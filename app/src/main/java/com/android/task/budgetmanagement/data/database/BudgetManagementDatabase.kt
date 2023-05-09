package com.android.task.budgetmanagement.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.android.task.budgetmanagement.data.database.dao.AccountDao
import com.android.task.budgetmanagement.data.database.dao.MonthYearDao
import com.android.task.budgetmanagement.data.database.dao.TransactionDao
import com.android.task.budgetmanagement.data.database.entity.AccountEntity
import com.android.task.budgetmanagement.data.database.entity.MonthYearEntity
import com.android.task.budgetmanagement.data.database.entity.TransactionEntity

@Database(
    entities = [AccountEntity::class, MonthYearEntity::class, TransactionEntity::class],
    version = 1
)
abstract class BudgetManagementDatabase : RoomDatabase() {
    abstract fun accountDao(): AccountDao
    abstract fun monthYearDao(): MonthYearDao
    abstract fun transactionDao(): TransactionDao
}