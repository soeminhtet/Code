package com.codigo.code.data.repository

import androidx.paging.PagingData
import com.codigo.code.domain.model.Popular
import com.codigo.code.domain.model.UpComing
import com.codigo.code.domain.repository.LocalDataSource
import com.codigo.code.domain.repository.RemoteDataSource
import kotlinx.coroutines.flow.Flow

class Repository (
    private val remote: RemoteDataSource,
    private val local : LocalDataSource
) {
    fun getAllPopulars() : Flow<PagingData<Popular>> = remote.getAllPopular()

    fun getAllUpComings() : Flow<PagingData<UpComing>> = remote.getAllUpComing()

    suspend fun updatePopular(popular: Popular) = local.updatePopular(popular)

    suspend fun updateUpComing(upComing: UpComing) = local.updateUpComing(upComing)

    fun getSelectedPopular(popularId: Int): Flow<Popular> = local.getSelectedPopular(popularId)

    fun getSelectedUpComing(upComingId : Int) : Flow<UpComing> = local.getSelectedUpComing(upComingId)
}