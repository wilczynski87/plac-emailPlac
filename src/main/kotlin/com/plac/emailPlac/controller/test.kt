package com.plac.emailPlac.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class test {

    @GetMapping("/templateTest1")
    fun test1():String {
        return "test";
    }
}