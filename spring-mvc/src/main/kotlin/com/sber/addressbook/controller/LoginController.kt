package com.sber.addressbook.controller

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping

@Controller
class LoginController {

    @GetMapping("/loginpage")
    fun login(): String {
        return "login"
    }
}