package com.android.task.budgetmanagement.domain.usecase

import com.android.task.budgetmanagement.data.database.entity.MonthYearEntity
import com.android.task.budgetmanagement.domain.repository.MonthYearRepository
import javax.inject.Inject

class GetAllMonthsUseCase @Inject constructor(private val repository: MonthYearRepository) {

    suspend fun getMonthYearsByAccountId(accountId: Int): List<MonthYearEntity> {
        return repository.getMonthYearsByAccountId(accountId)
    }
}