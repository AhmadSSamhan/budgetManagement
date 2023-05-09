package com.android.task.budgetmanagement.data.network

/**
 * User specific identification authorization token.
 *
 * Annotate API that must contain authorization token with this annotation. Custom authentication
 * interceptor will fetch the token based on the existence of this annotation
 */
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FUNCTION)
annotation class AuthToken {

    companion object {
        const val TOKEN_KEY = "Authorization"
        const val ACCEPT_KEY = "Accept"
    }
}

/**
 * Add Bearer prefix to passed string token representation
 */
fun String.toBearerToken(): String {
    return "Bearer $this"
}