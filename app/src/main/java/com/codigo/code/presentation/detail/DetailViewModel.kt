package com.codigo.code.presentation.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codigo.code.domain.mapper.toDetailModel
import com.codigo.code.domain.mapper.toPopular
import com.codigo.code.domain.mapper.toUpComing
import com.codigo.code.domain.model.DetailModel
import com.codigo.code.domain.model.Popular
import com.codigo.code.domain.model.UpComing
import com.codigo.code.domain.usecases.UseCases
import com.codigo.code.presentation.main.Category
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val useCases: UseCases
): ViewModel() {

    private var _detailModel = MutableStateFlow<DetailModel?>(null)
    val detailModel = _detailModel.asStateFlow()

    fun getDetailModel(movieId : Int, category : String) {
        viewModelScope.launch(Dispatchers.IO) {
            if (category == Category.POPULAR.name) {
                useCases.getSelectedPopularUseCase(movieId).collectLatest {
                    _detailModel.value = it.toDetailModel()
                }
            } else {
                useCases.getSelectedUpComingUseCase(movieId).collectLatest {
                    _detailModel.value = it.toDetailModel()
                }
            }
        }
    }

    fun toggleFav(data: DetailModel,category: String) {
        if (category == Category.POPULAR.name) togglePopularFav(data = data.toPopular())
        else toggleUpComingFav(data = data.toUpComing())
    }

    private fun toggleUpComingFav(data : UpComing) {
        viewModelScope.launch(Dispatchers.IO) {
            val favourite = !data.favourite
            val upComing = data.copy(favourite = favourite)
            useCases.updateUpComingUseCase(upComing)
        }
    }

    private fun togglePopularFav(data : Popular) {
        viewModelScope.launch(Dispatchers.IO) {
            val favourite = !data.favourite
            val popular = data.copy(favourite = favourite)
            useCases.updatePopularUseCase(popular)
        }
    }
}