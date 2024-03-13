package com.plac.emailPlac.model

data class Product(
    val productName:String,
    val unitPrice:String,
    val quantity:String,
    val vatRate:String,
    val vatAmount:String,
    val price:String,
    val vat:String,
    val priceWithVat:String,
) {
}