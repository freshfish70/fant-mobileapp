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
import org.json.JSONObject

class UserRepository private constructor(private val volleyHTTP: VolleyHTTP) {

    companion object {
        private var sInstance: UserRepository? = null
        fun getInstance(volleyHTTP: VolleyHTTP): UserRepository {
            if (sInstance == null) {
                synchronized(UserRepository) {
                    sInstance = UserRepository(volleyHTTP)
                }
            }
            return sInstance!!
        }
    }

    var loggedInUser: LoggedInUser? = null

    fun isLoggedIn(): Boolean {
        return sInstance!!.loggedInUser != null
    }

    fun logout() {
        sInstance!!.loggedInUser = null
    }

    fun getUserAccessToken() : String {
        return sInstance!!.loggedInUser?.accessToken ?: ""
    }

    fun getCurrentUser(cb: (User) -> Unit) {
        val getUserRequest =
            object : JsonObjectRequest(
                Request.Method.GET, Endpoints.GET_CURRENT_USER(), null,
                { response ->
                    Log.d("CURRENT USER", response.toString());
                    cb(User(1,"a", "b","c"))
                },
                {
//                    cb(null)
                }) {
                @Throws(AuthFailureError::class)
                override fun getHeaders(): Map<String, String?> {
                    val params: MutableMap<String, String?> = HashMap()
                    Log.d("CU", getUserAccessToken())
                    params["Authorization"] = sInstance!!.getUserAccessToken()
                    return params
                }
            }
        volleyHTTP.addToRequestQueue(getUserRequest)
    }

    fun login(email: String, password: String, cb: (LoggedInUser?) -> Unit) {
        var authToken = ""
        val stringRequest =
            object : StringRequest(
                Request.Method.POST, Endpoints.POST_LOGIN(),
                { response ->
                    val jsonObject: JsonObject = JsonParser.parseString(response).asJsonObject
                    val data = jsonObject.get("data")
                    if (data == null && authToken.isNotEmpty()) {
                        cb(null)
                    } else {
                        sInstance!!.loggedInUser = LoggedInUser(authToken)
                        cb(sInstance!!.loggedInUser)
                    }
                },
                {
                    cb(null)
                }) {
                @Throws(AuthFailureError::class)
                override fun getHeaders(): Map<String, String> {
                    val params: MutableMap<String, String> = HashMap()
                    params["email"] = email
                    params["password"] = password
                    params["content-type"] = "application/json"
                    return params
                }

                override fun parseNetworkResponse(response: NetworkResponse?): Response<String> {
                    val authorization = response?.headers?.get("Authorization")
                    if (authorization != null) {
                        authToken = authorization
                    } else {
                        cb(null)
                    }
                    return super.parseNetworkResponse(response)
                }
            }
        volleyHTTP.addToRequestQueue(stringRequest)
    }
}