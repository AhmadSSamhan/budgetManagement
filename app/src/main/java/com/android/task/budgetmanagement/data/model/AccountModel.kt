package com.android.task.budgetmanagement.data.model

import com.squareup.moshi.Json


data class AccountModel(
    @Json(name = "id")
    val id: Int = 0,
    @Json(name = "name")
    val name: String = "",
    @Json(name = "avatar")
    val avatar: String = ""
)