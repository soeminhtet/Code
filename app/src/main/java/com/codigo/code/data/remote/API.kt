package com.codigo.code.data.remote

import com.codigo.code.domain.model.PopularResponse
import com.codigo.code.domain.model.UpComingResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface API {

    companion object{
        const val KEY = "3c04f7738534d248337f218c76843007"
    }

    @GET("movie/popular")
    suspend fun getPopularMovie(
        @Query("api_key") key : String = KEY,
        @Query("language") language: String = "en-US",
        @Query("page") page: Int
    ): PopularResponse

    @GET("movie/upcoming")
    suspend fun getUpComingMovie(
        @Query("api_key") key : String = KEY,
        @Query("language") language: String = "en-US",
        @Query("page") page: Int
    ): UpComingResponse

}