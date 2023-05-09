package com.android.task.budgetmanagement.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.android.task.budgetmanagement.data.database.BudgetManagementDatabase
import com.android.task.budgetmanagement.data.database.entity.AccountEntity
import com.android.task.budgetmanagement.data.prefs.AppSharedPreferences
import com.android.task.budgetmanagement.data.prefs.AppSharedPreferencesImpl
import com.android.task.budgetmanagement.utils.Constants.BUDGET_MANAGEMENT_DATABASE
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DbModule {

    @Provides
    @Singleton
    fun provide(@ApplicationContext context: Context) = Room.databaseBuilder(
        context, BudgetManagementDatabase::class.java, BUDGET_MANAGEMENT_DATABASE)
        .allowMainThreadQueries()
        .fallbackToDestructiveMigration()
        .build()

    @Provides
    @Singleton
    fun provideDao(db: BudgetManagementDatabase) = db.accountDao()

    @Provides
    @Singleton
    fun provideMonthYearDao(db: BudgetManagementDatabase) = db.monthYearDao()


    @Provides
    @Singleton
    fun provideTransactionDao(db: BudgetManagementDatabase) = db.transactionDao()

    @Provides
    fun provideEntity() = AccountEntity()

    @Provides
    @Singleton
    fun provideAppSharedPreferences(app: Application): AppSharedPreferences {
        return AppSharedPreferencesImpl.getInstance(app.applicationContext)
    }
}