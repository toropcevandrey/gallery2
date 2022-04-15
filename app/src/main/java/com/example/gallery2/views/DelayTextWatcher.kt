package com.example.gallery2.views

import android.text.Editable
import android.text.TextWatcher
import java.util.*

class DelayTextWatcher(private val onTextChanged: OnTextChanged) : TextWatcher {

    fun interface OnTextChanged {
        fun onTextChanged(text: String)
    }

    private var timer = Timer()
    private var isTextChanged = true

    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) = Unit

    override fun onTextChanged(p0: CharSequence?, p1: Int, countBefore: Int, countAfter: Int)  {
        isTextChanged = countAfter - 1 == countBefore || countAfter + 1 == countBefore
    }

    override fun afterTextChanged(editable: Editable?) {
        if (isTextChanged) {
            timer.cancel()
            timer = Timer()
            timer.schedule(object : TimerTask() {
                override fun run() {
                    onTextChanged.onTextChanged(editable.toString())
                }
            }, DELAY_BEFORE_EXECUTE)
        }
    }

    companion object {
        private const val DELAY_BEFORE_EXECUTE = 500L
    }
}