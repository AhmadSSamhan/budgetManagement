package com.android.task.budgetmanagement.data.network

import android.app.Activity
import android.content.Context
import com.android.task.budgetmanagement.data.prefs.AppSharedPreferences
import com.android.task.budgetmanagement.utils.forceLogOut
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import retrofit2.Invocation
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.reflect.KClass


/**
 * Custom networking interceptor, handles:
 * - Authorization token
 * - X API key
 *
 * @see[AuthToken]
 * @see[XApiKey]
 */
@Singleton
class AuthorizationInterceptor @Inject constructor(
    private val preferences: AppSharedPreferences,
    @ApplicationContext private val applicationContext: Context? = null,
    private val refreshTokenFailureActivityClass: KClass<out Activity>? = null
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        val invocation = request.tag(Invocation::class.java)
        val apiMethod = invocation?.method()
        val requestBuilder = request.newBuilder()

        /***
         * Must not add any token header if token value is empty.
         * This will insure not running the authenticator upon unauthorized user with no valid credentials
         */
        if (apiMethod?.getAnnotation(AuthToken::class.java) != null) {
            try {
                runBlocking {
                    // If token has been saved, add it to the request
                    preferences.saveAuthToken?.let {
                        requestBuilder.addHeader(AuthToken.TOKEN_KEY, it.toBearerToken())
                        requestBuilder.addHeader(AuthToken.ACCEPT_KEY, HEADER_VALUE)
                    }
                }
            } catch (exception: AuthException) {
                if (exception.isForceLogOut) {
                    preferences.saveAuthToken = ""
                    executeForceLogOutFlow()
                }
            }
        }
        return chain.proceed(requestBuilder.build())
    }

    // Access token retrieval process produced an issue
    private fun executeForceLogOutFlow() {
        applicationContext.forceLogOut(refreshTokenFailureActivityClass)
    }

    companion object {
        const val HEADER_VALUE = "application/json"
    }
}