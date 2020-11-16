package karpat.guvencan.challenge.ui.detail

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import karpat.guvencan.challenge.common.Constants
import karpat.guvencan.challenge.common.Resource
import karpat.guvencan.challenge.common.Status
import karpat.guvencan.challenge.data.entitiy.Favourite
import karpat.guvencan.challenge.data.remote.Repo
import karpat.guvencan.challenge.data.repository.Repository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
class DetailViewModel @ViewModelInject constructor(
    private val repository: Repository,
    @Assisted private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _repo = MutableLiveData<Resource<Repo>>()
    val repo: LiveData<Resource<Repo>>
        get() = _repo

    init {
        val id = savedStateHandle.get<Int>(Constants.REPO_ID)
        val owner = savedStateHandle.get<String>(Constants.REPO_OWNER)
        val name = savedStateHandle.get<String>(Constants.REPO_NAME)
        if (id != null && owner != null && name != null) {
            getRepoDetail(id, owner, name)
        }
    }


    private fun getRepoDetail(id: Int, owner: String, name: String) {
        viewModelScope.launch {
            _repo.value = Resource.loading(null)
            repository.getRepoDetail(owner, name)
                .combine(repository.getSingleFav(id)) { resource, favs ->
                    if (resource.status == Status.SUCCESS) {
                        _repo.value = Resource.success(resource.data?.also { c ->
                            c.fav = (favs != null)
                        })
                    } else {
                        _repo.value = resource
                    }
                }.launchIn(viewModelScope)
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