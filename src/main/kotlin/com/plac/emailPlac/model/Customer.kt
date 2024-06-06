package com.plac.emailPlac.model

open class Customer(val name:String,
               val address:Address,
               val nip:String,
               val email:String,
               val phone:String,
               val salutation:String = "Drogi Kliencie",
    ) {
    override fun toString(): String {
        return "Customer(name='$name', address=$address, nip='$nip', email='$email', phone='$phone', salutation='$salutation')"
    }
}