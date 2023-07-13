package com.nagarro.mohitdemo.data.repository

import com.nagarro.mohitdemo.data.models.LoggedInUser
import com.nagarro.mohitdemo.domain.LoginDataSource
import com.nagarro.mohitdemo.domain.LoginResultState
import javax.inject.Inject

class LoginRepository @Inject constructor(private val dataSource: LoginDataSource) {

    private var user: LoggedInUser? = null

    val isLoggedIn: Boolean
        get() = user != null

    init {
        user = null
    }

    fun logout() {
        user = null
        dataSource.logout()
    }

    fun login(username: String, password: String): LoginResultState<LoggedInUser> {
        val result = dataSource.login(username, password)
        if (result is LoginResultState.Success) {
            setLoggedInUser(result.data)
        }
        return result
    }

    private fun setLoggedInUser(loggedInUser: LoggedInUser) {
        this.user = loggedInUser
    }
}