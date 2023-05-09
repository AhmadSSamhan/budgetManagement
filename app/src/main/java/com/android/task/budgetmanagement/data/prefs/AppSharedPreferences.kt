package com.android.task.budgetmanagement.data.prefs

interface AppSharedPreferences {

    var saveAuthToken: String?
    var accountId: String?
    var appLanguage: String?
    var themeId: Int?
    fun removePref(key: String)
}