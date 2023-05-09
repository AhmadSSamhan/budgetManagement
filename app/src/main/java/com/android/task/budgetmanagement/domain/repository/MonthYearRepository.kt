package com.android.task.budgetmanagement.domain.repository

import com.android.task.budgetmanagement.data.database.dao.MonthYearDao
import com.android.task.budgetmanagement.data.database.entity.MonthYearEntity
import javax.inject.Inject

class MonthYearRepository @Inject constructor(private val dao: MonthYearDao) {

    suspend fun getMonthYearsByAccountId(accountId: Int) =
        dao.getByAccountId(accountId.toString())

    suspend fun insertMonthYear(monthYear: MonthYearEntity) =
        dao.insertMonthYear(monthYear)

    suspend fun updateMonthYear(monthYear: MonthYearEntity) =
        dao.updateMonthYear(monthYear)

    suspend fun deleteMonthYear(monthYear: MonthYearEntity) = dao.deleteMonthYear(monthYear)

}
