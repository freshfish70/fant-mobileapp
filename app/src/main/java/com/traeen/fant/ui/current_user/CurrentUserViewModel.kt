package com.traeen.fant.ui.current_user

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.traeen.fant.Item
import com.traeen.fant.ItemRepository
import com.traeen.fant.UserRepository
import com.traeen.fant.shared.ListedItem
import com.traeen.fant.shared.User

class CurrentUserViewModel() : ViewModel() {

    private lateinit var authRepo: UserRepository

    constructor(authRepo: UserRepository) : this() {
        this.authRepo = authRepo;
    }


}