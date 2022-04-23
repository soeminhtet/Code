package com.codigo.code.presentation.main

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.codigo.code.databinding.PopularItemBinding
import com.codigo.code.domain.model.Popular
import com.codigo.code.presentation.detail.DetailActivity

class PopularAdapter(private val favCallBack : (Popular)->Unit) : PagingDataAdapter<Popular, PopularAdapter.ViewHolder>(PopularComparator) {

    class ViewHolder(private val binding: PopularItemBinding,private val favCallBack: (Popular) -> Unit) : RecyclerView.ViewHolder(binding.root),
        View.OnClickListener {
        private var data : Popular? = null

        init {
            binding.root.setOnClickListener(this)
            binding.favImage.setOnClickListener(this)
        }

        fun bind(data : Popular?) {
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
                        intent.putExtra(MainActivity.CATEGORY,Category.POPULAR.name)
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
        val binding = PopularItemBinding.inflate(inflater,parent,false)
        return ViewHolder(binding,favCallBack)
    }
}

object PopularComparator : DiffUtil.ItemCallback<Popular>() {
    override fun areItemsTheSame(oldItem: Popular, newItem: Popular): Boolean = oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Popular, newItem: Popular): Boolean = oldItem == newItem
}