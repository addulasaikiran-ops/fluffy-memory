package com.addulasaikiran.fluffymemory.data.local

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

/**
 * Stores only the user's favourite reference, never a mirrored Contacts Provider record.
 * At least one of contactLookupKey or phoneNumber must be supplied by the repository.
 */
@Entity(
    tableName = "favourites",
    indices = [Index(value = ["contactLookupKey"]), Index(value = ["phoneNumber"])]
)
data class FavouriteEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val contactLookupKey: String? = null,
    val phoneNumber: String? = null,
    val sortOrder: Int = 0
)
