package karpat.guvencan.challenge.common

import android.view.View
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import karpat.guvencan.challenge.R
import karpat.guvencan.challenge.data.remote.Repo
import karpat.guvencan.challenge.ui.main.MainAdapter


object BindingAdapter {

    @JvmStatic
    @BindingAdapter("imageUrlCircle")
    fun imageUrlCirle(view: ImageView, url: String?) {
        Glide.with(view.context)
            .load(url)
            .apply(RequestOptions().circleCrop())
            .into(view)
    }

    @JvmStatic
    @BindingAdapter("starValue")
    fun startState(view: ImageView, flag: Boolean) {
        view.drawable.mutate()
        if(flag)
            view.drawable.setTint(ContextCompat.getColor(view.context, R.color.startActive))
        else
            view.drawable.setTint(ContextCompat.getColor(view.context, R.color.startDeactive))
    }

    @JvmStatic
    @BindingAdapter("visibleIf")
    fun changeVisibility(view: View, status: Status?) {
        view.visibility = if (Status.LOADING == status) View.VISIBLE else View.GONE
    }

    @JvmStatic
    @BindingAdapter(value = ["repos", "listener"], requireAll = false)
    fun setRepos(
        view: RecyclerView,
        repos: List<Repo>?,
        listener: OnItemClickListener?,
    ) {
        if(!repos.isNullOrEmpty() && listener != null){
            view.adapter?.let {
                (it as MainAdapter).submitList(repos)
            } ?: kotlin.run {
                view.adapter = MainAdapter(listener).apply {
                    submitList(repos)
                }
            }
        }

    }
}