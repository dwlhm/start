package com.dwlhm.storage.di

import android.content.Context
import com.dwlhm.storage.prefs.onboarding.OnboardingPrefs
import com.dwlhm.storage.prefs.onboarding.OnboardingPrefsImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataStoreModule {
    @Provides @Singleton
    fun provideOnboardingPrefs(@ApplicationContext context: Context) : OnboardingPrefs {
        return OnboardingPrefsImpl(context)
    }
}