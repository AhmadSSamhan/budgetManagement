package com.android.task.budgetmanagement.present

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.android.task.budgetmanagement.data.prefs.AppSharedPreferences
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val sharedPreferences: AppSharedPreferences,
    app: Application,
) : AndroidViewModel(app) {

    val getSelectedTheme = sharedPreferences.themeId
}