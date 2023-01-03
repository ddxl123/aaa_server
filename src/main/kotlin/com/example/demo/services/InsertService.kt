package com.example.demo.services

import com.example.demo.entity.Users
import com.example.demo.server_generator.output.Crt
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.Instant

@Service
class InsertService(
        val simpleService: SimpleService
) {
    /**
     * 根据 [email] 插入 [Users]。
     */
    fun insertUser(email: String): Users {
        return simpleService.usersRepository.save(Crt.users(
                age = null,
                email = email,
                password = null,
                phone = null,
                username = "还没有起名字",
                createdAt = Instant.now(),
                updatedAt = Instant.now(),
        ))
    }
}