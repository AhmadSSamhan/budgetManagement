package com.android.task.budgetmanagement.domain.usecase

import com.android.task.budgetmanagement.data.database.entity.MonthYearEntity
import com.android.task.budgetmanagement.domain.repository.MonthYearRepository
import javax.inject.Inject

class AddMonthUseCase @Inject constructor(private val repository: MonthYearRepository) {
    suspend fun insertMonthYear(monthYear: MonthYearEntity) {
        return repository.insertMonthYear(monthYear)
    }
}