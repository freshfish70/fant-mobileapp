package com.traeen.fant

object AuthenticationRepository {

    var loggedInUser: LoggedInUser? = null

    init {

    }

    fun isLoggedIn(): Boolean {
        return loggedInUser != null
    }

    fun logout() {
        loggedInUser = null
    }
}