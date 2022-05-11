package com.codigo.code

import android.app.Application
import androidx.paging.ExperimentalPagingApi
import com.codigo.code.di.databaseModule
import com.codigo.code.di.networkModule
import com.codigo.code.di.repositoryModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

@ExperimentalPagingApi
class App : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(
                databaseModule,
                networkModule,
                repositoryModule
            )
        }
    }
}