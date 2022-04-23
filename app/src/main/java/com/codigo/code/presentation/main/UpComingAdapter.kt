package com.codigo.code.presentation.main

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.codigo.code.databinding.UpcomingItemBinding
import com.codigo.code.domain.model.UpComing
import com.codigo.code.presentation.detail.DetailActivity

class UpComingAdapter(private val favCallBack : (UpComing)->Unit) : PagingDataAdapter<UpComing, UpComingAdapter.ViewHolder>(UpComingComparator) {

    class ViewHolder(private val binding: UpcomingItemBinding,private val favCallBack: (UpComing) -> Unit) : RecyclerView.ViewHolder(binding.root),
        View.OnClickListener {
        private var data : UpComing? = null

        init {
            binding.root.setOnClickListener(this)
            binding.favImage.setOnClickListener(this)
        }

        fun bind(data : UpComing?) {
            this.data = data
            data?.let {
                binding.data = it
                binding.executePendingBindings()
            }
        }

        override fun onClick(p0: View?) {
            p0?.let {
                when(it) {
                    binding.favImage -> data?.let(favCallBack)
                    else -> {
                        val intent = Intent(it.context, DetailActivity::class.java)
                        intent.putExtra(MainActivity.MOVIE_ID,data?.id)
                        intent.putExtra(MainActivity.CATEGORY,Category.UPCOMING.name)
                        it.context.startActivity(intent)
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