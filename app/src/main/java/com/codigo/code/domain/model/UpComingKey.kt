package com.codigo.code.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "upcoming_key")
data class UpComingKey(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val prevPage: Int?,
    val nextPage: Int?,
    val lastUpdated: Long = System.currentTimeMillis()
)
