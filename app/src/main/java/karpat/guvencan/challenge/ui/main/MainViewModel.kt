package karpat.guvencan.challenge.ui.main

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import karpat.guvencan.challenge.common.Resource
import karpat.guvencan.challenge.common.Status
import karpat.guvencan.challenge.data.entitiy.Favourite
import karpat.guvencan.challenge.data.remote.Repo
import karpat.guvencan.challenge.data.repository.Repository
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn

@ExperimentalCoroutinesApi
class MainViewModel @ViewModelInject constructor(
    private val repository: Repository
) : ViewModel() {

    private val _repos = MutableLiveData<Resource<List<Repo>>>()
    val repos: LiveData<Resource<List<Repo>>>
        get() = _repos

    var lastJob: Job? = null


    fun getRepos(owner: String) {
        viewModelScope.launch {
            _repos.value = Resource.loading(null)

            if (lastJob != null)
                lastJob?.cancel()

            lastJob = launch {
                repository.getRepos(owner).combine(repository.getAllFav()) { resource, favs ->
                    if (resource.status == Status.SUCCESS) {
                        Resource.success(resource.data?.map { c ->
                            val copiedRepo = c.copy()
                            copiedRepo.fav =
                                favs.firstOrNull { fav -> fav.number == copiedRepo.id } != null
                            copiedRepo
                        })
                    } else {
                        resource
                    }
                }.collect {
                    _repos.value = it
                }

            }
        }
    }


    fun setFavourite(repo: Repo) {
        val fav = Favourite(repo.id)
        viewModelScope.launch {
            if (repo.fav) {
                repository.deleteFavourite(fav)
            } else {
                repository.addFavourite(fav)
            }
        }
    }


}