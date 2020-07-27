package com.rizkydian.internalmeet.di

import com.rizkydian.internalmeet.BuildConfig
import com.rizkydian.internalmeet.service.NotificationService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Suppress("unused")
@Module
@InstallIn(ApplicationComponent::class)
object NetworkModule {
    private fun provideHttpClient(): OkHttpClient {
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)
        return OkHttpClient.Builder()
            .addInterceptor {
                val req =
                    it.request().newBuilder()
                        .addHeader("Content-Type", "application/json").build()
                return@addInterceptor it.proceed(req)
            }
                .addInterceptor {
                val req =
                    it.request().newBuilder()
                        .addHeader("Authorization", "key=${BuildConfig.NOTIFICATION_TOKEN}").build()
                return@addInterceptor it.proceed(req)
            }
            .build()
    }

    private val retrofit = Retrofit.Builder()
        .baseUrl(BuildConfig.NOTIFICATION_URL)
        .addConverterFactory(MoshiConverterFactory.create())
        .client(provideHttpClient())
        .build()


    @Provides
    @Singleton
    fun provideNotificationService(): NotificationService {
        return retrofit.create(NotificationService::class.java)
    }
}