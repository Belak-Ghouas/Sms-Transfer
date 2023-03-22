package com.sms.pipe.view

import android.app.Activity
import android.os.Build
import android.util.DisplayMetrics
import androidx.fragment.app.Fragment


/**
 * return the height on px
 */
fun Activity.getScreenHeight():Int{
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        val windowMetrics = this.windowManager.currentWindowMetrics
        windowMetrics.bounds.height()
    } else {
        val displayMetrics = DisplayMetrics()
        this.windowManager.defaultDisplay.getMetrics(displayMetrics)
        displayMetrics.heightPixels
    }
}

fun Fragment.getScreenHeight():Int{
    return this.requireActivity().getScreenHeight()
}