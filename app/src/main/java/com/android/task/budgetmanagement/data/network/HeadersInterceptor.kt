package com.android.task.budgetmanagement.data.network

import com.android.task.budgetmanagement.data.prefs.AppSharedPreferences
import com.android.task.budgetmanagement.utils.HEADER_LANGUAGE
import com.android.task.budgetmanagement.utils.HEADER_USER_AGENT
import com.android.task.budgetmanagement.utils.HEADER_USER_AGENT_VALUE
import com.android.task.budgetmanagement.utils.HEADER_X_BUILD_VERSION
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class HeadersInterceptor @Inject constructor(
    private val preferences: AppSharedPreferences,
    private val versionName: String
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val requestBuilder = request.newBuilder()

        preferences.let { lang ->
            requestBuilder.addHeader(HEADER_LANGUAGE, "${lang.appLanguage}")
        }
        requestBuilder.addHeader(HEADER_USER_AGENT, HEADER_USER_AGENT_VALUE)
        requestBuilder.addHeader(HEADER_X_BUILD_VERSION, versionName)

        return chain.proceed(requestBuilder.build())
    }
}