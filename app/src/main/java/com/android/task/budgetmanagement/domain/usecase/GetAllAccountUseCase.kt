package com.android.task.budgetmanagement.domain.usecase

import androidx.lifecycle.LiveData
import com.android.task.budgetmanagement.data.database.entity.AccountEntity
import com.android.task.budgetmanagement.domain.repository.AccountRepository
import javax.inject.Inject

class GetAllAccountUseCase @Inject constructor(private val accountRepository: AccountRepository) {

    fun getAccountById(id: Int): AccountEntity {
        return accountRepository.getAccount(id)
    }

    fun getAllAccount(): LiveData<List<AccountEntity>> {
        return accountRepository.getAllAccount()
    }
}