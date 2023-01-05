package com.example.demo.services

import com.example.demo.entity.Users
import org.springframework.data.domain.Example
import org.springframework.stereotype.Service

@Service
class QueryService(
        val simpleService: SimpleService
) {

    /**
     * 根据 [email] 查找 [Users]。
     *
     * 查找到多个将抛出异常。
     */
    fun findOneOrNullUserBy(email: String): Users? {
        val result = simpleService.usersRepository.findOne(Example.of(Users().also { it.email = email }))
        return if (result.isEmpty) null else result.get()
    }
}