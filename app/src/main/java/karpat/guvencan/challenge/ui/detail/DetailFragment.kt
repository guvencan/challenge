package karpat.guvencan.challenge.ui.detail

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import karpat.guvencan.challenge.R
import karpat.guvencan.challenge.common.Constants
import karpat.guvencan.challenge.common.Resource
import karpat.guvencan.challenge.common.Status
import karpat.guvencan.challenge.databinding.DetailFragmentBinding
import karpat.guvencan.challenge.extension.showToast
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class DetailFragment : Fragment(R.layout.detail_fragment) {

    private val viewModel: DetailViewModel by viewModels()
    private val binding: DetailFragmentBinding by viewBinding()

    companion object {
        fun newInstance(id: Int, owner: String, name: String): DetailFragment {
            return DetailFragment().apply {
                arguments = Bundle().apply {
                    putInt(Constants.REPO_ID, id)
                    putString(Constants.REPO_OWNER, owner)
                    putString(Constants.REPO_NAME, name)
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        viewModel.repo.observe(viewLifecycleOwner) {
            when (it.status) {
                Status.SUCCESS -> {
                    binding.repo = it.data
                    binding.viewState = it.status
                }
                Status.LOADING -> {
                    binding.viewState = it.status
                }
                Status.ERROR -> {
                    showToast(getString(R.string.error_message))
                }
            }
        }

    }

}


