package com.plac.emailPlac.controller

import com.plac.emailPlac.config.TO_API
import com.plac.emailPlac.config.TO_EMAIL_PLAC
import com.plac.emailPlac.model.Invoice
import com.plac.emailPlac.serdes.InvoiceSerDes
import com.plac.emailPlac.service.PdfGenerator
import com.plac.emailPlac.service.TemplateCreator
import org.apache.kafka.common.serialization.Serde
import org.apache.kafka.common.serialization.Serdes
import org.apache.kafka.streams.StreamsBuilder
import org.apache.kafka.streams.kstream.Consumed
import org.apache.kafka.streams.kstream.KStream
import org.springframework.context.annotation.Bean
import org.springframework.kafka.support.serializer.JsonSerde
import org.springframework.stereotype.Component


@Component
class KafkaController(val pdfGenerator: PdfGenerator, val templateCreator: TemplateCreator) {

    private val STRING_SERDE: Serde<String> = Serdes.String()
    private val INVOICE_SERDE: Serde<Invoice?>? = InvoiceSerDes().invoice()

    @Bean
    fun kafkaStreamTopollogy(kStreamBuilder: StreamsBuilder): KStream<String, Invoice?>? {
        val stream: KStream<String, Invoice?>? = kStreamBuilder.stream(TO_EMAIL_PLAC, Consumed.with(STRING_SERDE, INVOICE_SERDE))
        if (stream != null) {
            stream
                .peek { x, y -> println("moj stream: x: $x i y: $y") }
    //            .map { x, y -> KeyValue("x", "Faktura wyslana" ) }
                .peek { x, invoice -> if(invoice is Invoice) pdfGenerator.generatePdfFromHtml(templateCreator.parseInvoiceForContainers(invoice),
                    "faktura${adjustStringToPath(invoice.invoiceNumber)}${adjustStringToPath(invoice.customer.name)}") }
                .to(TO_API)
        }
        return stream
    }

    private fun adjustStringToPath(invoiceNumber:String?): String {
        return invoiceNumber?.replace("/","")?.replace(" ", "")?.trim() ?: "";
    }
}