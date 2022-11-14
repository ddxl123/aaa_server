package com.example.demo.controller

import com.example.demo.repository.UsersRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/register_and_login")
class RegisterAndLoginController(
        @Autowired
        var usersRepository: UsersRepository,
) {

//    @PostMapping("/a")
//    fun a(registerAndLogin: RegisterAndLogin): Any? {
//        return ""
//    }
}