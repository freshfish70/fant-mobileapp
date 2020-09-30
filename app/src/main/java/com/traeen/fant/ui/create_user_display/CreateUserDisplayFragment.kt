package com.traeen.fant.ui.create_user_display

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import com.traeen.fant.R
import com.traeen.fant.constants.Endpoints
import com.traeen.fant.network.HTTPAccess
import com.traeen.fant.network.VolleyHTTP
import kotlinx.android.synthetic.main.fragment_create_user.view.*

class CreateUserDisplayFragment : Fragment() {

    private var http: VolleyHTTP? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (activity is HTTPAccess) {
            http = (activity as HTTPAccess).getHTTPInstace()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_create_user, container, false)

        val firstName = root.input_first_name
        val lastName = root.input_last_name
        val email = root.input_email
        val password = root.input_password
        val rePassword = root.input_retype_password
        val registerButton = root.register_submit

        fun isFormValid(): Boolean {
            return (firstName.error == null && lastName.error == null && email.error == null && password.error == null && rePassword.error == null)
        }

        registerButton.isEnabled = false
        firstName.addTextChangedListener(watcher {
            if (it != null) {
                if (it.toString().isBlank()) firstName.error =
                    getString(R.string.text_can_not_be_blank)
                else firstName.error = null
            }
            registerButton.isEnabled = isFormValid()

        })

        lastName.addTextChangedListener(watcher {
            if (it != null) {
                if (it.toString().isBlank()) lastName.error =
                    getString(R.string.text_can_not_be_blank)
                else lastName.error = null
            }
            registerButton.isEnabled = isFormValid()

        })

        email.addTextChangedListener(watcher {
            if (it != null) {
                when {
                    it.toString().isBlank() -> email.error =
                        getString(R.string.text_can_not_be_blank)
                    !Patterns.EMAIL_ADDRESS.matcher(it.toString()).matches() -> email.error =
                        getString(R.string.text_invalid_email)
                    else -> email.error = null
                }
                registerButton.isEnabled = isFormValid()

            }
        })

        password.addTextChangedListener(watcher {
            if (it != null) {
                print(it.toString())
                when {
                    it.toString().isEmpty() -> password.error =
                        getString(R.string.text_can_not_be_blank)
                    (it.toString().length < 6) -> password.error =
                        getString(R.string.text_invalid_password)
                    else -> password.error = null
                }
                registerButton.isEnabled = isFormValid()

            }
        })

        rePassword.addTextChangedListener(watcher {
            if (it != null) {
                when {
                    it.toString().isEmpty() -> {
                        rePassword.error = getString(R.string.text_can_not_be_blank)
                    }
                    it.toString() != password.text.toString() -> {
                        rePassword.error = getString(R.string.text_password_not_equal)
                    }
                    else -> rePassword.error = null
                }
                registerButton.isEnabled = isFormValid()
            }
        })

        registerButton.setOnClickListener {
            register(firstName.text.toString(), lastName.text.toString(), email.text.toString(), password.text.toString())
        }

        return root
    }

    private fun register(firstName: String, lastName: String, email: String, password: String){
        val stringRequest =
            object : StringRequest(
                Request.Method.POST, Endpoints.POST_REGISTER(),
                { response ->
                    val jsonObject: JsonObject = JsonParser.parseString(response).asJsonObject
                    val error = jsonObject.get("error")
                    if (error == null) {
                        Toast.makeText(context?.applicationContext, getText(R.string.text_user_was_created), Toast.LENGTH_LONG)
                            .show()
                        val navController = findNavController()
                        navController.navigate(R.id.nav_login)
                    } else {
                        Toast.makeText(context?.applicationContext, error.asJsonObject.get("message").asString, Toast.LENGTH_LONG)
                            .show()
                    }
                },
                {

                }) {
                @Throws(AuthFailureError::class)
                override fun getHeaders(): Map<String, String> {
                    val params: MutableMap<String, String> = HashMap()
                    params["firstname"] = firstName
                    params["lastname"] = lastName
                    params["email"] = email
                    params["password"] = password
                    params["content-type"] = "application/json"
                    return params
                }
            }
        http?.addToRequestQueue(stringRequest)
    }

    private fun watcher(validator: (s: CharSequence?) -> Unit): TextWatcher {
        return object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(
                s: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                validator(s)
            }
        }
    }

}
