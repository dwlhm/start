package com.dwlhm.storage.prefs.onboarding

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import com.dwlhm.storage.prefs.PrefKeys
import kotlinx.coroutines.flow.first

private val Context.datastore by preferencesDataStore("onboarding")


class OnboardingPrefsImpl(private val context: Context) : OnboardingPrefs {
    override suspend fun hasOnBoarded(): Boolean {
        return context.datastore.data.first()[PrefKeys.HAS_ONBOARDED] ?: false
    }

    override suspend fun markOnBoarded() {
        context.datastore.edit {
            it[PrefKeys.HAS_ONBOARDED] = true
        }
    }
}