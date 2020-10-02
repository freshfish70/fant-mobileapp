package com.traeen.fant.ui.item_display

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.traeen.fant.ItemRepository
import com.traeen.fant.UserRepository
import com.traeen.fant.network.VolleyHTTP
import java.lang.IllegalArgumentException

class ItemViewModelFactory (private var application: Application?) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return if (modelClass == ItemViewModel::class.java && application != null) {
            val volley = VolleyHTTP.getInstance(application!!.applicationContext)
            ItemViewModel(
                UserRepository.getInstance(volley),
                ItemRepository.getInstance(volley)
            ) as T
        } else throw IllegalArgumentException("Unknown ViewModel - $modelClass")
    }
}