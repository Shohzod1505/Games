package ru.itis.kpfu.games.utils

import com.google.android.material.textfield.TextInputLayout

fun TextInputLayout.checkField(error: String): Boolean {
    return if (this.editText?.text.toString().isEmpty()) {
        this.error = error
        false
    } else {
        this.error = null
        true
    }
}
