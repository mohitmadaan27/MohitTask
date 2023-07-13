package com.nagarro.mohitdemo.data.models

import com.google.gson.annotations.SerializedName

data class MovieDetailDto(
    val adult: Boolean,
    val id: Int,
    @SerializedName("poster_path")
    val posterPath: Any,
    val title: String,
    val video: Boolean,
)

