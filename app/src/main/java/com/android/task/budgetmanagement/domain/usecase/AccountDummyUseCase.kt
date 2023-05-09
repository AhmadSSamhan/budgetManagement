package com.android.task.budgetmanagement.domain.usecase

import com.android.task.budgetmanagement.data.model.AccountModel
import com.android.task.budgetmanagement.domain.repository.ApiHelperRepository
import retrofit2.Response
import javax.inject.Inject

/**
 * You can use this UseCase inside your viewModel to get your data ,
 * the below fun connected with live example to use API service.
 */

class AccountDummyUseCase @Inject constructor(private val apiHelperRepository: ApiHelperRepository) {

    suspend fun execute(): Response<List<AccountModel>> {
        return apiHelperRepository.getAccount()
    }
}