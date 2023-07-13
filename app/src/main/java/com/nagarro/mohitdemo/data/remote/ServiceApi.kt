package com.nagarro.mohitdemo.data.remote

import com.nagarro.mohitdemo.data.models.MovieDto
import retrofit2.http.GET
import retrofit2.http.Query

interface ServiceApi {

    @GET("movie/popular?")
    suspend fun getPopularMovies(
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        @Query("page") page: String
    ): MovieDto

}