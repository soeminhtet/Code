package com.codigo.code.presentation.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.codigo.code.databinding.PopularItemBinding
import com.codigo.code.domain.model.Popular
import com.codigo.code.presentation.main.Category
import com.codigo.code.util.loadURL
import com.codigo.code.util.setFav
import com.codigo.code.util.votePercent

class PopularAdapter(private val favCallBack : (Popular)->Unit) : PagingDataAdapter<Popular, PopularAdapter.ViewHolder>(
    PopularComparator
) {

    class ViewHolder(private val binding: PopularItemBinding,private val favCallBack: (Popular) -> Unit) : RecyclerView.ViewHolder(binding.root),
        View.OnClickListener {
        private var data : Popular? = null

        init {
            binding.root.setOnClickListener(this)
            binding.popularFavImg.setOnClickListener(this)
        }

        fun bind(data : Popular?) {
            this.data = data
            data?.let{ setData(it) }
        }

        private fun setData(data : Popular) {
            binding.popularPosterImg.loadURL(data.posterPath, conorRadius = 10f)
            binding.popularTitle.text = data.originalTitle ?: "Unknown"
            binding.popularFavImg.setFav(data.favourite)
            binding.popularVoteTxt.votePercent(data.voteAverage)
        }

        override fun onClick(p0: View?) {
            p0?.let {
                when(it) {
                    binding.popularFavImg -> data?.let(favCallBack)
                    else -> {
                        val action = HomeFragmentDirections
                            .actionHomeFragmentToDetailFragment(data?.id ?: 0, Category.POPULAR.name)
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
        val binding = PopularItemBinding.inflate(inflater,parent,false)
        return ViewHolder(binding,favCallBack)
    }
}

object PopularComparator : DiffUtil.ItemCallback<Popular>() {
    override fun areItemsTheSame(oldItem: Popular, newItem: Popular): Boolean = oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Popular, newItem: Popular): Boolean = oldItem == newItem
}