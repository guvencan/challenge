package karpat.guvencan.challenge

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth
import io.mockk.*
import karpat.guvencan.challenge.data.remote.Owner
import karpat.guvencan.challenge.data.remote.Repo
import karpat.guvencan.challenge.data.repository.Repository
import karpat.guvencan.challenge.ui.main.MainViewModel
import karpat.guvencan.challenge.util.CoroutineTestRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import com.google.common.truth.Truth.assertThat
import junit.framework.Assert.assertEquals
import junit.framework.TestCase.assertEquals
import karpat.guvencan.challenge.common.Resource
import karpat.guvencan.challenge.common.Status
import karpat.guvencan.challenge.data.entitiy.Favourite
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import retrofit2.Response

@ExperimentalCoroutinesApi
open class MainViewModelTest {
    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    open val coroutineTestRule = CoroutineTestRule()

    private val repository: Repository = mockk()

    private lateinit var viewModel: MainViewModel

    val owner = "guvencan"
    val favourite = Favourite(number = 1)
    val dummy = createDummy()

    @Before
    fun setUp() {
        viewModel = MainViewModel(repository)
    }

    @Test
    fun `get repos list loading`() {
        //1- Mock calls
        coEvery { repository.getRepos(owner) } returns flow {
            val result =  Resource.loading(null)
        }
        coEvery { repository.getAllFav() } returns flow {
            val result =  Resource.loading(null)
        }

        //2-Call
        viewModel.getRepos(owner)
        viewModel.repos.observeForever { }

        //2-Verify
        Assert.assertTrue(viewModel.repos.value?.status == Status.LOADING)
    }

    private fun createDummy(): Repo {
        return Repo(
            name = "Challenge",
            owner = Owner(
                "guvencan",
                "avatar_image"
            ),
            description = "github",
            fullName = "guvencan karpat",
            id = 1,
            openIssues = "0",
            stars = "5"
        )
    }

}


private infix fun Any.returns(flow: Flow<Response<Any>>) {

}
