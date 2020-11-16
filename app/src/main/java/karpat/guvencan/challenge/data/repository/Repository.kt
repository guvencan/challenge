package karpat.guvencan.challenge.data.repository

import karpat.guvencan.challenge.data.entitiy.Favourite
import karpat.guvencan.challenge.data.local.FavouriteDao
import karpat.guvencan.challenge.data.remote.ApiService
import karpat.guvencan.challenge.data.remote.RemoteData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class Repository @Inject constructor(
    private val remoteData: RemoteData,
    private val favouriteDao: FavouriteDao
) {

    suspend fun getRepos(owner: String) = flow { emit(remoteData.getRepos(owner)) }

    suspend fun getRepoDetail(owner: String, name: String) = flow { emit(remoteData.getRepoDetail(owner, name)) }

    fun getAllFav(): Flow<List<Favourite>> = favouriteDao.getAll()

    fun getSingleFav(id: Int): Flow<Favourite?> = favouriteDao.get(id)

    suspend fun addFavourite(favourite: Favourite) {
        favouriteDao.add(favourite)
    }

    suspend fun deleteFavourite(favourite: Favourite) {
        favouriteDao.delete(favourite)
    }

}