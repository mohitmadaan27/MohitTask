package com.nagarro.mohitdemo.data.models

data class MovieDto(
    val dates: Dates,
    val page: Int,
    val results: List<MovieDetailDto>,
    val total_pages: Int,
    val total_results: Int
)