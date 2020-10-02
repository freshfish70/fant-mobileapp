package com.traeen.fant.ui.login_display

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.TextView.OnEditorActionListener
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.traeen.fant.ApplicationViewModel
import com.traeen.fant.ApplicationViewModelFactory
import com.traeen.fant.R
import com.traeen.fant.network.HTTPAccess
import com.traeen.fant.network.VolleyHTTP
import kotlinx.android.synthetic.main.fragment_login.view.*


class LoginDisplayFragment : Fragment() {

    private var http: VolleyHTTP? = null

    private lateinit var loginModel: LoginDisplayViewModel

    private var returnToLastScreen = false

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

        val root = inflater.inflate(R.layout.fragment_login, container, false)
//        loginModel = ViewModelProvider.AndroidViewModelFactory(activity?.application!!).create<LoginDisplayViewModel>(LoginDisplayViewModel::class.java)
        loginModel = ViewModelProvider(this, LoginViewModelFactory(activity?.application)).get(
            LoginDisplayViewModel::class.java
        )

        returnToLastScreen = arguments?.getBoolean("return") ?: false

        val email = root.input_email
        val password = root.input_password
        val loginButton = root.register_submit
        val loginFieldsWatcher = object : TextWatcher {

            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                loginModel.loginDataChanged(email.text.toString(), password.text.toString())
            }
        }
        email.addTextChangedListener(loginFieldsWatcher)
        password.addTextChangedListener(loginFieldsWatcher)

        // Tries to login when text input is finished on password, and DONE action button
        // on keyboard is pressed
        password.setOnEditorActionListener(OnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                login(
                    email.text.toString(),
                    password.text.toString()
                )
            }
            false
        })

        loginButton.setOnClickListener {
            login(email.text.toString(), password.text.toString())
        }

        // Disable login button on start
        loginButton.isEnabled = false

        loginModel.formState.observe(viewLifecycleOwner, Observer {
            if (!it.isFormValid()) {
                loginButton.isEnabled = false
                email.error = if (it.mailError == null) it.mailError else getString(it.mailError!!)
                password.error =
                    if (it.passwordError == null) it.passwordError else getString(it.passwordError!!)
            } else {
                loginButton.isEnabled = true
            }
        })

        loginModel.loginResult.observe(viewLifecycleOwner, Observer {
            if (it.success) {
                val appModel = ViewModelProvider(
                    requireActivity(),
                    ApplicationViewModelFactory(activity?.application)
                ).get(
                    ApplicationViewModel::class.java
                )
                val navController = findNavController()
                if (returnToLastScreen) {
                    navController.popBackStack()
                } else {
                    navController.navigate(R.id.nav_home)
                }
                appModel.userLoggedIn()
            } else {
                Toast.makeText(context?.applicationContext, getText(it.error), Toast.LENGTH_SHORT)
                    .show()
            }
        })

        return root
    }

    private fun login(email: String, password: String) {
        loginModel.login(email, password)
    }

}
