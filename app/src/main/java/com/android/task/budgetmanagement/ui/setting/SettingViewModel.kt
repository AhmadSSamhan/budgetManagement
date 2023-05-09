package com.android.task.budgetmanagement.ui.setting

import android.app.Activity
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.android.task.budgetmanagement.data.prefs.AppSharedPreferences
import com.android.task.budgetmanagement.data.prefs.AppSharedPreferencesImpl.Companion.LANG_AR
import com.android.task.budgetmanagement.utils.RunTimeLocaleManager
import com.android.task.budgetmanagement.utils.restartApplication
import dagger.hilt.android.lifecycle.HiltViewModel
import java.lang.ref.WeakReference
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(
    private val sharedPreferences: AppSharedPreferences,
    app: Application,
) : AndroidViewModel(app) {


    fun saveUserLanguageAndRefreshApp(language: String, activity: WeakReference<Activity>?) {
        sharedPreferences.appLanguage = language
        activity?.get().let {
            RunTimeLocaleManager.overrideLocale(it)
            it.restartApplication()
            it?.finish()
        }
    }

    fun isSelectedLanguageArabic(): Boolean? {
        return sharedPreferences.appLanguage?.equals(LANG_AR, true)
    }

    val getSelectedTheme = sharedPreferences.themeId

    fun setSelectedTheme(themeId: Int) {
        sharedPreferences.themeId = themeId
    }
}