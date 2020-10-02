package com.traeen.fant

import android.text.Editable
import android.text.TextWatcher

/**
 * TextWatcher helper method with callback handling of text change
 */
fun textWatcherTextChanged(validator: (s: CharSequence?) -> Unit): TextWatcher {
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