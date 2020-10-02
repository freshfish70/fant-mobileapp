package com.traeen.fant

import android.util.Log
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.JsonRequest
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import com.traeen.fant.constants.Endpoints
import com.traeen.fant.network.VolleyHTTP
import com.traeen.fant.shared.User
import org.json.JSONException
import java.lang.Exception
import com.traeen.fant.shared.ListedItem as ListingItem


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

    fun addNewItem(item: Item, authToken: String, cb: (success: Boolean) -> Unit) {
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
                    Log.d("ITEM_ERROR", it.message.toString())
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

    fun buyItem(item: ListingItem, authToken: String, cb: (success: Boolean) -> Unit) {
        val request = object : JsonObjectRequest(
            Request.Method.PUT, Endpoints.POST_BUY_ITEM(), null,
            { response ->
                try {
                    cb(true)
                } catch (e: Exception) {
                    cb(false)
                }
            },
            {
                Log.d("ITEM_REQUEST_ERROR", it.message.toString())
            }) {
            @Throws(AuthFailureError::class)
            override fun getHeaders(): Map<String, String?> {
                val params: MutableMap<String, String?> = HashMap()
                params["Content-Type"] = "application/json"
                params["Authorization"] = authToken

                params["id"] = item.id.toString()
                return params
            }

        }

        volleyHTTP.addToRequestQueue(request)
    }
}