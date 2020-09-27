package com.traeen.fant.constants

import com.traeen.fant.constants.Constants

class Endpoints {
    companion object{
        val GET_ITEMS = fun(page: Int): String { return Constants.SERVER_ADDRESS + "shop/getitems?pag=${page}" }
        val GET_IMAGE = fun(id: Int, width: Int): String { return Constants.SERVER_ADDRESS + "resource/image/${id}?width=${width}" }
        val POST_LOGIN = fun(): String { return Constants.SERVER_ADDRESS + "authentication/login" }
    }
}