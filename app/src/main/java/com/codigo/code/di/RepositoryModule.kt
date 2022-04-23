package com.codigo.code.di

import com.codigo.code.data.repository.Repository
import com.codigo.code.domain.usecases.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideUseCases(repository: Repository): UseCases {
        return UseCases(
            getAllPopularsUseCase = GetAllPopularsUseCase(repository),
            getAllUpComingsUseCase = GetAllUpComingsUseCase(repository),
            updatePopularUseCase = UpdatePopularUseCase(repository),
            updateUpComingUseCase =  UpdateUpComingUseCase(repository),
            getSelectedPopularUseCase = GetSelectedPopularUseCase(repository),
            getSelectedUpComingUseCase = GetSelectedUpComingUseCase(repository)
        )
    }
}