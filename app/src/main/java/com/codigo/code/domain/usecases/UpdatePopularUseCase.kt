package com.codigo.code.domain.usecases

import androidx.paging.PagingData
import com.codigo.code.data.repository.Repository
import com.codigo.code.domain.model.Popular
import com.codigo.code.domain.model.UpComing
import kotlinx.coroutines.flow.Flow

class UpdatePopularUseCase(private val repository: Repository) {
    suspend operator fun invoke(popular: Popular) = repository.updatePopular(popular)
}