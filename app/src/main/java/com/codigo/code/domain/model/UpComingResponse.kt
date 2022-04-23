package com.codigo.code.domain.model

import com.google.gson.annotations.SerializedName

data class UpComingResponse(
    @SerializedName("page")
    val page: Int,
    @SerializedName("results")
    val results: List<UpComing>,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResults: Int
)