package com.addulasaikiran.fluffymemory.data.repository

import com.addulasaikiran.fluffymemory.data.local.FavouriteDao
import com.addulasaikiran.fluffymemory.data.local.FavouriteEntity
import kotlinx.coroutines.flow.Flow

class FavouritesRepository(
    private val favouriteDao: FavouriteDao
) {
    fun observeFavourites(): Flow<List<FavouriteEntity>> = favouriteDao.observeAll()

    suspend fun addContact(contactLookupKey: String): Long {
        require(contactLookupKey.isNotBlank()) { "Contact lookup key cannot be blank" }
        return favouriteDao.insert(
            FavouriteEntity(contactLookupKey = contactLookupKey)
        )
    }

    suspend fun addNumber(phoneNumber: String): Long {
        require(phoneNumber.isNotBlank()) { "Phone number cannot be blank" }
        return favouriteDao.insert(
            FavouriteEntity(phoneNumber = phoneNumber)
        )
    }

    suspend fun remove(id: Long) {
        favouriteDao.deleteById(id)
    }

    suspend fun reorder(id: Long, sortOrder: Int) {
        favouriteDao.updateSortOrder(id, sortOrder)
    }
}
