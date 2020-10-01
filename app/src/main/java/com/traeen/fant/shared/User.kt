package com.traeen.fant.shared

/**
 * Represents a user in the Fant system
 */
class User(var id: Int, var firstName: String, var lastName: String = "",
           var email: String = "") {

    fun getFullName(): String{
        return "$firstName $lastName"
    }
}