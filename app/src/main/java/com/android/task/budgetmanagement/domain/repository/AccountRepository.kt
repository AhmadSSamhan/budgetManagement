package com.android.task.budgetmanagement.domain.repository

import com.android.task.budgetmanagement.data.database.dao.AccountDao
import com.android.task.budgetmanagement.data.database.entity.AccountEntity
import javax.inject.Inject

class AccountRepository @Inject constructor(private val dao: AccountDao) {
    suspend fun saveAccount(entity: AccountEntity) = dao.insertAccount(entity)
    suspend fun updateAccount(entity: AccountEntity) = dao.updateAccount(entity)
    suspend fun deleteAccount(entity: AccountEntity) = dao.deleteAccount(entity)
    fun getAccount(id: Int): AccountEntity = dao.getAccount(id)
    fun getAllAccount() = dao.getAllAccount()
}
