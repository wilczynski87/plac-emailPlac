package com.plac.emailPlac.service.mail

import org.springframework.stereotype.Service
import org.thymeleaf.TemplateEngine
import org.thymeleaf.context.Context

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
    fun sendInvoiceYard(name:String, imagesName:Map<String, String>?): String {

        val context: Context = Context()
        context.setVariable("name", name)
        imagesName?.forEach{ (key, value) -> context.setVariable(key, value)}

        val fragments: MutableSet<String> = HashSet()

        val html: String = templateEngine.process("mails/invoiceYard.html", fragments, context)

        return html
    }

}