package com.codigo.code.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.codigo.code.domain.model.PopularKey

@Dao
interface PopularKeyDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(remoteKey: List<PopularKey>)

    @Query("SELECT * FROM popular_key WHERE id = :popularId")
    suspend fun getRemoteKeys(popularId: Int): PopularKey?

    @Query("SELECT * FROM popular_key")
    suspend fun firstRemoteKey(): PopularKey?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addAllRemoteKeys(popularRemoteKeys: List<PopularKey>)

    @Query("DELETE FROM popular_key")
    suspend fun deleteAllKeys()
}