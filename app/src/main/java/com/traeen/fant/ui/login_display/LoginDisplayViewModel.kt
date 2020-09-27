package com.traeen.fant.ui.login_display;

import android.app.Application
import android.util.Patterns
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.traeen.fant.R
import com.traeen.fant.shared.Item

class LoginDisplayViewModel(application: Application) : AndroidViewModel(application) {

    private val _emailError = MutableLiveData<Int>()
    private val _passwordError = MutableLiveData<Int>()
    private val _loginError = MutableLiveData<Int>()


    val emailError: LiveData<Int> = _emailError
    val passwordError: LiveData<Int> = _passwordError
    val loginError: LiveData<Int> = _loginError

    fun setEmailErrorMessage(errorMessage: Int) {
        this._emailError.value = errorMessage
    }

    fun setPasswordErrorMessage(errorMessage: Int) {
        this._emailError.value = errorMessage
    }

    fun setLoginError(errorMessage: Int) {
        this._emailError.value = errorMessage
    }

    fun loginDataChanged(email: String, password: String){
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            setEmailErrorMessage(R.string.text_login_invalid_email)
        }
        if (password.length < 6){
            setPasswordErrorMessage(R.string.text_login_invalid_password)
        }
    }

}