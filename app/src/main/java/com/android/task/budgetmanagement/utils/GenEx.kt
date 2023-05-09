package com.android.task.budgetmanagement.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import com.android.task.budgetmanagement.BuildConfig
import com.android.task.budgetmanagement.present.SplashActivity
import kotlin.reflect.KClass

const val EXTRA_ACTIVITY_FORCE_LOGOUT = "extra_networking_activity_force_logout"

fun Context?.forceLogOut(refreshTokenFailureActivityClass: KClass<out Activity>? = null) {
    refreshTokenFailureActivityClass?.let { activityClass ->
        this?.let {
            startActivity(
                Intent(it, activityClass.java)
                    .putExtra(EXTRA_ACTIVITY_FORCE_LOGOUT, true)
                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                    .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            )
        }
    }
}

fun getShortVersionName() = BuildConfig.VERSION_NAME.split("-")[0]

fun Context?.restartApplication() {
    this?.startActivity(
        Intent(this, SplashActivity::class.java).apply {
            // SingleTop launch mode should be used here
            //Intent.FLAG_ACTIVITY_NEW_TASK is used to be able to restart application from application context.
            flags =
                Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        })
}