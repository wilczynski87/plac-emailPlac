package com.plac.emailPlac.serdes

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import org.apache.kafka.common.errors.SerializationException
import org.apache.kafka.common.serialization.Serializer
import java.nio.charset.StandardCharsets


class JsonSerializer<T>  // default constructor needed by Kafka
    : Serializer<T?> {
    private val gson: Gson = GsonBuilder().create()

    override fun configure(props: Map<String?, *>?, isKey: Boolean) {
        // nothing to do
    }

    override fun serialize(topic: String, data: T?): ByteArray? {
        if (data == null) return null

        try {
            return gson.toJson(data).toByteArray(StandardCharsets.UTF_8)
        } catch (e: Exception) {
            throw SerializationException("Error serializing JSON message", e)
        }
    }

    override fun close() {
        // nothing to do
    }
}