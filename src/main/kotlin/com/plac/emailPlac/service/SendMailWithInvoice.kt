package com.plac.emailPlac.service

import com.plac.emailPlac.model.Invoice
import com.plac.emailPlac.service.mail.MailService
import com.plac.emailPlac.service.mail.TemplateCreatorMail
import com.plac.emailPlac.service.pdf.PdfGenerator
import com.plac.emailPlac.service.pdf.TemplateCreator
import jakarta.activation.DataSource
import org.springframework.boot.autoconfigure.amqp.RabbitProperties.Stream
import org.springframework.core.env.ConfigurableEnvironment
import org.springframework.stereotype.Service
import java.util.*
import java.util.function.Predicate

@Service
class SendMailWithInvoice(
    val pdfGenerator: PdfGenerator,
    val templateCreator: TemplateCreator,
    val mailService: MailService,
    val mailTemplateCreator: TemplateCreatorMail,
    private val env: ConfigurableEnvironment,
) {

    fun send(invoice: Invoice) {
        // creating of invoice PDF in DataSource
        val pdfInvoice:ByteArray = pdfGenerator.generatePdfByteArrayFromHtmlTo(
            templateCreator.parseInvoiceForYard(invoice), // Invoice template
            invoiceName(invoice), // pdf name
        )
        val mailTemplateHTML:String = mailTemplateCreator.sendInvoiceYard(name = "Drogi Kliencie", null, invoice)

        mailService.sendEmailInvoiceYard(
            invoice.customer.name,
            if(checkProfileIsDev()) "wilczynski87@gmail.com" else invoice.customer.email,
            "Faktura za kontener przy Ostrowskiego 102",
            "",
            pdfInvoice,
            invoiceName(invoice),
            mailTemplateHTML,
        )
    }

    private fun adjustStringToPath(invoiceNumber:String?): String {
        return invoiceNumber?.replace("/","")?.replace(" ", "")?.trim() ?: "";
    }
    private fun invoiceName(invoice:Invoice?):String {
        return "faktura${adjustStringToPath(invoice?.invoiceNumber)}${adjustStringToPath(invoice?.customer?.name)}"
    }

    private fun checkProfileIsDev(): Boolean {
        return Arrays.stream(env.activeProfiles)
            .filter(Predicate.isEqual("dev"))
            .findFirst()
            .isPresent
    }
}