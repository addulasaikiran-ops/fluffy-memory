package com.addulasaikiran.fluffymemory.data

import androidx.datastore.core.PreferenceDataStoreFactory
import com.addulasaikiran.fluffymemory.data.preferences.UserPreferencesRepository
import com.addulasaikiran.fluffymemory.ui.theme.DialerThemeMode
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test
import java.io.File

class UserPreferencesRepositoryTest {
    @Test
    fun themeAndHapticsPreferencesPersist() = runBlocking {
        val file = File.createTempFile("dialer-preferences", ".preferences_pb")
        file.deleteOnExit()
        val store = PreferenceDataStoreFactory.create { file }
        val repository = UserPreferencesRepository(store)

        repository.setThemeMode(DialerThemeMode.DARK)
        repository.setHapticsEnabled(false)

        val saved = repository.preferences.first()
        assertEquals(DialerThemeMode.DARK, saved.themeMode)
        assertEquals(false, saved.hapticsEnabled)
    }

    @Test
    fun missingPreferencesUseSystemAndHapticsDefaults() = runBlocking {
        val file = File.createTempFile("dialer-defaults", ".preferences_pb")
        file.deleteOnExit()
        val store = PreferenceDataStoreFactory.create { file }
        val saved = UserPreferencesRepository(store).preferences.first()

        assertEquals(DialerThemeMode.SYSTEM, saved.themeMode)
        assertEquals(true, saved.hapticsEnabled)
    }
}
