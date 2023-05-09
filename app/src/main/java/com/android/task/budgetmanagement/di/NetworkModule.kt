package com.android.task.budgetmanagement.di

import android.app.Application
import com.android.task.budgetmanagement.BuildConfig
import com.android.task.budgetmanagement.present.MainActivity
import com.android.task.budgetmanagement.domain.repository.ApiHelperRepository
import com.android.task.budgetmanagement.domain.repository.ApiHelperRepositoryImpl
import com.android.task.budgetmanagement.data.api.ApiService
import com.android.task.budgetmanagement.data.network.AuthorizationInterceptor
import com.android.task.budgetmanagement.data.network.HeadersInterceptor
import com.android.task.budgetmanagement.data.prefs.AppSharedPreferences
import com.android.task.budgetmanagement.utils.getShortVersionName
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private val loggingInterceptor =
        HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }

    @Provides
    fun provideBaseUrl() = BuildConfig.BASE_URL

    @Singleton
    @Provides
    fun provideOkHttpClient(
        authorizationInterceptor: AuthorizationInterceptor,
        preferences: AppSharedPreferences,
    ): OkHttpClient {
        val builder = OkHttpClient.Builder()
            .addInterceptor(HeadersInterceptor(preferences, getShortVersionName()))
            .addInterceptor(authorizationInterceptor)

        if (BuildConfig.DEBUG) {
            builder.addInterceptor(loggingInterceptor)
        }
        return builder.build()
    }

    @Singleton
    @Provides
    fun provideAuthorizationInterceptor(
        preferences: AppSharedPreferences,
        app: Application
    ): AuthorizationInterceptor {
        return AuthorizationInterceptor(
            preferences, app, MainActivity::class
        )
    }

    @Provides
    @Singleton
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
    ): Retrofit =
        Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create())
            .baseUrl(provideBaseUrl())
            .client(okHttpClient)
            .build()


    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService = retrofit.create(ApiService::class.java)

    @Provides
    @Singleton
    fun provideApiHelper(apiHelper: ApiHelperRepositoryImpl): ApiHelperRepository = apiHelper
}