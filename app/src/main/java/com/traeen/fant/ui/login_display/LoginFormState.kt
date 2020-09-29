package com.traeen.fant.ui.login_display

class LoginFormState() {

    var mailError: Int? = null
    var passwordError: Int? = null

    fun isFormValid(): Boolean{
        return (mailError == null && passwordError == null)
    }

}