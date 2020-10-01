package com.traeen.fant

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.traeen.fant.shared.User
import com.traeen.fant.ui.create_user_display.CreateUserFormState
import java.io.InputStream

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