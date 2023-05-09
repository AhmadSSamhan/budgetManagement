package com.android.task.budgetmanagement.data.database.dao

import androidx.room.*
import com.android.task.budgetmanagement.data.database.entity.MonthYearEntity
import com.android.task.budgetmanagement.utils.Constants.MONTH_YEAR_TABLE

@Dao
interface MonthYearDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMonthYear(monthYear: MonthYearEntity)

    @Update
    suspend fun updateMonthYear(monthYear: MonthYearEntity)

    @Delete
    suspend fun deleteMonthYear(monthYear: MonthYearEntity)

    @Transaction
    @Query("SELECT * FROM $MONTH_YEAR_TABLE WHERE accountId = :id")
    suspend fun getByAccountId(id: String): List<MonthYearEntity>
}