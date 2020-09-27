package com.traeen.fant.ui.items_display

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ItemsDisplayViewModels : ViewModel() {

    var selected: String = ""

    fun select(item: String) {
        selected = item
    }
}