package com.codigo.code.data.paging_source

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.codigo.code.data.local.TMDBDatabase
import com.codigo.code.data.remote.API
import com.codigo.code.domain.model.UpComing
import com.codigo.code.domain.model.UpComingKey

@ExperimentalPagingApi
class UpComingRemoteMediator(
    private val api: API,
    private val database: TMDBDatabase
) : RemoteMediator<Int, UpComing>() {

    private val upcomingDao = database.upComingDao()
    private val upcomingKeyDao = database.upComingKeyDao()

    override suspend fun initialize(): InitializeAction {
        val currentTime = System.currentTimeMillis()
        val lastUpdated = upcomingKeyDao.firstRemoteKey()?.lastUpdated ?: 0L
        val cacheTimeout = 1440

        val diffInMinutes = (currentTime - lastUpdated) / 1000 / 60
        return if (diffInMinutes.toInt() <= cacheTimeout) {
            InitializeAction.SKIP_INITIAL_REFRESH
        } else {
            InitializeAction.LAUNCH_INITIAL_REFRESH
        }
    }

    override suspend fun load(loadType: LoadType, state: PagingState<Int, UpComing>): MediatorResult {
        return try {
            val page = when (loadType) {
                LoadType.REFRESH -> {
                    val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                    remoteKeys?.nextPage?.minus(1) ?: 1
                }
                LoadType.PREPEND -> {
                    val remoteKeys = getRemoteKeyForFirstItem(state)
                    val prevPage = remoteKeys?.prevPage
                        ?: return MediatorResult.Success(
                            endOfPaginationReached = remoteKeys != null
                        )
                    prevPage
                }
                LoadType.APPEND -> {
                    val remoteKeys = getRemoteKeyForLastItem(state)
                    val nextPage = remoteKeys?.nextPage ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                    nextPage
                }
            }

            val response = api.getUpComingMovie(page = page)
            val endOfPaginationReached = response.results.isEmpty()
            if(response.results.isNotEmpty()) {
                database.withTransaction {
                    if (loadType == LoadType.REFRESH) {
                        upcomingDao.deleteAllUpComings()
                        upcomingKeyDao.deleteAllKeys()
                    }
                    val prevPage = if (page == 1) null else page - 1
                    val nextPage = if (endOfPaginationReached) null else page + 1
                    val keys = response.results.map { upcoming ->
                        UpComingKey(
                            id = upcoming.id,
                            prevPage = prevPage,
                            nextPage = nextPage,
                        )
                    }
                    database.upComingKeyDao().insertAll(remoteKey = keys)
                    database.upComingDao().addUpComings(response.results)
                }
            }
            MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        }
        catch (e: Exception) {
            return MediatorResult.Error(e)
        }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, UpComing>
    ): UpComingKey? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { id ->
                upcomingKeyDao.getRemoteKeys(upcomingId = id)
            }
        }
    }

    private suspend fun getRemoteKeyForFirstItem(
        state: PagingState<Int, UpComing>
    ): UpComingKey? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { upcoming ->
                upcomingKeyDao.getRemoteKeys(upcomingId = upcoming.id)
            }
    }

    private suspend fun getRemoteKeyForLastItem(
        state: PagingState<Int, UpComing>
    ): UpComingKey? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { upcoming -> upcomingKeyDao.getRemoteKeys(upcomingId = upcoming.id) }
    }
}