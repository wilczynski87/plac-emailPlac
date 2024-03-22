package com.plac.emailPlac.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class test {
    @GetMapping("/healthCheck")
    fun healthCheck():String {
        return "Ok"
    }
}