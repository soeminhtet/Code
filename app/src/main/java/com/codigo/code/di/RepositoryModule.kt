package com.codigo.code.di

import com.codigo.code.data.repository.Repository
import com.codigo.code.domain.usecases.*
import com.codigo.code.presentation.detail.DetailFragment
import com.codigo.code.presentation.detail.DetailViewModel
import com.codigo.code.presentation.home.HomeFragment
import com.codigo.code.presentation.home.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val repositoryModule = module {
    single {
        Repository(
            remote = get(),
            local = get()
        )
    }
    single {
        UseCases(
            getAllPopularsUseCase = GetAllPopularsUseCase(get()),
            getAllUpComingsUseCase = GetAllUpComingsUseCase(get()),
            updatePopularUseCase = UpdatePopularUseCase(get()),
            updateUpComingUseCase =  UpdateUpComingUseCase(get()),
            getSelectedPopularUseCase = GetSelectedPopularUseCase(get()),
            getSelectedUpComingUseCase = GetSelectedUpComingUseCase(get())
        )
    }
    viewModel {
        HomeViewModel(get())
    }
    viewModel {
        DetailViewModel(get())
    }
}