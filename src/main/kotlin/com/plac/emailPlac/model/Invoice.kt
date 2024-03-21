package com.plac.emailPlac.model

import java.time.LocalDate

data class Invoice(
    val invoiceNumber:String,
    val invoiceDate:String,
    val seller:Seller,
    val customer:Customer,
    val products:Array<Product>,
    val vatAmountSum:String,
    val priceSum:String,
    val priceWithVatSum:String,
    val paymentDay:String = LocalDate.now().toString(),
    val mainAccount:String = "50 1950 0001 2006 0023 6241 0001",
    val taxAccount:String = "23 1950 0001 2006 0023 6241 0002",
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Invoice

        if (invoiceNumber != other.invoiceNumber) return false
        if (invoiceDate != other.invoiceDate) return false
        if (seller != other.seller) return false
        if (customer != other.customer) return false
        if (!products.contentEquals(other.products)) return false
        if (vatAmountSum != other.vatAmountSum) return false
        if (priceSum != other.priceSum) return false
        if (priceWithVatSum != other.priceWithVatSum) return false

        return true
    }

    override fun hashCode(): Int {
        var result = invoiceNumber.hashCode()
        result = 31 * result + invoiceDate.hashCode()
        result = 31 * result + seller.hashCode()
        result = 31 * result + customer.hashCode()
        result = 31 * result + products.contentHashCode()
        result = 31 * result + vatAmountSum.hashCode()
        result = 31 * result + priceSum.hashCode()
        result = 31 * result + priceWithVatSum.hashCode()
        return result
    }
}