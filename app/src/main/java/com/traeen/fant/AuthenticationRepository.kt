package com.traeen.fant

import android.app.Application
import android.content.Context
import com.android.volley.AuthFailureError
import com.android.volley.NetworkResponse
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import com.traeen.fant.constants.Endpoints
import com.traeen.fant.network.VolleyHTTP

class AuthenticationRepository(val volleyHTTP: VolleyHTTP) {

    companion object {
        private var sInstance: AuthenticationRepository? = null
        fun getInstance(volleyHTTP: VolleyHTTP): AuthenticationRepository {
            if (sInstance == null) {
                synchronized(AuthenticationRepository) {
                    sInstance = AuthenticationRepository(volleyHTTP)
                }
            }
            return sInstance!!
        }
    }

    var loggedInUser: LoggedInUser? = null

    init {

    }

    fun isLoggedIn(): Boolean {
        return loggedInUser != null
    }

    fun logout() {
        loggedInUser = null
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
                        loggedInUser = LoggedInUser(authToken)
                        cb(loggedInUser)
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