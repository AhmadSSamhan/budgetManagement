package com.android.task.budgetmanagement.domain.usecase

import com.android.task.budgetmanagement.data.database.entity.AccountEntity
import com.android.task.budgetmanagement.domain.repository.AccountRepository
import javax.inject.Inject

class AccountUpdateUseCase @Inject constructor(private val accountRepository: AccountRepository) {

    suspend fun updateAccount(entity: AccountEntity) {
        return accountRepository.updateAccount(entity)
    }

    suspend fun deleteAccount(entity: AccountEntity) {
        return accountRepository.deleteAccount(entity)
    }

    fun getAccountById(id: Int): AccountEntity {
        return accountRepository.getAccount(id)
    }
}