package com.traeen.fant.ui.current_user

import com.traeen.fant.UserRepository

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.traeen.fant.ItemRepository
import com.traeen.fant.network.VolleyHTTP
import java.lang.IllegalArgumentException

class CurrentUserViewModelFactory (private var application: Application?) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return if (modelClass == CurrentUserViewModel::class.java && application != null) {
            val volley = VolleyHTTP.getInstance(application!!.applicationContext)
            CurrentUserViewModel(
                UserRepository.getInstance(volley)
            ) as T
        } else throw IllegalArgumentException("Unknown ViewModel - $modelClass")
    }
}
