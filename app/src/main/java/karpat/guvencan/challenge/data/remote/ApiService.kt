package karpat.guvencan.challenge.data.remote

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET("users/{owner}/repos")
    suspend fun getRepos(
        @Path("owner") user: String
    ): Response<List<Repo>>

    @GET("repos/{owner}/{repoName}")
    suspend fun getRepoDetail(
        @Path("owner") owner: String,
        @Path("repoName") repoName: String
    ): Response<Repo>
}