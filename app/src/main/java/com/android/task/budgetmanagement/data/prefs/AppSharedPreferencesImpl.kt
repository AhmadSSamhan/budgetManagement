package com.android.task.budgetmanagement.data.prefs

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import dagger.hilt.android.qualifiers.ApplicationContext
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppSharedPreferencesImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : AppSharedPreferences {

    private var prefs: SharedPreferences? = null

    init {
        prefs = EncryptedSharedPreferences.create(
            PREFERENCE_FILE_NAME,
            MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC),
            context,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }

    override var saveAuthToken: String?
        get() = prefs?.getString(KEY_PREF_ACCOUNT_TOKEN, "")
        set(value) {
            putString(KEY_PREF_ACCOUNT_TOKEN, value)
        }
    override var accountId: String?
        get() = prefs?.getString(KEY_PREF_ACCOUNT_ID, "")
        set(value) {
            putString(KEY_PREF_ACCOUNT_ID, value)
        }

    override var appLanguage: String?
        get() = prefs?.getString(KEY_PREF_APP_LANGUAGE, getDefaultLocale())
        set(value) {
            saveUserLanguageToRegularPrefs(value)
            putString(KEY_PREF_APP_LANGUAGE, value)
        }

    override var themeId: Int?
        get() = prefs?.getInt(KEY_PREF_THEME_ID, LIGHT)
        set(value) {
            putInt(KEY_PREF_THEME_ID, value)
        }

    override fun removePref(key: String) {
        prefs?.edit()?.remove(key)?.apply()
    }

    private fun putString(key: String, value: String?) {
        prefs?.edit()?.putString(key, value)?.apply()
    }

    private fun putInt(key: String, value: Int?) {
        value?.let { prefs?.edit()?.putInt(key, it)?.apply() }
    }

    private fun putBoolean(key: String, value: Boolean?) {
        prefs?.edit()?.putBoolean(key, value == true)?.apply()
    }

    private fun getDefaultLocale(): String {
        return LANG_AR
    }

    private fun saveUserLanguageToRegularPrefs(userLanguage: String?) {
        try {
            userLanguage?.let {
                val normalPrefs: SharedPreferences = context.getSharedPreferences(
                    REGULAR_PREFERENCE_FILE_NAME,
                    Context.MODE_PRIVATE
                )
                normalPrefs.edit().putString(KEY_REGULAR_PREF_USER_LANGUAGE, userLanguage).apply()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


    companion object {

        const val PREFERENCE_FILE_NAME = "budget_management_app"
        const val REGULAR_PREFERENCE_FILE_NAME = "budget_management_app_regular"
        const val KEY_PREF_ACCOUNT_TOKEN = "account_token"
        const val KEY_PREF_ACCOUNT_ID = "account_id"
        const val KEY_PREF_APP_LANGUAGE = "pref_app_language"
        const val KEY_REGULAR_PREF_USER_LANGUAGE = "regular_pref_app_language"
        const val KEY_PREF_THEME_ID = "theme_id"
        private const val LIGHT = 0

        const val LANG_EN = "en"
        const val LANG_AR = "ar"

        @SuppressLint("StaticFieldLeak")// application context should solve memory leak
        @Volatile
        private var instance: AppSharedPreferencesImpl? = null

        @Synchronized
        fun getInstance(context: Context): AppSharedPreferencesImpl {
            if (instance == null) {
                instance = AppSharedPreferencesImpl(context)
            }
            return instance!!
        }
    }
}