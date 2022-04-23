package com.codigo.code.domain.usecases

import androidx.paging.PagingData
import com.codigo.code.data.repository.Repository
import com.codigo.code.domain.model.UpComing
import kotlinx.coroutines.flow.Flow

class GetAllUpComingsUseCase (
    private val repository: Repository
) {
    operator fun invoke() : Flow<PagingData<UpComing>> = repository.getAllUpComings()
}