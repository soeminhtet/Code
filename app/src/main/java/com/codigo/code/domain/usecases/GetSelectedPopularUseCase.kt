package com.codigo.code.domain.usecases

import com.codigo.code.data.repository.Repository
import com.codigo.code.domain.model.Popular
import kotlinx.coroutines.flow.Flow

class GetSelectedPopularUseCase(private val repository: Repository) {
    operator fun invoke(popularId : Int) : Flow<Popular> = repository.getSelectedPopular(popularId)
}