package com.traeen.fant.ui.create_user_display

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.traeen.fant.shared.Item

class CreateUserViewModel : ViewModel() {

    private val _formState = MutableLiveData<CreateUserFormState>()

    val fromState: LiveData<CreateUserFormState> = _formState

    fun formValuesChanged(){

    }
}