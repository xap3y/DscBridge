package me.xap3y.dscbridge.ws

import com.google.gson.JsonElement
import com.google.gson.JsonObject

class WsResObj {
    private val jsonObject = JsonObject()

    fun addProperty(key: String, value: String): WsResObj {
        jsonObject.addProperty(key, value)
        return this
    }

    fun addProperty(key: String, value: Number): WsResObj {
        jsonObject.addProperty(key, value)
        return this
    }

    fun addProperty(key: String, value: Boolean): WsResObj {
        jsonObject.addProperty(key, value)
        return this
    }

    fun addArr(key: String, values: JsonElement): WsResObj {
        jsonObject.add(key, values)
        return this
    }

    fun build(): JsonObject {
        return jsonObject
    }
}