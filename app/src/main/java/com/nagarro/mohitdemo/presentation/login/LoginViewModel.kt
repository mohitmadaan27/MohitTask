package com.nagarro.mohitdemo.presentation.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nagarro.mohitdemo.R
import com.nagarro.mohitdemo.data.models.LoginFormState
import com.nagarro.mohitdemo.data.repository.LoginRepository
import com.nagarro.mohitdemo.domain.LoginResult
import com.nagarro.mohitdemo.domain.LoginResultState
import com.nagarro.mohitdemo.util.isPasswordValid
import com.nagarro.mohitdemo.util.isUserNameValid
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel@Inject constructor(
    private val loginRepository: LoginRepository
): ViewModel() {

    private val _loginForm = MutableLiveData<LoginFormState>()
    val loginFormState: LiveData<LoginFormState> = _loginForm

    private val _loginResult = MutableLiveData<LoginResult>()
    val loginResult: LiveData<LoginResult> = _loginResult

    fun login(username: String, password: String) {
        val result = loginRepository.login(username, password)
        if (result is LoginResultState.Success) {
            _loginResult.value = LoginResult(success = result.data)
        } else {
            _loginResult.value = LoginResult(error = R.string.login_failed)
        }
    }

    fun loginDataChanged(username: String, password: String) {
        if (!isUserNameValid(username)) {
            _loginForm.value = LoginFormState(usernameError = R.string.invalid_email)
        } else if (!isPasswordValid(password)) {
            _loginForm.value = LoginFormState(passwordError = R.string.invalid_password)
        } else {
            _loginForm.value = LoginFormState(isDataValid = true)
        }
    }
}