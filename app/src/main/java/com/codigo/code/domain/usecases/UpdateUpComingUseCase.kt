package com.codigo.code.domain.usecases

import com.codigo.code.data.repository.Repository
import com.codigo.code.domain.model.UpComing

class UpdateUpComingUseCase(private val repository: Repository) {
    suspend operator fun invoke(upcoming : UpComing) = repository.updateUpComing(upcoming)
}