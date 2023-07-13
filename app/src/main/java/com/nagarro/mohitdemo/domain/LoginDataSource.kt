package com.nagarro.mohitdemo.domain

import com.nagarro.mohitdemo.data.models.LoggedInUser
import java.io.IOException
import java.util.*
import javax.inject.Inject

class LoginDataSource @Inject constructor() {

    fun login(username: String, password: String): LoginResultState<LoggedInUser> {
        return try {
            val currentUser = LoggedInUser(UUID.randomUUID().toString(), username)
            LoginResultState.Success(currentUser)
        } catch (e: Throwable) {
            LoginResultState.Error(IOException("Error logging in", e))
        }
    }

    fun logout() {
    }
}