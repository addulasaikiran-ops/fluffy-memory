package com.addulasaikiran.fluffymemory.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [FavouriteEntity::class],
    version = 1,
    exportSchema = true
)
abstract class DialerDatabase : RoomDatabase() {
    abstract fun favouriteDao(): FavouriteDao

    companion object {
        @Volatile private var instance: DialerDatabase? = null

        fun getInstance(context: Context): DialerDatabase =
            instance ?: synchronized(this) {
                instance ?: Room.databaseBuilder(
                    context.applicationContext,
                    DialerDatabase::class.java,
                    "fluffy-memory.db"
                ).build().also { instance = it }
            }
    }
}
