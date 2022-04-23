package com.codigo.code.data.local.dao

import androidx.paging.PagingSource
import androidx.room.*
import com.codigo.code.domain.model.Popular
import com.codigo.code.domain.model.UpComing
import kotlinx.coroutines.flow.Flow

@Dao
interface UpComingDao {
    @Query("SELECT * FROM upcoming ORDER BY id ASC")
    fun getAllUpComing(): PagingSource<Int, UpComing>

    @Query("SELECT * FROM upcoming WHERE id=:upComingId")
    fun getSelectedUpComing(upComingId : Int): Flow<UpComing>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addUpComings(upComings: List<UpComing>)

    @Query("DELETE FROM upcoming")
    suspend fun deleteAllUpComings()

    @Update
    suspend fun updateUpComing(upComing: UpComing)
}
