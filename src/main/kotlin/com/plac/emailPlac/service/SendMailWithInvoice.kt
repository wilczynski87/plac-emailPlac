package com.plac.emailPlac.service

import com.plac.emailPlac.model.Invoice
import com.plac.emailPlac.service.mail.MailService
import com.plac.emailPlac.service.pdf.PdfGenerator
import com.plac.emailPlac.service.pdf.TemplateCreator
import jakarta.activation.DataSource
import org.springframework.stereotype.Service

@Service
class SendMailWithInvoice(val pdfGenerator: PdfGenerator, val templateCreator: TemplateCreator, val mailService: MailService) {

    fun send(invoice: Invoice) {
        // creating of invoice PDF in DataSource
        val pdfInvoice:DataSource = pdfGenerator.generatePdfInvoiceFromHtml(
            templateCreator.parseInvoiceForYard(invoice), // template
            "faktura${adjustStringToPath(invoice.invoiceNumber)}${adjustStringToPath(invoice.customer.name)}" // pdf name
        )
        mailService.sendEmailInvoiceYard(
            invoice.customer.name,
            invoice.customer.email,
            "Faktura za kontener przy Ostrowskiego 102",
            "",
            pdfInvoice)
    }

    private fun adjustStringToPath(invoiceNumber:String?): String {
        return invoiceNumber?.replace("/","")?.replace(" ", "")?.trim() ?: "";
    }
}