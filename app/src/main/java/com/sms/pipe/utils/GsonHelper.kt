package com.sms.pipe.utils

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


object GsonHelper {

    @PublishedApi internal
    val gson = Gson()


    //convert a map to a data class
    inline fun <reified T> Map<String, Any?>.deserialize(): T {
        return convert()
    }

    //convert a data class to a map
    fun <T> T.serializeToMap(): Map<String, Any?> {
        return convert()
    }

    /**
     * convert an object of type I to O
     * using json
     */
    @PublishedApi internal
     inline fun <I, reified O> I.convert(): O {
        val json = gson.toJson(this)
        return gson.fromJson(json, object : TypeToken<O>() {}.type)
    }
}