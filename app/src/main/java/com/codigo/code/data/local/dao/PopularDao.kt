package com.codigo.code.data.local.dao

import androidx.paging.PagingSource
import androidx.room.*
import com.codigo.code.domain.model.Popular
import kotlinx.coroutines.flow.Flow

@Dao
interface PopularDao {

    @Query("SELECT * FROM popular ORDER BY id ASC")
    fun getAllPopular(): PagingSource<Int, Popular>

    @Query("SELECT * FROM popular WHERE id=:popularId")
    fun getSelectedPopular(popularId : Int): Flow<Popular>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addPopulars(populars: List<Popular>)

    @Query("DELETE FROM popular")
    suspend fun deleteAllPopulars()

    @Update
    suspend fun updatePopular(popular: Popular)
}