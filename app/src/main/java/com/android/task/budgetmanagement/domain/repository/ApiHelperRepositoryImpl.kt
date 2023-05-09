package com.android.task.budgetmanagement.domain.repository

import com.android.task.budgetmanagement.data.api.ApiService
import com.android.task.budgetmanagement.data.model.AccountModel
import retrofit2.Response
import javax.inject.Inject

/**
 * Added a mock fun inside ApiHelperRepositoryImpl,
 * to know how you can implement your service,
 * the below fun connected with live example to use API service.
 */
class ApiHelperRepositoryImpl @Inject constructor(private val apiService: ApiService) : ApiHelperRepository {

    override suspend fun getAccount(): Response<List<AccountModel>> = apiService.getAccount()
}