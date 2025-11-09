package com.dwlhm.storage.prefs.onboarding

interface OnboardingPrefs {
    suspend fun hasOnBoarded(): Boolean
    suspend fun markOnBoarded()
}