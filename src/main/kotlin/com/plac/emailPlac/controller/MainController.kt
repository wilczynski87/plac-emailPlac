package com.plac.emailPlac.controller

import com.plac.emailPlac.TO_API
import com.plac.emailPlac.TO_EMAIL_PLAC
import com.plac.emailPlac.service.PdfGenerator
import com.plac.emailPlac.service.TemplateCreator
import org.apache.kafka.streams.StreamsBuilder
import org.apache.kafka.streams.kstream.KStream
import org.springframework.context.annotation.Bean
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping


@Controller
class MainController(val templateCreator: TemplateCreator, val pdfGenerator: PdfGenerator) {

    @GetMapping("/test")
    fun appHealtCheck(): ResponseEntity<String> {
        return ResponseEntity.ok("I am 'EmailPlac'!")
    }

    @GetMapping("/testPdfGenerator")
    fun testPdf():ResponseEntity<Any> {
        val template = templateCreator.parseInvoiceForContainers()
//        println(template)
        val pdf = pdfGenerator.generatePdfFromHtml(template, "")
        println(pdf)
        return ResponseEntity.ok(pdf)
    }

}