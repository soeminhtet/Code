package com.codigo.code.domain.usecases

data class UseCases(
    val getAllPopularsUseCase: GetAllPopularsUseCase,
    val getAllUpComingsUseCase: GetAllUpComingsUseCase,
    val updatePopularUseCase: UpdatePopularUseCase,
    val updateUpComingUseCase: UpdateUpComingUseCase,
    val getSelectedPopularUseCase: GetSelectedPopularUseCase,
    val getSelectedUpComingUseCase: GetSelectedUpComingUseCase,
)
