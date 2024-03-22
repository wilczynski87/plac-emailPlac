package com.plac.emailPlac.controller

import com.plac.emailPlac.model.*
import com.plac.emailPlac.service.mail.MailService
import com.plac.emailPlac.service.pdf.PdfGenerator
import com.plac.emailPlac.service.pdf.TemplateCreator
import org.springframework.core.io.InputStreamSource
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import java.io.ByteArrayInputStream
import java.io.InputStream
import java.time.LocalDate


@Controller
class MainController(val templateCreator: TemplateCreator, val pdfGenerator: PdfGenerator, val mailService: MailService) {
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
    final val customer: Customer = Customer("Customer name",
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
    val emailWithByteArray: EmailDetails = EmailDetails(
        "test" ,
        "parkingostrowskiego@gmail.com",
        "test",
        null,
        null)

    @GetMapping("/test")
    fun appHealtCheck(): ResponseEntity<String> {
        return ResponseEntity.ok("I am 'EmailPlac'!")
    }

    @GetMapping("/testPdfGenerator")
    fun testPdf():ResponseEntity<Any> {
        val template = templateCreator.parseInvoiceForContainers(invoice)
//        println(template)
        val pdf = pdfGenerator.generatePdfFromHtml(template, "baTest")
        println(pdf)
        return ResponseEntity.ok(pdf)
    }

    @GetMapping("/testSendMailWithPdf")
    fun testSendMailWithPdf():ResponseEntity<Any> {
        val template = templateCreator.parseInvoiceForContainers(invoice)
//        println(template)
        val pdf = pdfGenerator.generatePdfInvoiceFromHtml(template, "invoiceTest")
        mailService.sendEmailTEST(emailWithByteArray.name, emailWithByteArray.targetEmail, emailWithByteArray.subject, emailWithByteArray.text, pdf)
        println(pdf)
        return ResponseEntity.ok(pdf)
    }

}