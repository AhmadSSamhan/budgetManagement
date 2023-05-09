package com.android.task.budgetmanagement.domain.usecase

import com.android.task.budgetmanagement.data.database.entity.AccountEntity
import com.android.task.budgetmanagement.domain.repository.AccountRepository
import javax.inject.Inject

class AddAccountUseCase @Inject constructor(private val accountRepository: AccountRepository) {

    suspend fun saveAccount(entity: AccountEntity) {
        return accountRepository.saveAccount(entity)
    }
}