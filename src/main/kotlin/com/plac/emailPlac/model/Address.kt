package com.plac.emailPlac.model

class Address(val street:String,
              val home:Int,
              val local:Int?,
              val city:String,
              val post:String,
              val country:String = "PL") {

}