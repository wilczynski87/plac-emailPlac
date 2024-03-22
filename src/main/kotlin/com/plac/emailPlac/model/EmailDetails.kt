package com.plac.emailPlac.model

import java.io.File

class EmailDetails(
    val name: String? = "Drogi Kliencie",
    val targetEmail: String,
    val subject: String? = "Faktura",
    val text: String?,
    val invoice: ByteArray?,
    ) {
}