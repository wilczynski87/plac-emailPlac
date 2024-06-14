package com.plac.emailPlac.service.mail

import com.plac.emailPlac.model.Invoice
import com.plac.emailPlac.model.InvoiceTemplate
import org.springframework.stereotype.Service
import org.thymeleaf.TemplateEngine
import org.thymeleaf.context.Context
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.*
import kotlin.collections.HashSet

@Service
class TemplateCreatorMail(
    private val templateEngine: TemplateEngine,
    ) {

    fun sendInvoiceContainer(name:String, imagesName:Map<String, String>?): String {

        val context: Context = Context()
        context.setVariable("name", name)
        imagesName?.forEach{ (key, value) -> context.setVariable(key, value)}

        val fragments: MutableSet<String> = HashSet()

        val html: String = templateEngine.process("invoiceContainer.html", fragments, context)

        return html
    }
    fun sendInvoiceYard(name:String, imagesName:Map<String, String>?, invoice: Invoice): String {

        val context: Context = Context()
        context.setVariable("invoiceTemplate", invoiceVariableParser(invoice))
        imagesName?.forEach{ (key, value) -> context.setVariable(key, value)}


        val fragments: MutableSet<String> = HashSet()

        val html: String = templateEngine.process("mails/invoiceYard.html", fragments, context)

        return html
    }

    fun sendInvoicesToPrint(): String {

        val context: Context = Context()

        val fragments: MutableSet<String> = HashSet()

        val html: String = templateEngine.process("mails/invoicesToPrint.html", fragments, context)

        return html
    }

    private fun invoiceVariableParser(invoice: Invoice?): InvoiceTemplate {
        return InvoiceTemplate(
            invoice?.customer?.salutation ?: "Drogi Kliencie",
            getDateFromString(invoice?.invoiceDate))
    }
    private fun getDateFromString(date:String?): String {
        val dateNum:Int = date?.substring(6,7)?.toInt() ?: LocalDate.now().dayOfMonth
        val c = Calendar.getInstance()
        val month_date = SimpleDateFormat("MMMM", Locale.of("pl", "PL"))
        c[Calendar.MONTH] = dateNum-1
        return month_date.format(c.time)
    }

}