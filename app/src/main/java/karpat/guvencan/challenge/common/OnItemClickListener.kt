package karpat.guvencan.challenge.common

import karpat.guvencan.challenge.data.remote.Repo

interface  OnItemClickListener {
    fun onClick(repo: Repo)
    fun onStarClick(repo: Repo)
}