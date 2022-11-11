package com.example.demo.controller

import com.example.demo.entity.Users
import com.example.demo.repository.UsersRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.time.Instant

@RestController
class DemoController(
        @Autowired
        val usersRepository: UsersRepository
) {

    @RequestMapping("/a")
    fun a(): Any? {

        usersRepository.save(Users().also { user ->
            user.username = "dadasda";
            user.updatedAt = Instant.ofEpochSecond(0)
            user.createdAt = Instant.now()
            user.password = "ddddd"
        })
        return usersRepository.findAll()
    }
}