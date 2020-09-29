package com.traeen.fant.ui.login_display;

import android.app.Application
import android.os.AsyncTask
import android.util.Log
import android.util.Patterns
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.loader.content.AsyncTaskLoader
import com.traeen.fant.AuthenticationRepository
import com.traeen.fant.LoggedInUser
import com.traeen.fant.R

class LoginDisplayViewModel(val authenticationRepository: AuthenticationRepository) : ViewModel() {

    private val _formState = MutableLiveData<LoginFormState>()
    private val _loginResult = MutableLiveData<LoginResult>()

    val formState: LiveData<LoginFormState> = _formState
    val loginResult: LiveData<LoginResult> = _loginResult

    fun loginDataChanged(email: String, password: String) {

        val form = LoginFormState();
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            form.mailError = R.string.text_login_invalid_email
        }

        if (password.length < 6) {
            form.passwordError = R.string.text_login_invalid_password
        }

        _formState.value = form
    }

    fun login(email: String, password: String) {
        authenticationRepository.login(email, password) {
            val loginResult = LoginResult()
            if (it != null) {
                loginResult.success = true
            } else {
                loginResult.error = R.string.text_login_wrong_email_password
            }
            _loginResult.value = loginResult
        }
    }


}