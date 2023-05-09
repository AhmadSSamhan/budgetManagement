package com.android.task.budgetmanagement.ui.add_month

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.task.budgetmanagement.data.database.entity.MonthYearEntity
import com.android.task.budgetmanagement.domain.usecase.AddMonthUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddMonthViewModel @Inject constructor(
    private val repository: AddMonthUseCase
) : ViewModel() {

    fun insertMonthYear(monthYear: MonthYearEntity) = viewModelScope.launch {
        repository.insertMonthYear(monthYear)
    }
}