package com.plac.emailPlac.serdes

import com.plac.emailPlac.model.Invoice
import org.apache.kafka.common.serialization.Serde
import org.apache.kafka.common.serialization.Serdes


class InvoiceSerDes {
    private fun InvoiceSerDes() {}
    public fun invoice(): Serde<Invoice?>? {
        val serializer: JsonSerializer<Invoice> = JsonSerializer<Invoice>()
        val deserializer: JsonDeserializer<Invoice> = JsonDeserializer<Invoice>(
            Invoice::class.java
        )
        return Serdes.serdeFrom(serializer, deserializer)
    }

//    fun GenreCount(): Serde<GenreCount> {
//        val serializer: JsonSerializer<GenreCount> = JsonSerializer<GenreCount>()
//        val deserializer: JsonDeserializer<GenreCount> = JsonDeserializer<GenreCount>(
//            GenreCount::class.java
//        )
//        return Serdes.serdeFrom(serializer, deserializer)
//    }
}