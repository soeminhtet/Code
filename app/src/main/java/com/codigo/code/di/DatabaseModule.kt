package com.codigo.code.di

import androidx.paging.ExperimentalPagingApi
import androidx.room.Room
import com.codigo.code.data.local.TMDBDatabase
import com.codigo.code.data.repository.LocalDataSourceImpl
import com.codigo.code.domain.repository.LocalDataSource
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

@ExperimentalPagingApi
val databaseModule = module {
    single {
        Room.databaseBuilder(
            androidApplication(),
            TMDBDatabase::class.java,
            "TMDBDatabase"
        ).build()
    }
    single<LocalDataSource> {
        LocalDataSourceImpl(get())
    }
}