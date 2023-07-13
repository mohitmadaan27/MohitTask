package com.nagarro.mohitdemo.presentation.login

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.nagarro.mohitdemo.presentation.base.BaseFragment
import com.nagarro.mohitdemo.databinding.FragmentFormBinding
import com.nagarro.mohitdemo.util.toast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment: BaseFragment<FragmentFormBinding, LoginViewModel>() {

    override val viewModel: LoginViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpViews()
    }

    private fun setUpViews() {
        with(binding) {
            username.afterTextChanged {
                viewModel.loginDataChanged(
                    username.text.toString(),
                    password.text.toString()
                )
            }

            password.apply {
                afterTextChanged {
                    viewModel.loginDataChanged(
                        username.text.toString(),
                        password.text.toString()
                    )
                }

                setOnEditorActionListener { _, actionId, _ ->
                    when (actionId) {
                        EditorInfo.IME_ACTION_DONE ->
                            viewModel.login(
                                username.text.toString(),
                                password.text.toString()
                            )
                    }
                    false
                }

                login.setOnClickListener {
                    loading.visibility = View.VISIBLE
                    viewModel.login(username.text.toString(), password.text.toString())

                }
            }
            activity?.let { it ->
                viewModel.loginFormState.observe(it, Observer {
                    val loginState = it ?: return@Observer

                    login.isEnabled = loginState.isDataValid
                    if(username.isFocused && loginState.usernameError != null) {
                            username.error = getString(loginState.usernameError)
                    }
                    if(password.isFocused && loginState.passwordError != null) {
                            password.error = getString(loginState.passwordError)
                    }
                })
                
                viewModel.loginResult.observe(it, Observer {
                    val loginResult = it ?: return@Observer

                    loading.visibility = View.GONE
                    if (loginResult.error != null) {
                        showLoginFailed(loginResult.error)
                    }
                    if (loginResult.success != null) {
                        val action = LoginFragmentDirections
                            .actionFormFragmentToMovieFragment()
                        findNavController().navigate(action)
                    }
                })
            }
        }
    }

    private fun showLoginFailed(@StringRes errorString: Int) {
        toast(errorString.toString(),Toast.LENGTH_SHORT)
    }


    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentFormBinding =
        FragmentFormBinding.inflate(inflater, container, false)
    
    private fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
        this.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(editable: Editable?) {
                afterTextChanged.invoke(editable.toString())
            }
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
        })
    }
}

