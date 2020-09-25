package com.traeen.fant.network

import android.content.Context

interface HTTPAccess {
    fun get() : VolleyHTTP
}