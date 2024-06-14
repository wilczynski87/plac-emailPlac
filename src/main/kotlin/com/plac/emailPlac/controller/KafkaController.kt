package com.plac.emailPlac.controller

import com.plac.emailPlac.config.TO_API
import com.plac.emailPlac.config.TO_EMAIL_PLAC
import com.plac.emailPlac.config.TO_EMAIL_PLAC_PRINT
import com.plac.emailPlac.model.Invoice
import com.plac.emailPlac.model.InvoicesToPrint
import com.plac.emailPlac.serdes.InvoiceSerDes
import com.plac.emailPlac.service.SendMailWithInvoice
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
    private val INVOICE_LIST_SERDE: Serde<InvoicesToPrint?>? = InvoiceSerDes().invoiceList()

    @Bean
    fun kafkaStreamTopollogy(kStreamBuilder: StreamsBuilder): KStream<String, Invoice?>? {
        val stream: KStream<String, Invoice?>? = kStreamBuilder.stream(TO_EMAIL_PLAC, Consumed.with(STRING_SERDE, INVOICE_SERDE))

        stream?.peek { x, y -> println("moj stream: x: $x i y: $y") }
            ?.peek { _, invoice ->
                if(invoice is Invoice) sendMailWithInvoice.send(invoice)
            }
            ?.to(TO_API)
        return stream
    }
    @Bean
    fun kafkaStreamToPrint(kStreamBuilder: StreamsBuilder): KStream<String, InvoicesToPrint?>? {
        val stream: KStream<String, InvoicesToPrint?>? = kStreamBuilder.stream(TO_EMAIL_PLAC_PRINT, Consumed.with(STRING_SERDE, INVOICE_LIST_SERDE))

        stream
            ?.peek { _, invoices ->
                if(invoices is InvoicesToPrint && invoices.invoices.isNotEmpty())
                    sendMailWithInvoice.sendToPrint(invoices.invoices)
            }
            ?.to(TO_API)
        return stream
    }
}