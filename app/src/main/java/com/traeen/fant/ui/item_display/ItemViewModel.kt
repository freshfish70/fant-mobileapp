package com.traeen.fant.ui.item_display

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.traeen.fant.Item
import com.traeen.fant.ItemRepository
import com.traeen.fant.UserRepository
import com.traeen.fant.shared.ListedItem

class ItemViewModel() : ViewModel() {

    private lateinit var userRepo: UserRepository

    private lateinit var itemRep: ItemRepository

    constructor(userRepo: UserRepository, itemRep: ItemRepository) : this() {
        this.userRepo = userRepo;
        this.itemRep = itemRep;
    }

    private val _item = MutableLiveData<ListedItem>()

    private val _purchased = MutableLiveData<Boolean>()



    fun setItem(listedItem: ListedItem) {
        this._item.value = listedItem
    }

    fun buyItem(listedItem: ListedItem){
        itemRep.buyItem(listedItem, userRepo.getUserAccessToken()){

        }
    }

    val listedItem: LiveData<ListedItem> = _item

    val purchased: LiveData<Boolean> = _purchased
}