package com.plac.emailPlac.service.pdf

import com.lowagie.text.pdf.BaseFont
import jakarta.activation.DataSource
import jakarta.mail.util.ByteArrayDataSource
import org.springframework.stereotype.Service
import org.xhtmlrenderer.pdf.ITextRenderer
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream


@Service
class PdfGenerator {
//    val pdfDirectory: String = "C:\\Users\\wilcz\\OneDrive\\Documents\\VCSProjects\\emailPlac\\src\\main\\resources\\"
    val pdfDirectory: String = (System.getProperty("user.home") + File.separator).toString()

    fun generatePdfFileFromHtml(html: String?, invoiceName:String?): File? {
        val name: String = "${if(invoiceName == null || invoiceName == "") "invoice" else invoiceName}.pdf"
//        println(pdfDirectory)
        val outputFolder = (System.getProperty("user.home") + File.separator) + name
        val outputStream: OutputStream = FileOutputStream(outputFolder)

        val renderer = ITextRenderer()

        renderer.fontResolver.addFont("templates/fonts/AlexBrush-Regular.ttf", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED)
        renderer.fontResolver.addFont("templates/fonts/Voces-Regular.ttf", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED)
        renderer.setDocumentFromString(html!!)

        println(renderer.fontResolver.fonts.values.forEach { x -> println(x.name) }) // check if font is in

        renderer.layout()
        renderer.createPDF(outputStream)

        outputStream.close()

        val file: File = File(pdfDirectory, name)
        return file
    }

    fun generatePdfByteArrayFromHtmlTo(html: String?, invoiceName:String? = "faktura"): ByteArray {
        val byteArrayOutputStream: ByteArrayOutputStream = ByteArrayOutputStream()

        val renderer = ITextRenderer()
        renderer.fontResolver.addFont("templates/fonts/AlexBrush-Regular.ttf", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED)
        renderer.fontResolver.addFont("templates/fonts/Voces-Regular.ttf", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED)

        renderer.setDocumentFromString(html!!)
        renderer.layout()
        renderer.createPDF(byteArrayOutputStream)

        val byteArray: ByteArray = byteArrayOutputStream.toByteArray()
        byteArrayOutputStream.close()

        return byteArray
    }

    fun generatePdfToPrint(htmls: List<String>, invoiceName:String? = "faktura"): ByteArray {
        val byteArrayOutputStream: ByteArrayOutputStream = ByteArrayOutputStream()

        val renderer = ITextRenderer()
        renderer.fontResolver.addFont("templates/fonts/AlexBrush-Regular.ttf", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED)
        renderer.fontResolver.addFont("templates/fonts/Voces-Regular.ttf", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED)

        renderer.setDocumentFromString(htmls[0]);
        renderer.layout();
        renderer.createPDF(byteArrayOutputStream, false);

        for(html in htmls) {
            if(html == htmls[0]) continue // skip first
            renderer.setDocumentFromString(html)
            renderer.layout()
            renderer.writeNextDocument()
        }

        renderer.finishPDF()

        val byteArray: ByteArray = byteArrayOutputStream.toByteArray()
        byteArrayOutputStream.close()

        return byteArray
    }
}