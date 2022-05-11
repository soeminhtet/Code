package com.codigo.code.data.repository

import com.codigo.code.data.local.TMDBDatabase
import com.codigo.code.domain.model.Popular
import com.codigo.code.domain.model.UpComing
import com.codigo.code.domain.repository.LocalDataSource
import kotlinx.coroutines.flow.Flow

class LocalDataSourceImpl (database : TMDBDatabase): LocalDataSource {
    private val popularDao = database.popularDao()
    private val upcomingDao = database.upComingDao()

    override suspend fun updatePopular(popular: Popular) = popularDao.updatePopular(popular)

    override suspend fun updateUpComing(upComing: UpComing) = upcomingDao.updateUpComing(upComing)

    override fun getSelectedPopular(popularId: Int): Flow<Popular> = popularDao.getSelectedPopular(popularId)

    override fun getSelectedUpComing(upComingId: Int): Flow<UpComing> = upcomingDao.getSelectedUpComing(upComingId)
}