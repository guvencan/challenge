package karpat.guvencan.challenge.data.remote

import karpat.guvencan.challenge.common.Resource
import karpat.guvencan.challenge.data.remote.ApiHelper.safeApiCall
import javax.inject.Inject

class RemoteData @Inject constructor(
    private val apiService: ApiService
) {

    suspend fun getRepos(owner: String): Resource<List<Repo>> {
        return safeApiCall(call = { apiService.getRepos(owner) })
    }

    suspend fun getRepoDetail(owner: String, name: String): Resource<Repo> {
        return safeApiCall(call = { apiService.getRepoDetail(owner, name) })
    }


}