package com.example.demo.controller

import com.example.demo.repository.UsersRepository
import com.example.demo.share_object.RegisterAndLoginDto
import com.example.demo.share_object.RegisterAndLoginVo
//import com.example.demo.share_object.RegisterAndLogin
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

    @PostMapping("/with_username")
    fun withUsername(r: RegisterAndLoginDto): Any? {

        return RegisterAndLoginVo()
    }
}