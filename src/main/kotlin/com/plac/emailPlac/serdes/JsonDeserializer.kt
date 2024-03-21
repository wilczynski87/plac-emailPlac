package com.plac.emailPlac.serdes

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import org.apache.kafka.common.errors.SerializationException
import org.apache.kafka.common.serialization.Deserializer
import java.lang.reflect.Type
import java.nio.charset.StandardCharsets

class JsonDeserializer<T> : Deserializer<T?> {
    private val gson: Gson = GsonBuilder().create()

    private var destinationClass: Class<T>? = null
    private var reflectionTypeToken: Type? = null

    constructor(destinationClass: Class<T>?) {
        this.destinationClass = destinationClass
    }

    constructor(reflectionTypeToken: Type?) {
        this.reflectionTypeToken = reflectionTypeToken
    }

    override fun configure(props: Map<String?, *>?, isKey: Boolean) {
        // nothing to do
    }

    override fun deserialize(topic: String, bytes: ByteArray): T? {
        if (bytes == null) return null

        try {
            val type = if (destinationClass != null) destinationClass!! else reflectionTypeToken!!
            return gson.fromJson(String(bytes, StandardCharsets.UTF_8), type)
        } catch (e: Exception) {
            throw SerializationException("Error deserializing message", e)
        }
    }

    override fun close() {
        // nothing to do
    }
}