package com.traeen.fant

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.traeen.fant.network.VolleyHTTP
import com.traeen.fant.ui.login_display.LoginDisplayViewModel
import java.lang.IllegalArgumentException

class ApplicationViewModelFactory (private var application: Application?) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return if (modelClass == ApplicationViewModel::class.java && application != null) {
            val volley = VolleyHTTP.getInstance(application!!.applicationContext)
            ApplicationViewModel(
                UserRepository.getInstance(volley)
            ) as T
        } else throw IllegalArgumentException("Unknown ViewModel - $modelClass")
    }
}