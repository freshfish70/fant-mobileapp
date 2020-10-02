package com.traeen.fant.shared

import java.util.*

class ListedItem(id: Int){

    var id : Int = id

    var description: String = ""

    var name: String = ""

    var price: Float = 0f

    var sold: Boolean = false

    var created: String = ""

    var updated: String = ""

    var image: Array<Int> = arrayOf(0)

    var seller: User? = null;

    var buyer: User? = null;


}