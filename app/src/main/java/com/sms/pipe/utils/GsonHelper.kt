package com.sms.pipe.utils

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


object GsonHelper {

    @PublishedApi internal
    val gson = Gson()

    /**
     *
     */

    fun <I> I.toJson(): String {
        return gson.toJson(this)
    }

    inline fun < reified O> String.deserialize():O{
        return gson.fromJson(this, object : TypeToken<O>() {}.type)
    }
}