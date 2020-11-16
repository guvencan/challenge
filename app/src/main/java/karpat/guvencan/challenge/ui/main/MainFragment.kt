package karpat.guvencan.challenge.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import karpat.guvencan.challenge.R
import karpat.guvencan.challenge.common.OnItemClickListener
import karpat.guvencan.challenge.common.Status
import karpat.guvencan.challenge.data.remote.Repo
import karpat.guvencan.challenge.databinding.MainFragmentBinding
import karpat.guvencan.challenge.extension.showToast
import karpat.guvencan.challenge.ui.detail.DetailFragment
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class MainFragment : Fragment(R.layout.main_fragment), OnItemClickListener {


    private val viewModel: MainViewModel by viewModels()
    private val binding: MainFragmentBinding by viewBinding()

    companion object {
        fun newInstance(): MainFragment = MainFragment()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.listener = this
        binding.lifecycleOwner = viewLifecycleOwner

        binding.submitButton.setOnClickListener {
            val owner = binding.editText.text.toString()
            if (owner.isNotEmpty()) {
                viewModel.getRepos(owner)
            }
        }

        viewModel.repos.observe(viewLifecycleOwner) {
            when (it.status) {
                Status.SUCCESS -> {
                    binding.data = it.data
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

    override fun onStarClick(repo: Repo) {
        viewModel.setFavourite(repo)
    }

    override fun onClick(repo: Repo) {
        activity?.supportFragmentManager?.beginTransaction()
            ?.add(R.id.container, DetailFragment.newInstance(repo.id, repo.owner.login, repo.name))
            ?.addToBackStack(DetailFragment::class.simpleName)?.commit()

    }


}