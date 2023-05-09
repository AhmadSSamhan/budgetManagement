package com.android.task.budgetmanagement.ui.update_account

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.task.budgetmanagement.data.database.entity.AccountEntity
import com.android.task.budgetmanagement.domain.usecase.AccountUpdateUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UpdateAccountViewModel @Inject constructor(
    private val repository: AccountUpdateUseCase
) : ViewModel() {

    fun updateAccount(account: AccountEntity) = viewModelScope.launch {
        repository.updateAccount(account)
    }

    fun deleteAccount(account: AccountEntity) = viewModelScope.launch {
        repository.deleteAccount(account)
    }

    fun getAccountId(id: Int) = repository.getAccountById(id)
}