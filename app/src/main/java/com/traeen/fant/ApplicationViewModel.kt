package com.traeen.fant

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.traeen.fant.shared.User

class ApplicationViewModel(private val authRepo: UserRepository) : ViewModel() {

    private val _userLoggedIn = MutableLiveData<Boolean>(false)

    val userState: LiveData<Boolean> = _userLoggedIn

    var currentUser: User? = null
        private set

    fun userLoggedIn() {
        _userLoggedIn.value = true
        authRepo.getCurrentUser {
            currentUser = it
        }
    }

    fun logoutUser() {
        _userLoggedIn.value = false
        authRepo.logout()
    }

    fun isUserLoggedIn(): Boolean {
        return authRepo.isLoggedIn()
    }


}