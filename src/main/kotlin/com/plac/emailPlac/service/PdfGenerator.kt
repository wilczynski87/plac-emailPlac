package com.plac.emailPlac.service

//import org.xhtmlrenderer.pdf.ITextRenderer
import org.springframework.stereotype.Service
import org.xhtmlrenderer.pdf.ITextRenderer
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

}