package com.codigo.code.domain.usecases

import androidx.paging.PagingData
import com.codigo.code.data.repository.Repository
import com.codigo.code.domain.model.Popular
import kotlinx.coroutines.flow.Flow

class GetAllPopularsUseCase(
    private val repository: Repository
) {
    operator fun invoke() : Flow<PagingData<Popular>> = repository.getAllPopulars()
}