package com.traeen.fant.shared

import java.util.*

class Item(id: Int){

    var id : Int = id
        set(value) {}

    var description: String = ""
        set(value)  {}

    var name: String = ""
        set(value)  {}

    var price: Float = 0f
        set(value)  {}

    var sold: Boolean = false
        set(value)  {}

    var created: String = ""
        set(value)  {}

    var updated: String = ""
        set(value)  {}

    var image: Array<Int> = arrayOf(0)
        set(value)  {}

    var seller: User? = null;


}