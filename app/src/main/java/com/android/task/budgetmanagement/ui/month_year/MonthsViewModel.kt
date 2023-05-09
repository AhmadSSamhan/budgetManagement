package com.android.task.budgetmanagement.ui.month_year

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.task.budgetmanagement.data.database.entity.AccountEntity
import com.android.task.budgetmanagement.data.database.entity.MonthYearEntity
import com.android.task.budgetmanagement.domain.usecase.AccountUpdateUseCase
import com.android.task.budgetmanagement.domain.usecase.GetAllMonthsUseCase
import com.android.task.budgetmanagement.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MonthsViewModel @Inject constructor(
    private val repository: GetAllMonthsUseCase,
    private val repositoryAccountByMonthTable: AccountUpdateUseCase
) : ViewModel() {

    private val _monthsList = MutableSharedFlow<Resource<List<MonthYearEntity>>>(replay = 1)
    val monthsList: SharedFlow<Resource<List<MonthYearEntity>>> = _monthsList.asSharedFlow()

    fun fetchMonthsByAccountId(accountId: Int) {
        viewModelScope.launch {
            _monthsList.emit(Resource.loading(null))
            repository.getMonthYearsByAccountId(accountId).let { months ->
                if (months.isNotEmpty()) {
                    _monthsList.emit(Resource.success(months))
                } else {
                    _monthsList.emit(Resource.error("No Data Found", null))
                }
            }
        }
    }

    fun updateAccount(account: AccountEntity, data: List<MonthYearEntity>?) =
        viewModelScope.launch {
            data?.forEach { monthYearEntity ->
                account.dollarTotal = monthYearEntity.spendingDollar + monthYearEntity.incomeDollar
                account.dinarTotal = monthYearEntity.spendingDinar + monthYearEntity.incomeDinar
            }
            repositoryAccountByMonthTable.updateAccount(account)
        }
}