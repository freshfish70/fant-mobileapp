package com.traeen.fant.ui.item_display

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.traeen.fant.shared.Item

class ItemDisplayViewModel : ViewModel() {

    private val _item = MutableLiveData<Item>()

    fun setItem(item: Item) {
        this._item.value = item
    }

    val text: LiveData<Item> = _item
}