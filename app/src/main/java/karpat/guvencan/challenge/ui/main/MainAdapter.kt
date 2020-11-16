package karpat.guvencan.challenge.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import karpat.guvencan.challenge.common.OnItemClickListener
import karpat.guvencan.challenge.data.remote.Repo
import karpat.guvencan.challenge.databinding.CellRepoBinding

class MainAdapter(private val onItemClickListener: OnItemClickListener) :
    ListAdapter<Repo, RecyclerView.ViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return CellViewHolder(
            CellRepoBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as CellViewHolder).bind(getItem(position))
    }

    inner class CellViewHolder(val binding: CellRepoBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(_repo: Repo) {
            with(binding) {
                listener = onItemClickListener
                repo = _repo
                executePendingBindings()
            }
        }
    }


    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Repo>() {
            override fun areItemsTheSame(oldItem: Repo, newItem: Repo): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Repo, newItem: Repo): Boolean =
                oldItem == newItem
        }
    }


}