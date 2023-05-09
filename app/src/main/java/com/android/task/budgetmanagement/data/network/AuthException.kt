package com.android.task.budgetmanagement.data.network


internal const val CODE_DEFAULT = "500"
internal const val CODE_TOKENS_RETRIEVAL_FAILURE = "auth_tokens_retrieval_failure"
internal const val CODE_FORCE_LOGOUT = "500"


class AuthException(
    message: String,
    cause: Throwable? = null
) : RuntimeException(message, cause) {
    private var code: String = CODE_DEFAULT

    val isForceLogOut: Boolean
        get() = code == CODE_FORCE_LOGOUT

    val isTokensRetrievalFailure: Boolean
        get() = code == CODE_TOKENS_RETRIEVAL_FAILURE


}