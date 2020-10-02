package com.traeen.fant.ui.new_item

import com.traeen.fant.ApplicationViewModel
import com.traeen.fant.UserRepository

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.traeen.fant.ItemRepository
import com.traeen.fant.network.VolleyHTTP
import com.traeen.fant.ui.login_display.LoginDisplayViewModel
import java.lang.IllegalArgumentException

class NewItemsViewModelFactory (private var application: Application?) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return if (modelClass == NewItemsViewModel::class.java && application != null) {
            val volley = VolleyHTTP.getInstance(application!!.applicationContext)
            NewItemsViewModel(
                UserRepository.getInstance(volley),
                ItemRepository.getInstance(volley)
            ) as T
        } else throw IllegalArgumentException("Unknown ViewModel - $modelClass")
    }
}
