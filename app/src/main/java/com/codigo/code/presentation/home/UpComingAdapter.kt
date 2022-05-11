package com.codigo.code.presentation.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.codigo.code.databinding.UpcomingItemBinding
import com.codigo.code.domain.model.UpComing
import com.codigo.code.presentation.main.Category
import com.codigo.code.util.loadURL
import com.codigo.code.util.setFav
import com.codigo.code.util.voteCountFormat
import com.codigo.code.util.votePercent

class UpComingAdapter(private val favCallBack : (UpComing)->Unit) : PagingDataAdapter<UpComing, UpComingAdapter.ViewHolder>(
    UpComingComparator
) {

    class ViewHolder(private val binding: UpcomingItemBinding,private val favCallBack: (UpComing) -> Unit) : RecyclerView.ViewHolder(binding.root),
        View.OnClickListener {
        private var data : UpComing? = null

        init {
            binding.root.setOnClickListener(this)
            binding.upComingFavImg.setOnClickListener(this)
        }

        fun bind(data : UpComing?) {
            this.data = data
            data?.let {setData(it)}
        }

        private fun setData(data : UpComing) {
            binding.upComingPosterImg.loadURL(data.posterPath,conorRadius = 10f)
            binding.upComingTitleTxt.text = data.originalTitle ?: "Unknown"
            binding.upComingOverviewTxt.text = data.overview ?: "No Overview"
            binding.upComingFavImg.setFav(data.favourite)
            binding.upComingVotePercentTxt.votePercent(data.voteAverage)
            binding.upComingVoteTotalTxt.voteCountFormat(data.voteCount)
        }

        override fun onClick(p0: View?) {
            p0?.let {
                when(it) {
                    binding.upComingFavImg -> data?.let(favCallBack)
                    else -> {
                        val action = HomeFragmentDirections
                            .actionHomeFragmentToDetailFragment(data?.id ?: 0, Category.UPCOMING.name)
                        it.findNavController().navigate(action)
                    }
                }
            }
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = UpcomingItemBinding.inflate(inflater,parent,false)
        return ViewHolder(binding,favCallBack)
    }
}

object UpComingComparator : DiffUtil.ItemCallback<UpComing>() {
    override fun areItemsTheSame(oldItem: UpComing, newItem: UpComing): Boolean = oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: UpComing, newItem: UpComing): Boolean = oldItem == newItem
}