package com.plac.emailPlac.controller

import com.plac.emailPlac.config.TO_API
import com.plac.emailPlac.config.TO_EMAIL_PLAC
import com.plac.emailPlac.model.Invoice
import com.plac.emailPlac.serdes.InvoiceSerDes
import com.plac.emailPlac.service.SendMailWithInvoice
import com.plac.emailPlac.service.pdf.PdfGenerator
import com.plac.emailPlac.service.pdf.TemplateCreator
import org.apache.kafka.common.serialization.Serde
import org.apache.kafka.common.serialization.Serdes
import org.apache.kafka.streams.StreamsBuilder
import org.apache.kafka.streams.kstream.Consumed
import org.apache.kafka.streams.kstream.KStream
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Component


@Component
class KafkaController(val sendMailWithInvoice: SendMailWithInvoice) {

    private val STRING_SERDE: Serde<String> = Serdes.String()
    private val INVOICE_SERDE: Serde<Invoice?>? = InvoiceSerDes().invoice()

    @Bean
    fun kafkaStreamTopollogy(kStreamBuilder: StreamsBuilder): KStream<String, Invoice?>? {
        val stream: KStream<String, Invoice?>? = kStreamBuilder.stream(TO_EMAIL_PLAC, Consumed.with(STRING_SERDE, INVOICE_SERDE))
        stream?.peek { x, y -> println("moj stream: x: $x i y: $y") }
            ?.peek { _, invoice ->
                if(invoice is Invoice)
                    sendMailWithInvoice.send(invoice)
            }
            ?.to(TO_API)
        return stream
    }
}