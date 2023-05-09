package com.android.task.budgetmanagement.domain.usecase

import com.android.task.budgetmanagement.data.database.entity.MonthYearEntity
import com.android.task.budgetmanagement.domain.repository.MonthYearRepository
import javax.inject.Inject

class UpdateMonthsUseCase @Inject constructor(private val repository: MonthYearRepository) {

    suspend fun executeDelete(monthYear: MonthYearEntity) {
        return repository.deleteMonthYear(monthYear)
    }

    suspend fun executeUpdate(monthYear: MonthYearEntity) {
        return repository.updateMonthYear(monthYear)
    }
}