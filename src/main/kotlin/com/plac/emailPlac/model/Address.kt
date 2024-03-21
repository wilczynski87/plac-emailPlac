package com.plac.emailPlac.model

class Address(val street:String,
              val home:String,
              val local:String?,
              val city:String,
              val post:String,
              val country:String = "PL") {
    override fun toString(): String {
        return "Address(street='$street', home='$home', local=$local, city='$city', post='$post', country='$country')"
    }
}