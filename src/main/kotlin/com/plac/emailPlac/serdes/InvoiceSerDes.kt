package com.plac.emailPlac.serdes

import com.plac.emailPlac.model.Invoice
import com.plac.emailPlac.model.InvoicesToPrint
import org.apache.kafka.common.serialization.Serde
import org.apache.kafka.common.serialization.Serdes


class InvoiceSerDes {
    private fun InvoiceSerDes() {}
    fun invoice(): Serde<Invoice?>? {
        val serializer: JsonSerializer<Invoice> = JsonSerializer()
        val deserializer: JsonDeserializer<Invoice> = JsonDeserializer(
            Invoice::class.java
        )
        return Serdes.serdeFrom(serializer, deserializer)
    }
    fun invoiceList(): Serde<InvoicesToPrint?>? {
        val serializer: JsonSerializer<InvoicesToPrint> = JsonSerializer()
        val deserializer: JsonDeserializer<InvoicesToPrint> = JsonDeserializer<InvoicesToPrint>(
            InvoicesToPrint::class.java
        )
        return Serdes.serdeFrom(serializer, deserializer)
    }
}