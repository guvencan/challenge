package karpat.guvencan.challenge.data.remote

import com.google.gson.annotations.SerializedName

data class Repo(
    var fav: Boolean = false,
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("full_name")
    val fullName: String,
    @SerializedName("description")
    val description: String?,
    @SerializedName("stargazers_count")
    val stars: String,
    @SerializedName("open_issues_count")
    val openIssues: String,
    @SerializedName("owner")
    val owner: Owner
)

data class Owner(
    @SerializedName("login")
    val login: String,
    @SerializedName("avatar_url")
    val avatar_url: String
)

