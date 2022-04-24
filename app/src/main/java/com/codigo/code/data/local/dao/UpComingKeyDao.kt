package com.codigo.code.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.codigo.code.domain.model.UpComingKey

@Dao
interface UpComingKeyDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(remoteKey: List<UpComingKey>)

    @Query("SELECT * FROM upcoming_key WHERE id = :upcomingId")
    suspend fun getRemoteKeys(upcomingId : Int): UpComingKey?

    @Query("SELECT * FROM upcoming_key")
    suspend fun firstRemoteKey(): UpComingKey?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addAllRemoteKeys(popularRemoteKeys: List<UpComingKey>)

    @Query("DELETE FROM upcoming_key")
    suspend fun deleteAllKeys()
}