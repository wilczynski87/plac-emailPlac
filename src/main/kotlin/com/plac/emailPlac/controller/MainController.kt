package com.plac.emailPlac.controller

import com.plac.emailPlac.model.*
import com.plac.emailPlac.service.SendMailWithInvoice
import com.plac.emailPlac.service.mail.MailService
import com.plac.emailPlac.service.pdf.PdfGenerator
import com.plac.emailPlac.service.pdf.TemplateCreator
import org.springframework.core.io.InputStreamSource
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.thymeleaf.context.Context
import java.io.ByteArrayInputStream
import java.io.InputStream
import java.time.LocalDate


@Controller
class MainController(val templateCreator: TemplateCreator, val pdfGenerator: PdfGenerator, val mailService: MailService, val sendMailWithInvoice: SendMailWithInvoice) {
    // Constans for invoice
    private final val karolAddress: Address = Address("ul. Ostrowskiego", "102", null, "Wrocław","53-238")
    private final val karolCompany: Seller = Seller("Karol Wilczynski",
        karolAddress,
        "8942957044",
        "wilczynski87@gmail.com",
        "+48 507 036 484",
        "50 1950 0001 2006 0023 6241 0001",
        "23 1950 0001 2006 0023 6241 0002",
        "11 2490 1044 0000 4200 8845 2192")
    // test values
    final val customer: Customer = Customer("znaki polskie: ĄĆÓŻŹŁ",
        karolAddress,
        "8942957044",
        "wilczynski87@gmail.com",
        "+48 507 036 484",)
    val invoiceDate:String = LocalDate.now().toString()
    final val paymentDay:String = LocalDate.now().plusDays(14).toString()
    final val products:Array<Product> = arrayOf(
        Product("Miejsce z kontenerem 6m", "350", "1", "23", "80,50", "350", "80,50","430,5"),
        Product("Miejsce z kontenerem 12m", "600", "1", "23", "138", "600", "138","738"),
    )
    val invoice: Invoice = Invoice("1/03/24",
        "13/03/2024",
        karolCompany,
        customer,
        products,
        "218,5",
        "950",
        "1168,5",
        paymentDay)
    val invoice2: Invoice = Invoice("DUPA2222",
        "DUPAAA",
        karolCompany,
        customer,
        products,
        "218,5",
        "950",
        "1168,5",
        paymentDay)
    val emailWithByteArray: EmailDetails = EmailDetails(
        "test" ,
        "parkingostrowskiego@gmail.com",
        "test",
        "znaki polskie: ĄĆÓŻŹŁ",
        null)

    @GetMapping("/test")
    fun appHealtCheck(): ResponseEntity<String> {
        return ResponseEntity.ok("I am 'EmailPlac'!")
    }

    @GetMapping("/testPdfGenerator")
    fun testPdf():ResponseEntity<Any> {
        println("\n\n/testPdfGenerator")
        val template = templateCreator.parseInvoiceForContainers(invoice)
        println("\n\nTemplate:")
        println(template)
//        val pdf = pdfGenerator.generatePdfInvoiceFromHtml(template, "baTest")
        val pdf = pdfGenerator.generatePdfFileFromHtml(template, "baTest")
        println("\n\nPDF:")
        println(pdf)
        return ResponseEntity.ok(template)
    }

    @GetMapping("/testSendMailWithPdf")
    fun testSendMailWithPdf():ResponseEntity<Any> {
        val template = templateCreator.parseInvoiceForContainers(invoice)
        println("stworzenie templata faktury")
//        val pdf = pdfGenerator.generatePdfInvoiceFromHtml(template, "invoiceTest")
        val pdf = pdfGenerator.generatePdfByteArrayFromHtmlTo(template, "invoiceTest")
        println("pdfGenerator zadzialal ok")
        mailService.sendEmailInvoiceYard(
            emailWithByteArray.name,
            emailWithByteArray.targetEmail,
            emailWithByteArray.subject,
            emailWithByteArray.text,
            pdf,
            "invoice name",
            "invoice template",
        )
        println("mail wyslany! ")
        return ResponseEntity.ok(pdf)
    }

    @GetMapping("/testSendMailWithInvoice")
    fun testSendMailWithInvoice():ResponseEntity<Any> {
        sendMailWithInvoice.send(invoice)
        return ResponseEntity.ok("poszedł mail z pdf")
    }

    @GetMapping("/invoicesToPrint")
    fun invoicesToPrintTest():ResponseEntity<Any> {
        sendMailWithInvoice.sendToPrint(listOf(invoice, invoice2))

        return ResponseEntity.ok("poszedł mail z pdf")
    }

}