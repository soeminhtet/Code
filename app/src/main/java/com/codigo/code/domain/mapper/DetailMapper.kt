package com.codigo.code.domain.mapper

import com.codigo.code.domain.model.DetailModel
import com.codigo.code.domain.model.Popular
import com.codigo.code.domain.model.UpComing

fun Popular.toDetailModel() : DetailModel = DetailModel(
    id = this.id,
    originalTitle = this.originalTitle,
    overview = this.overview,
    posterPath = this.posterPath,
    backdropPath = this.backdropPath,
    releaseDate = this.releaseDate,
    voteAverage = this.voteAverage,
    voteCount = this.voteCount,
    genres = this.genres,
    favourite = this.favourite
)

fun UpComing.toDetailModel() : DetailModel = DetailModel(
    id = this.id,
    originalTitle = this.originalTitle,
    overview = this.overview,
    posterPath = this.posterPath,
    backdropPath = this.backdropPath,
    releaseDate = this.releaseDate,
    voteAverage = this.voteAverage,
    voteCount = this.voteCount,
    genres = this.genres,
    favourite = this.favourite
)

fun DetailModel.toPopular() : Popular = Popular(
    id = this.id,
    originalTitle = this.originalTitle,
    overview = this.overview,
    posterPath = this.posterPath,
    backdropPath = this.backdropPath,
    releaseDate = this.releaseDate,
    voteAverage = this.voteAverage,
    voteCount = this.voteCount,
    genres = this.genres,
    favourite = this.favourite
)

fun DetailModel.toUpComing() : UpComing = UpComing(
    id = this.id,
    originalTitle = this.originalTitle,
    overview = this.overview,
    posterPath = this.posterPath,
    backdropPath = this.backdropPath,
    releaseDate = this.releaseDate,
    voteAverage = this.voteAverage,
    voteCount = this.voteCount,
    genres = this.genres,
    favourite = this.favourite
)