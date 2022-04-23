package com.codigo.code.domain.repository

import androidx.paging.PagingData
import com.codigo.code.domain.model.Popular
import com.codigo.code.domain.model.UpComing
import kotlinx.coroutines.flow.Flow

interface RemoteDataSource {
    fun getAllPopular(): Flow<PagingData<Popular>>

    fun getAllUpComing() : Flow<PagingData<UpComing>>
}