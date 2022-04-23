package com.codigo.code.domain.model

data class DetailModel(
    val id: Int,
    val originalTitle: String?,
    val overview: String?,
    val posterPath: String?,
    val backdropPath: String?,
    val releaseDate: String?,
    val voteAverage: Double?,
    val voteCount: Int?,
    val genres : List<Int>?,
    val favourite : Boolean = false
)

