package com.addulasaikiran.fluffymemory.data.preferences

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.addulasaikiran.fluffymemory.ui.theme.DialerThemeMode
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.userPreferencesDataStore: DataStore<Preferences> by preferencesDataStore(
    name = "user_preferences"
)

data class UserPreferences(
    val themeMode: DialerThemeMode = DialerThemeMode.SYSTEM,
    val hapticsEnabled: Boolean = true
)

class UserPreferencesRepository(
    private val dataStore: DataStore<Preferences>
) {
    val preferences: Flow<UserPreferences> = dataStore.data.map { values ->
        UserPreferences(
            themeMode = values[THEME_MODE]
                ?.let { stored -> DialerThemeMode.entries.firstOrNull { it.name == stored } }
                ?: DialerThemeMode.SYSTEM,
            hapticsEnabled = values[HAPTICS_ENABLED] ?: true
        )
    }

    suspend fun setThemeMode(mode: DialerThemeMode) {
        dataStore.edit { it[THEME_MODE] = mode.name }
    }

    suspend fun setHapticsEnabled(enabled: Boolean) {
        dataStore.edit { it[HAPTICS_ENABLED] = enabled }
    }

    companion object {
        private val THEME_MODE = stringPreferencesKey("theme_mode")
        private val HAPTICS_ENABLED = booleanPreferencesKey("haptics_enabled")

        fun from(context: Context): UserPreferencesRepository =
            UserPreferencesRepository(context.applicationContext.userPreferencesDataStore)
    }
}
