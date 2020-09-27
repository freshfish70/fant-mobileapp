package com.traeen.fant.ui.login_display

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.android.volley.AuthFailureError
import com.android.volley.NetworkResponse
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import com.traeen.fant.R
import com.traeen.fant.constants.Endpoints
import com.traeen.fant.network.HTTPAccess
import com.traeen.fant.network.VolleyHTTP
import com.traeen.fant.shared.Item
import com.traeen.fant.ui.item_display.ItemDisplayViewModel
import com.traeen.fant.ui.items_display.ItemsListAdapter
import kotlinx.android.synthetic.main.fragment_home.view.*
import kotlinx.android.synthetic.main.fragment_login.view.*
import kotlinx.android.synthetic.main.nav_header_main.*
import org.json.JSONObject


class LoginDisplayFragment : Fragment() {

    private var http: VolleyHTTP? = null

    private val loginModel: LoginDisplayViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (activity is HTTPAccess) {
            http = (activity as HTTPAccess).get()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_login, container, false)

        val email = root.input_email
        val password = root.input_password


        val loginFieldsWatcher = object: TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                loginModel.loginDataChanged(email.toString(), password.toString())
            }
        }
        email.addTextChangedListener(loginFieldsWatcher)

        root.login_submit.setOnClickListener {
            login(email.toString(), password.toString())
        }

        return root
    }

    fun login(email: String, password: String) {
        val stringRequest =
            object : StringRequest(Request.Method.POST, Endpoints.POST_LOGIN(),
                { response ->
                    val gson = Gson()
                    val jsonObject: JsonObject = JsonParser.parseString(response).asJsonObject
                    val data = jsonObject.get("data")
                    if (data == null) {
                        loginModel.setLoginError(R.string.text_login_wrong_email_password)
                    }
                },
                {
                    loginModel.setLoginError(R.string.text_login_error)
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
                        Log.d("login", authorization)
                    }
                    return super.parseNetworkResponse(response)
                }
            }


        http?.addToRequestQueue(stringRequest)
    }

}
