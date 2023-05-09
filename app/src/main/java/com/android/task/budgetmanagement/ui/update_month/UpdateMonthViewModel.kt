package com.android.task.budgetmanagement.ui.update_month

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.task.budgetmanagement.data.database.entity.MonthYearEntity
import com.android.task.budgetmanagement.domain.usecase.UpdateMonthsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UpdateMonthViewModel @Inject constructor(
    private val repository: UpdateMonthsUseCase
) : ViewModel() {

    fun updateAccount(monthYear: MonthYearEntity) = viewModelScope.launch {
        repository.executeUpdate(monthYear)
    }

    fun deleteAccount(monthYear: MonthYearEntity) = viewModelScope.launch {
        repository.executeDelete(monthYear)
    }

}