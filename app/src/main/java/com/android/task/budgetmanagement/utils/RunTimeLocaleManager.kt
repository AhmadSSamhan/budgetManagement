package com.android.task.budgetmanagement.utils

import android.content.Context
import android.content.SharedPreferences
import android.content.res.Configuration
import com.android.task.budgetmanagement.data.prefs.AppSharedPreferencesImpl
import com.android.task.budgetmanagement.data.prefs.AppSharedPreferencesImpl.Companion.KEY_PREF_APP_LANGUAGE
import com.android.task.budgetmanagement.data.prefs.AppSharedPreferencesImpl.Companion.LANG_EN
import com.android.task.budgetmanagement.data.prefs.AppSharedPreferencesImpl.Companion.PREFERENCE_FILE_NAME
import java.util.*

object RunTimeLocaleManager {

    fun wrapContext(context: Context?, isFromAttachBaseContext: Boolean = false): Context? {

        val savedLocale =
            getUserPrefsLocale(context, isFromAttachBaseContext)
        Locale.setDefault(savedLocale)

        val newConfig = context?.resources?.configuration ?: Configuration()
        newConfig.setLocale(savedLocale)

        return context?.createConfigurationContext(newConfig)
    }

    fun overrideLocale(context: Context?) {

        val savedLocale =
            getUserPrefsLocale(context, false)
        Locale.setDefault(savedLocale)

        val newConfig = context?.resources?.configuration ?: Configuration()
        newConfig.setLocale(savedLocale)

        newConfig.setLayoutDirection(savedLocale)
        context?.createConfigurationContext(newConfig)
        if (context != context?.applicationContext) {
            context?.applicationContext?.createConfigurationContext(newConfig)
            context?.resources?.updateConfiguration(newConfig, context.resources?.displayMetrics);
        }

    }

    private fun getUserPrefsLocale(context: Context?, isFromAttachBaseContext: Boolean): Locale {
        return if (isFromAttachBaseContext) {
            getLocaleFromRegularPrefs(context)
        } else {
            getLocaleFromEncryptedPrefs(context)
        }
    }

    private fun getLocaleFromRegularPrefs(context: Context?): Locale {
        var locale = Locale(LANG_EN)
        try {
            val prefs: SharedPreferences? =
                context?.getSharedPreferences(PREFERENCE_FILE_NAME, Context.MODE_PRIVATE)
            val userLanguage = prefs?.getString(KEY_PREF_APP_LANGUAGE, "")
            if (userLanguage?.isNotEmpty() == true) {
                locale = Locale(userLanguage)
            }
        } catch (e: Exception) {
            // no impl needed
        }
        return locale
    }

    private fun getLocaleFromEncryptedPrefs(context: Context?): Locale {
        var locale = Locale(LANG_EN)
        try {
            context?.let {
                val prefs = AppSharedPreferencesImpl(it)
                prefs.appLanguage?.let { lang ->
                    locale = Locale(lang)
                }
            }
        } catch (e: Exception) {
            // no impl needed
        }
        return locale
    }

}