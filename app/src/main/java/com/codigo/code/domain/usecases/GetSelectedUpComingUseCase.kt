package com.codigo.code.domain.usecases

import com.codigo.code.data.repository.Repository
import com.codigo.code.domain.model.UpComing
import kotlinx.coroutines.flow.Flow

class GetSelectedUpComingUseCase (private val repository: Repository) {
    operator fun invoke(upComingId : Int) : Flow<UpComing> = repository.getSelectedUpComing(upComingId)
}