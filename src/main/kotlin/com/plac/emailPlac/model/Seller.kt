package com.plac.emailPlac.model

//class Seller(val name:String,
//             val address:Address,
//             val nip:String,
//             val email:String,
//             val phone:String,
//             val mainAccount:String,
//             val vatAccount:String,
//             val prvAccount:String,
//)  {
class Seller(name:String,
             address:Address,
             nip:String,
             email:String,
             phone:String,
             val mainAccount:String,
             val vatAccount:String,
             val prvAccount:String,
) : Customer(name, address, nip, email, phone) {

}