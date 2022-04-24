package com.codigo.code.domain.usecases

import com.codigo.code.data.repository.Repository
import com.codigo.code.domain.model.Popular

class UpdatePopularUseCase(private val repository: Repository) {
    suspend operator fun invoke(popular: Popular) = repository.updatePopular(popular)
}