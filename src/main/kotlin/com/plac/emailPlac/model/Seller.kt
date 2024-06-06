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
class Seller(name:String = "Karol Wilczyński",
             address:Address = Address("Ostrowskiego", "102", "", "Wrocław", "53-238"),
             nip:String = "8942957044",
             email:String = "parkingostrowskiego@gmail.com",
             phone:String = "507036484",
             val mainAccount:String = "50 1950 0001 2006 0023 6241 0001",
             val vatAccount:String = "23 1950 0001 2006 0023 6241 0002",
             val prvAccount:String = "11 2490 1044 0000 4200 8845 2192",
) : Customer(name, address, nip, email, phone) {
    override fun toString(): String {
        return "Seller(mainAccount='$mainAccount', vatAccount='$vatAccount', prvAccount='$prvAccount')"
    }
}