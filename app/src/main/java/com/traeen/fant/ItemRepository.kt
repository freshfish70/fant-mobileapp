package com.traeen.fant

import android.util.Log
import com.android.volley.AuthFailureError
import com.android.volley.NetworkResponse
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import com.traeen.fant.constants.Endpoints
import com.traeen.fant.network.VolleyHTTP
import com.traeen.fant.shared.User
import java.io.File
import java.io.InputStream


class ItemRepository private constructor(private val volleyHTTP: VolleyHTTP) {

    companion object {
        private var sInstance: ItemRepository? = null
        fun getInstance(volleyHTTP: VolleyHTTP): ItemRepository {
            if (sInstance == null) {
                synchronized(UserRepository) {
                    sInstance = ItemRepository(volleyHTTP)
                }
            }
            return sInstance!!
        }
    }

    fun addNewItem(item: Item, authToken:String, cb: (success: Boolean)->Unit) {
        val headers: MutableMap<String, String> = HashMap()
        headers["name"] = item.name
        headers["description"] = item.description
        headers["price"] = item.price.toString()
        headers["Authorization"] = authToken

        val request =
            object : VolleyMultipartRequest(
                Endpoints.POST_NEW_ITEM(),
                headers,
                { response ->

                },
                {

                }) {
                override fun getByteData(): Map<String, DataPart>? {
                    val params: MutableMap<String, DataPart> = HashMap()
                    params["cover"] = DataPart(
                        "test.jpg",
                        item.image.readBytes(),
                        "image/jpg"
                    )

                    return params
                }
            }
        volleyHTTP.addToRequestQueue(request)
    }
}