package com.codigo.code.presentation.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import com.codigo.code.R
import com.codigo.code.databinding.ActivityMainBinding
import com.codigo.code.domain.model.UpComing
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

enum class Category {
    POPULAR,UPCOMING
}

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    companion object{
        const val MOVIE_ID = "movie_id"
        const val CATEGORY = "category"
    }

    private lateinit var binding : ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()

    private lateinit var popularAdapter: PopularAdapter
    private lateinit var upComingAdapter: UpComingAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main)

        popularAdapter = PopularAdapter { viewModel.togglePopularFav(it) }

        upComingAdapter = UpComingAdapter { viewModel.toggleUpComingFav(it) }

        binding.popularRecycler.adapter = popularAdapter
        binding.upComingRecycler.adapter = upComingAdapter

        binding.popularRetry.setOnClickListener { popularAdapter.retry() }
        binding.upComingRetry.setOnClickListener{ upComingAdapter.retry() }
    }

    override fun onStart() {
        super.onStart()

        //PopularData
        lifecycleScope.launchWhenStarted {
            viewModel.populars.collectLatest {
                popularAdapter.submitData(it)
            }
        }

        //PopularState
        lifecycleScope.launchWhenStarted {
            popularAdapter.loadStateFlow.collectLatest { loadState ->
                val isListEmpty = loadState.refresh is LoadState.NotLoading && popularAdapter.itemCount == 0
                if(isListEmpty) showEmptyPopularMovie() else hideEmptyPopularMovie()

                val loading = (loadState.source.refresh is LoadState.Loading || loadState.mediator?.refresh is LoadState.Loading) && isListEmpty
                binding.popularProgress.visibility = if (loading) View.VISIBLE else View.INVISIBLE

                val retry = loadState.source.refresh is LoadState.Error || loadState.mediator?.refresh is LoadState.Error
                binding.popularRetry.visibility = if (retry) View.VISIBLE else View.INVISIBLE

                val errorState = loadState.source.append as? LoadState.Error
                    ?: loadState.source.prepend as? LoadState.Error
                    ?: loadState.mediator?.append as? LoadState.Error
                    ?: loadState.mediator?.prepend as? LoadState.Error
                    ?: loadState.append as? LoadState.Error
                    ?: loadState.prepend as? LoadState.Error
                errorState?.let {
                    Toast.makeText(this@MainActivity, "${it.error}", Toast.LENGTH_SHORT).show()
                }
            }
        }

        //UpComingData
        lifecycleScope.launchWhenStarted {
            viewModel.upComings.collectLatest {
                upComingAdapter.submitData(it)
            }
        }

        //UpComingState
        lifecycleScope.launchWhenStarted {
            upComingAdapter.loadStateFlow.collectLatest { loadState ->
                val isListEmpty = loadState.refresh is LoadState.NotLoading && upComingAdapter.itemCount == 0
                if(isListEmpty) showEmptyUpComingMovie() else hideEmptyUpComingMovie()

                val loading = (loadState.source.refresh is LoadState.Loading || loadState.mediator?.refresh is LoadState.Loading) && isListEmpty
                binding.upComingProgress.visibility = if (loading) View.VISIBLE else View.INVISIBLE

                val retry = loadState.source.refresh is LoadState.Error || loadState.mediator?.refresh is LoadState.Error
                binding.upComingRetry.visibility = if (retry) View.VISIBLE else View.INVISIBLE

                val errorState = loadState.source.append as? LoadState.Error
                    ?: loadState.source.prepend as? LoadState.Error
                    ?: loadState.mediator?.append as? LoadState.Error
                    ?: loadState.mediator?.prepend as? LoadState.Error
                    ?: loadState.append as? LoadState.Error
                    ?: loadState.prepend as? LoadState.Error
                errorState?.let {
                    Toast.makeText(this@MainActivity, "${it.error}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun showEmptyPopularMovie() {
        binding.popularRecycler.visibility = View.INVISIBLE
        binding.emptyPopularTxt.visibility = View.VISIBLE
    }

    private fun showEmptyUpComingMovie() {
        binding.upComingRecycler.visibility = View.INVISIBLE
        binding.emptyUpComingTxt.visibility = View.VISIBLE
    }

    private fun hideEmptyPopularMovie() {
        binding.popularRecycler.visibility = View.VISIBLE
        binding.emptyPopularTxt.visibility = View.INVISIBLE
    }

    private fun hideEmptyUpComingMovie() {
        binding.upComingRecycler.visibility = View.VISIBLE
        binding.emptyUpComingTxt.visibility = View.INVISIBLE
    }
}