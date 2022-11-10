package com.example.demo.controller

import com.example.demo.entity.Users
import com.example.demo.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.time.Instant

@RestController
class DemoController(
        @Autowired
        val userRepository: UserRepository
) {

    @RequestMapping("/a")
    fun a(): Any? {

        userRepository.save(Users().also { user ->
            user.username = "dadasda";
            user.updatedAt = Instant.ofEpochSecond(0)
            user.createdAt = Instant.now()
            user.password = "ddddd"
        })
        return userRepository.findAll()
    }
}