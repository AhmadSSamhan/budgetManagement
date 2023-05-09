package com.android.task.budgetmanagement.domain.repository

import com.android.task.budgetmanagement.data.model.AccountModel
import retrofit2.Response

/**
 * Added a mock fun inside ApiHelperRepository,
 * to know how you can implement your service,
 * the below fun connected with live example to use API service.
 */
interface ApiHelperRepository {

    suspend fun getAccount(): Response<List<AccountModel>>
}