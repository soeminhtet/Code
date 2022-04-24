package com.codigo.code.presentation.detail

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.codigo.code.R
import com.codigo.code.databinding.ActivityDetailBinding
import com.codigo.code.domain.model.DetailModel
import com.codigo.code.presentation.main.Category
import com.codigo.code.presentation.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class DetailActivity : AppCompatActivity() {

    private lateinit var binding : ActivityDetailBinding
    private val viewModel : DetailViewModel by viewModels()
    private lateinit var detailModel : DetailModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_detail)

        val movieId = intent.getIntExtra(MainActivity.MOVIE_ID,-1)
        val category = intent.getStringExtra(MainActivity.CATEGORY) ?: Category.POPULAR.name
        viewModel.getDetailModel(movieId,category)

        binding.favImage.setOnClickListener{ viewModel.toggleFav(detailModel,category) }
    }

    override fun onStart() {
        super.onStart()
        lifecycleScope.launchWhenStarted {
            viewModel.detailModel
                .flowWithLifecycle(lifecycle,Lifecycle.State.STARTED)
                .collectLatest { model ->
                model?.let { detailModel = it }
                binding.data = model
                title = model?.originalTitle ?: "MovieDetail"
            }
        }
    }
}