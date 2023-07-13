package com.nagarro.mohitdemo.domain

import com.nagarro.mohitdemo.data.models.LoggedInUser
import java.io.Serializable

data class LoginResult(
    val success: LoggedInUser? = null,
    val error: Int? = null
): Serializable