package com.codigo.code.di

import androidx.paging.ExperimentalPagingApi
import com.codigo.code.data.local.TMDBDatabase
import com.codigo.code.data.remote.API
import com.codigo.code.data.repository.RemoteDataSourceImpl
import com.codigo.code.domain.repository.RemoteDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@ExperimentalPagingApi
@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideOkHttp(): OkHttpClient {
        return OkHttpClient.Builder()
            .readTimeout(100, TimeUnit.SECONDS)
            .connectTimeout(100, TimeUnit.SECONDS)
            .build()
    }

    @Singleton
    @Provides
    fun provideAPI(okHttpClient: OkHttpClient): API {
        return Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/3/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(API::class.java)
    }

    @Provides
    @Singleton
    fun provideRemoteDataSource(
        api: API,
        database : TMDBDatabase
    ): RemoteDataSource {
        return RemoteDataSourceImpl(
            api = api,
            database = database
        )
    }
}