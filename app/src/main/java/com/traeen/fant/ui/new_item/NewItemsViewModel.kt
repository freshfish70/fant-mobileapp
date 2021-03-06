package com.traeen.fant.ui.new_item

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.traeen.fant.Item
import com.traeen.fant.ItemRepository
import com.traeen.fant.UserRepository
import com.traeen.fant.shared.ListedItem
import com.traeen.fant.shared.User

class NewItemsViewModel() : ViewModel() {

    private lateinit var authRepo: UserRepository

    private lateinit var itemRep: ItemRepository

    constructor(authRepo: UserRepository, itemRep: ItemRepository) : this() {
        this.authRepo = authRepo;
        this.itemRep = itemRep;
    }

    private val _itemAdded = MutableLiveData<ListedItem?>(null)
    val itemAddedState: LiveData<ListedItem?> = _itemAdded

    fun addItem(item: Item) {
        if (!authRepo.isLoggedIn()) return
        itemRep.addNewItem(item, authRepo.getUserAccessToken()) {
            _itemAdded.value = it

        }
    }

}