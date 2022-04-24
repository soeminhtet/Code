package com.codigo.code.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.codigo.code.data.local.TMDBDatabase
import com.codigo.code.data.paging_source.PopularRemoteMediator
import com.codigo.code.data.paging_source.UpComingRemoteMediator
import com.codigo.code.data.remote.API
import com.codigo.code.domain.model.Popular
import com.codigo.code.domain.model.UpComing
import com.codigo.code.domain.repository.RemoteDataSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@ExperimentalPagingApi
class RemoteDataSourceImpl @Inject constructor(
    private val api : API,
    private val database: TMDBDatabase
) : RemoteDataSource {
    private val popularDao = database.popularDao()
    private val upComingDao = database.upComingDao()

    override fun getAllPopular(): Flow<PagingData<Popular>> {
        val pagingSourceFactory = { popularDao.getAllPopular() }
        return Pager(
            config = PagingConfig(pageSize = 20),
            remoteMediator = PopularRemoteMediator(
                api = api,
                database = database
            ),
            pagingSourceFactory = pagingSourceFactory
        ).flow
    }

    override fun getAllUpComing(): Flow<PagingData<UpComing>> {
        val pagingSourceFactory = { upComingDao.getAllUpComing() }
        return Pager(
            config = PagingConfig(pageSize = 20),
            remoteMediator = UpComingRemoteMediator(
                api = api,
                database = database
            ),
            pagingSourceFactory = pagingSourceFactory
        ).flow
    }
}