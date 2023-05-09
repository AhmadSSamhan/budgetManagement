package com.android.task.budgetmanagement.ui.transaction

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.task.budgetmanagement.data.database.entity.MonthYearEntity
import com.android.task.budgetmanagement.data.database.entity.TransactionEntity
import com.android.task.budgetmanagement.domain.usecase.TransactionUseCase
import com.android.task.budgetmanagement.domain.usecase.UpdateMonthsUseCase
import com.android.task.budgetmanagement.utils.Constants
import com.android.task.budgetmanagement.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TransactionViewModel @Inject constructor(
    private val transactionUseCase: TransactionUseCase,
    private val repository: UpdateMonthsUseCase
) : ViewModel() {

    private val _transactionsList = MutableSharedFlow<Resource<List<TransactionEntity>>>(replay = 1)
    val transactionsList: SharedFlow<Resource<List<TransactionEntity>>> =
        _transactionsList.asSharedFlow()


    fun loadTransactionsByMonthYearId(transactionId: String) {
        viewModelScope.launch {
            _transactionsList.emit(Resource.loading(null))
            transactionUseCase.getTransactionsByMonthYearId(transactionId).let { data ->
                if (data.isNotEmpty()) {
                    _transactionsList.emit(Resource.success(data))
                } else {
                    _transactionsList.emit(Resource.error("No Data Found", null))
                }
            }
        }
    }

    fun insertTransaction(transaction: TransactionEntity) =
        viewModelScope.launch {
            transactionUseCase.save(transaction)
        }

    fun updateTransaction(transaction: TransactionEntity) =
        viewModelScope.launch {
            transactionUseCase.updateTransaction(transaction)
        }

    fun deleteTransaction(transaction: TransactionEntity) = viewModelScope.launch {
        transactionUseCase.delete(transaction)
    }

    fun updateMonthAccount(monthYear: MonthYearEntity) = viewModelScope.launch {
        repository.executeUpdate(monthYear)
    }

    fun handleUpdateMonthTableByTransactionList(
        data: List<TransactionEntity>?,
        monthEntity: MonthYearEntity
    ) {
        var sumTotalDollar = 0.0
        var sumTotalDinar = 0.0
        var sumTotalSpendDollar = 0.0
        var sumTotalSpendDinar = 0.0

        data?.forEach { transactionEntity ->
            val amount = transactionEntity.total
            val isIncome = transactionEntity.isIncome
            val currency = transactionEntity.currency

            when {
                isIncome && currency.equals(Constants.CURRENCY_DOLLAR, true) -> {
                    sumTotalDollar += amount
                    monthEntity.incomeDollar = sumTotalDollar
                }
                isIncome -> {
                    sumTotalDinar += amount
                    monthEntity.incomeDinar = sumTotalDinar
                }
                currency.equals(Constants.CURRENCY_DOLLAR, true) -> {
                    sumTotalSpendDollar -= amount
                    monthEntity.spendingDollar = sumTotalSpendDollar
                }
                else -> {
                    sumTotalSpendDinar -= amount
                    monthEntity.spendingDinar = sumTotalSpendDinar
                }
            }
            monthEntity.currency = currency
        }
        updateMonthAccount(monthEntity)
    }

}