package com.codigo.code.di

import androidx.paging.ExperimentalPagingApi
import com.codigo.code.data.remote.API
import com.codigo.code.data.repository.RemoteDataSourceImpl
import com.codigo.code.domain.repository.RemoteDataSource
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

@ExperimentalPagingApi
val networkModule = module {
    single {
        OkHttpClient.Builder()
            .readTimeout(100, TimeUnit.SECONDS)
            .connectTimeout(100, TimeUnit.SECONDS)
            .build()
    }
    single {
        Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/3/")
            .client(get())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(API::class.java)
    }
    single<RemoteDataSource> {
        RemoteDataSourceImpl(
            api = get(),
            database = get()
        )
    }
}