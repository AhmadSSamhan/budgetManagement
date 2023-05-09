package com.android.task.budgetmanagement.data.api

import com.android.task.budgetmanagement.data.model.AccountModel
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {

    /**
     * Added a mock api service from my side,
     * to know how you can implement your service,
     * the below fun connected with live example to use API service.
     */
    @GET("users")
    suspend fun getAccount(): Response<List<AccountModel>>
}