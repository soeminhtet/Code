package com.codigo.code.di

import android.content.Context
import androidx.room.Room
import com.codigo.code.data.local.TMDBDatabase
import com.codigo.code.data.repository.LocalDataSourceImpl
import com.codigo.code.domain.repository.LocalDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ): TMDBDatabase {
        return Room.databaseBuilder(
            context,
            TMDBDatabase::class.java,
            "TMDBDatabase"
        ).build()
    }

    @Provides
    @Singleton
    fun provideLocalDataSource(database: TMDBDatabase): LocalDataSource  = LocalDataSourceImpl(database = database)
}