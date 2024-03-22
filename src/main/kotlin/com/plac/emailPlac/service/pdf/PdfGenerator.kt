package com.plac.emailPlac.service.pdf

//import org.xhtmlrenderer.pdf.ITextRenderer
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

    fun generatePdfFromHtml(html: String?, invoiceName:String?): File? {
        val name: String = "${if(invoiceName == null || invoiceName == "") "invoice" else invoiceName}.pdf"
//        println(pdfDirectory)
        val outputFolder = (System.getProperty("user.home") + File.separator).toString() + name
        val outputStream: OutputStream = FileOutputStream(outputFolder)

        val renderer = ITextRenderer()
        renderer.setDocumentFromString(html!!)
        renderer.layout()
        renderer.createPDF(outputStream)

        outputStream.close()

        val file: File = File(pdfDirectory, name)
        return file
    }

    fun generatePdfFromHtmlToByteArray(html: String?, invoiceName:String?): ByteArray {
        val byteArrayOutputStream: ByteArrayOutputStream = ByteArrayOutputStream()

        val renderer = ITextRenderer()
        renderer.setDocumentFromString(html!!)
        renderer.layout()
        renderer.createPDF(byteArrayOutputStream)

        val byteArray: ByteArray = byteArrayOutputStream.toByteArray()
        byteArrayOutputStream.close()

        println(byteArray)
        return byteArray
    }

    fun generatePdfInvoiceFromHtml(html: String?, invoiceName:String?): DataSource {
        val outputStream: ByteArrayOutputStream = ByteArrayOutputStream()

        val renderer = ITextRenderer()
        renderer.setDocumentFromString(html!!)
        renderer.layout()
        renderer.createPDF(outputStream)

        val dataSource: DataSource = ByteArrayDataSource(outputStream.toByteArray(), "application/pdf")
        outputStream.close()


        return dataSource
    }


}