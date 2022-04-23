package com.codigo.code.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.codigo.code.data.local.dao.PopularDao
import com.codigo.code.data.local.dao.PopularKeyDao
import com.codigo.code.data.local.dao.UpComingDao
import com.codigo.code.data.local.dao.UpComingKeyDao
import com.codigo.code.domain.model.Popular
import com.codigo.code.domain.model.PopularKey
import com.codigo.code.domain.model.UpComing
import com.codigo.code.domain.model.UpComingKey

@Database(entities = [Popular::class, PopularKey::class, UpComing::class, UpComingKey::class], version = 1, exportSchema = false)
@TypeConverters(GenreConverter::class)
abstract class TMDBDatabase : RoomDatabase() {
    abstract fun popularDao() : PopularDao
    abstract fun popularKeysDao() : PopularKeyDao
    abstract fun upComingDao() : UpComingDao
    abstract fun upComingKeyDao() : UpComingKeyDao
}