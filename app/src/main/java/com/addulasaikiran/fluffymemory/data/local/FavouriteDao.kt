package com.addulasaikiran.fluffymemory.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface FavouriteDao {
    @Query("SELECT * FROM favourites ORDER BY sortOrder ASC, id ASC")
    fun observeAll(): Flow<List<FavouriteEntity>>

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(favourite: FavouriteEntity): Long

    @Query("DELETE FROM favourites WHERE id = :id")
    suspend fun deleteById(id: Long): Int

    @Query("UPDATE favourites SET sortOrder = :sortOrder WHERE id = :id")
    suspend fun updateSortOrder(id: Long, sortOrder: Int): Int
}
