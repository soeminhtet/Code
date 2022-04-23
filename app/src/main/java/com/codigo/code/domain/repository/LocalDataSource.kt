package com.codigo.code.domain.repository

import com.codigo.code.domain.model.Popular
import com.codigo.code.domain.model.UpComing
import kotlinx.coroutines.flow.Flow

interface LocalDataSource {
    suspend fun updatePopular(popular: Popular)
    suspend fun updateUpComing(upComing: UpComing)
    fun getSelectedPopular(popularId : Int) : Flow<Popular>
    fun getSelectedUpComing(upComingId : Int) : Flow<UpComing>
}