package karpat.guvencan.challenge.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import karpat.guvencan.challenge.data.entitiy.Favourite

@Database(
    entities = [Favourite::class],
    version = 1,
    exportSchema = false
)

abstract class AppDatabase : RoomDatabase() {

    abstract fun favDao(): FavouriteDao
}