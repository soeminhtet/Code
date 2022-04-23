package com.codigo.code.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.codigo.code.domain.model.Popular
import com.codigo.code.domain.model.UpComing
import com.codigo.code.domain.usecases.UseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val useCases: UseCases
): ViewModel() {

    val populars : Flow<PagingData<Popular>> = useCases.getAllPopularsUseCase().flowOn(Dispatchers.IO).cachedIn(viewModelScope)

    val upComings : Flow<PagingData<UpComing>> = useCases.getAllUpComingsUseCase().flowOn(Dispatchers.IO).cachedIn(viewModelScope)

    fun toggleUpComingFav(data : UpComing) {
        viewModelScope.launch(Dispatchers.IO) {
            val favourite = !data.favourite
            val upComing = data.copy(favourite = favourite)
            useCases.updateUpComingUseCase(upComing)
        }
    }

    fun togglePopularFav(data : Popular) {
        viewModelScope.launch(Dispatchers.IO) {
            val favourite = !data.favourite
            val popular = data.copy(favourite = favourite)
            useCases.updatePopularUseCase(popular)
        }
    }
}