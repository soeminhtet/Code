package com.codigo.code.presentation.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.codigo.code.databinding.FragmentDetailBinding
import com.codigo.code.domain.model.DetailModel
import com.codigo.code.presentation.main.MainActivity
import com.codigo.code.util.*
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailFragment : Fragment() {
    private val args : DetailFragmentArgs by navArgs()

    private var _binding : FragmentDetailBinding? = null
    private val binding get() = _binding!!

    private val viewModel : DetailViewModel by viewModel()

    private lateinit var detailModel : DetailModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val movieId = args.movieId
        val category = args.category
        viewModel.getDetailModel(movieId,category)

        binding.favImage?.setOnClickListener{ viewModel.toggleFav(detailModel,category) }
    }

    override fun onStart() {
        super.onStart()
        lifecycleScope.launchWhenStarted {
            viewModel.detailModel
                .flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
                .collectLatest { data ->
                    data?.let {
                        detailModel = it
                        setData(data)
                    }
                }
        }
    }

    private fun setData(data : DetailModel) {
        val title = data.originalTitle ?: "MovieDetail"
        (requireActivity() as MainActivity).title = title
        binding.posterImg.loadURL(data.posterPath)
        binding.titleTxt.text = title
        binding.favImage?.setFav(data.favourite)
        binding.voteAvgTxt.votePercent(data.voteAverage)
        binding.dateTxt.setDetailDate(data.releaseDate)
        binding.voteTxt.voteCount(data.voteCount)
        binding.overviewTxt.text = data.overview
    }

    override fun onDestroy() {
        super.onDestroy()
        (requireActivity() as MainActivity).title = "Code"
        _binding = null
    }
}