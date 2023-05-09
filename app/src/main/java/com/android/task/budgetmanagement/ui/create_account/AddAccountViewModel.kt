package com.android.task.budgetmanagement.ui.create_account

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.task.budgetmanagement.data.database.entity.AccountEntity
import com.android.task.budgetmanagement.domain.usecase.AddAccountUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddAccountViewModel @Inject constructor(
    private val repository: AddAccountUseCase
) : ViewModel() {

    fun insertAccount(account: AccountEntity) = viewModelScope.launch {
        repository.saveAccount(account)
    }
}