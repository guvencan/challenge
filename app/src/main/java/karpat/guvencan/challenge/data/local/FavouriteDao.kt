package karpat.guvencan.challenge.data.local

import androidx.room.*
import karpat.guvencan.challenge.data.entitiy.Favourite
import kotlinx.coroutines.flow.Flow

@Dao
interface FavouriteDao {

    @Query("SELECT * FROM favourite")
    fun getAll(): Flow<List<Favourite>>

    @Query("SELECT * FROM favourite WHERE number = :id")
    fun get(id: Int): Flow<Favourite>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun add(favourite: Favourite)

    @Delete
    suspend fun delete(favourite: Favourite)
}