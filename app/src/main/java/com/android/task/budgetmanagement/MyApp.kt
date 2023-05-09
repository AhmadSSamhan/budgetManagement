package com.android.task.budgetmanagement

import android.app.Application
import android.content.Context
import android.content.res.Configuration
import com.android.task.budgetmanagement.utils.RunTimeLocaleManager
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyApp : Application() {
    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(RunTimeLocaleManager.wrapContext(base, true))
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        RunTimeLocaleManager.overrideLocale(this)
    }
}