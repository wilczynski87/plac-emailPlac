package com.plac.emailPlac.service.mail

import jakarta.mail.internet.MimeMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.MimeMessageHelper
import org.springframework.stereotype.Service
import java.io.File

@Service
class MailService(
    private val emailSender: JavaMailSender,
    private val mailTemplateCreator: TemplateCreatorMail,
    ) {
    fun sendEmailInvoiceYard(
        name: String,
        targetEmail: String,
        subject: String?,
        text: String?,
        invoice: File,
    ) {
//        val imgNames:Map<String, String> = mapOf(
//            "logo1" to logo1.filename!!,
//            "foto1" to foto1.filename!!,
//            "foto2" to foto2.filename!!,
//            "fundatorzy" to fundatorzy.filename!!,
//            "logoCwynar" to logoCwynar.filename!!,
//            "smutnaPani" to smutnaPani.filename!!,
//            )

        val message: MimeMessage = emailSender.createMimeMessage()
        val helper = MimeMessageHelper(message, true)
        helper.setTo(targetEmail)
        helper.setFrom("parkingostrowskiego@gmail.com", "Od Karol Wilczynski z Ostrowskiego 102")
        helper.setSubject(subject ?: "Faktura z Ostrowskiego 102")
        helper.setText(mailTemplateCreator.sendInvoiceYard(name, null), true)
        helper.addAttachment(invoice.name, invoice)
//        addResourcesInline(helper, logo1, foto1, foto2, fundatorzy, logoCwynar, smutnaPani)
        emailSender.send(message)
    }

    fun sendEmailInvoiceContainer(
        name: String,
        targetEmail: String,
        subject: String?,
        text: String?,
        invoice: File,
    ) {
//        val imgNames:Map<String, String> = mapOf(
//            "logo1" to logo1.filename!!,
//            "foto1" to foto1.filename!!,
//            "foto2" to foto2.filename!!,
//            "fundatorzy" to fundatorzy.filename!!,
//            "logoCwynar" to logoCwynar.filename!!,
//            "smutnaPani" to smutnaPani.filename!!,
//            )

        val message: MimeMessage = emailSender.createMimeMessage()
        val helper = MimeMessageHelper(message, true)
        helper.setTo(targetEmail)
        helper.setFrom("parkingostrowskiego@gmail.com", "Od Karol Wilczynski z Ostrowskiego 102")
        helper.setSubject(subject ?: "Faktura z Ostrowskiego 102")
        helper.setText(mailTemplateCreator.sendInvoiceContainer(name, null), true)
        helper.addAttachment(invoice.name, invoice)
//        addResourcesInline(helper, logo1, foto1, foto2, fundatorzy, logoCwynar, smutnaPani)
        emailSender.send(message)
    }
}