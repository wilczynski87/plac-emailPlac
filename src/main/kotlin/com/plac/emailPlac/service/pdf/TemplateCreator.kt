package com.plac.emailPlac.service.pdf

import com.plac.emailPlac.model.*
import org.springframework.stereotype.Service
import org.thymeleaf.TemplateEngine
import org.thymeleaf.context.Context
import org.thymeleaf.spring6.templateresolver.SpringResourceTemplateResolver
import org.thymeleaf.spring6.view.ThymeleafViewResolver
import org.thymeleaf.templatemode.TemplateMode
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver
import org.thymeleaf.templateresolver.ITemplateResolver
import org.thymeleaf.util.DateUtils
import java.text.DateFormat
import java.time.LocalDate
import java.util.Date


@Service
class TemplateCreator(private val templateEngine: TemplateEngine) {

    // Constans for invoice
    private final val karolAddress:Address = Address("ul. Ostrowskiego", "102", null, "Wrocław","53-238")
    private final val karolCompany:Seller = Seller("Karol Wilczynski",
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
    val invoice:Invoice = Invoice("1/03/24",
        "13/03/2024",
        karolCompany,
        customer,
        products,
        "218,5",
        "950",
        "1168,5",
        paymentDay)


    fun parseThymeleafTemplate(): String {

        val fragments: MutableSet<String> = HashSet()

        val context: Context = Context()
        context.setVariable("test1", "To jest test")

        return templateEngine.process("test.html", fragments, context)
    }

    fun parseInvoiceForContainers(invoice:Invoice): String {

        val fragments: MutableSet<String> = HashSet()

        val context: Context = Context()
        context.setVariable("invoice", invoice)

        return templateEngine.process("fakturaKontenery.html", fragments, context)
    }
    fun parseInvoiceForYard(invoice:Invoice): String {

        val fragments: MutableSet<String> = HashSet()

        val context: Context = Context()
        context.setVariable("invoice", invoice)
//        println(templateEngine.process("fakturaKontenery.html", fragments, context)) // check invoice

        return templateEngine.process("fakturaKontenery.html", fragments, context)
    }
}