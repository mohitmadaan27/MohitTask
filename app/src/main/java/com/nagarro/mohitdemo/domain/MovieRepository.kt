package com.nagarro.mohitdemo.domain

import com.nagarro.mohitdemo.data.remote.ServiceApi
import com.nagarro.mohitdemo.util.Constants
import javax.inject.Inject

class MovieRepository @Inject constructor(
    private val api: ServiceApi,
) {
    suspend fun getPopularMovie() = api.getPopularMovies(Constants.API_KEY, Constants.LANGUAGE, "1")
}