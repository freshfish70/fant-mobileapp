package com.traeen.fant.ui.login_display

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.traeen.fant.AuthenticationRepository
import com.traeen.fant.network.VolleyHTTP
import java.lang.IllegalArgumentException


class LoginViewModelFactory(private var application: Application?) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return if (modelClass == LoginDisplayViewModel::class.java && application != null) {
            LoginDisplayViewModel(
                AuthenticationRepository(VolleyHTTP.getInstance(application!!.applicationContext))
            ) as T
        } else throw IllegalArgumentException("Unknown ViewModel")
    }
}