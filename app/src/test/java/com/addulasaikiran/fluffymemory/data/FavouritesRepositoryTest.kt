package com.addulasaikiran.fluffymemory.data

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import android.content.Context
import com.addulasaikiran.fluffymemory.data.local.DialerDatabase
import com.addulasaikiran.fluffymemory.data.repository.FavouritesRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertThrows
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class FavouritesRepositoryTest {
    private lateinit var database: DialerDatabase
    private lateinit var repository: FavouritesRepository

    @Before
    fun setUp() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext<Context>(),
            DialerDatabase::class.java
        ).allowMainThreadQueries().build()
        repository = FavouritesRepository(database.favouriteDao())
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun favouriteReferencePersistsAndCanBeRemoved() = runBlocking {
        val id = repository.addContact("lookup-key-123")
        assertEquals("lookup-key-123", repository.observeFavourites().first().single().contactLookupKey)

        repository.remove(id)
        assertEquals(0, repository.observeFavourites().first().size)
    }

    @Test
    fun phoneNumberFavouritePersistsWithoutContactCopy() = runBlocking {
        repository.addNumber("+15551234567")
        val favourite = repository.observeFavourites().first().single()
        assertEquals("+15551234567", favourite.phoneNumber)
        assertEquals(null, favourite.contactLookupKey)
    }

    @Test
    fun blankReferencesAreRejected() {
        assertThrows(IllegalArgumentException::class.java) {
            runBlocking { repository.addContact(" ") }
        }
        assertThrows(IllegalArgumentException::class.java) {
            runBlocking { repository.addNumber("") }
        }
    }

    @Test
    fun databaseStartsAtVersionOneMigrationBaseline() {
        assertEquals(1, database.openHelper.readableDatabase.version)
    }
}
