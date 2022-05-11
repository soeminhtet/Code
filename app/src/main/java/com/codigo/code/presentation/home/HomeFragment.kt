package com.codigo.code.presentation.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import com.codigo.code.databinding.FragmentHomeBinding
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : Fragment() {
    companion object {
        private const val TAG = "HomeFragment"
    }

    private var _binding : FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val viewModel : HomeViewModel by viewModel()

    private lateinit var popularAdapter: PopularAdapter
    private lateinit var upComingAdapter: UpComingAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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
            viewModel.populars
                .flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
                .collectLatest {
                    popularAdapter.submitData(it)
                }
        }

        //PopularState
        lifecycleScope.launchWhenStarted {
            popularAdapter.loadStateFlow
                .flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
                .collectLatest { loadState ->
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
                        Log.e(TAG,it.error.toString())
                    }
                }
        }

        //UpComingData
        lifecycleScope.launchWhenStarted {
            viewModel.upComings
                .flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
                .collectLatest {
                    upComingAdapter.submitData(it)
                }
        }

        //UpComingState
        lifecycleScope.launchWhenStarted {
            upComingAdapter.loadStateFlow
                .flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
                .collectLatest { loadState ->
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
                        Log.e(TAG,it.error.toString())
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

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}