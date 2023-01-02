package com.example.demo.entity

import org.springframework.data.domain.Example
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.jpa.repository.support.SimpleJpaRepository
import java.util.*

 class UsersRepository : SimpleJpaRepository<Users, Long>() {
    /**
     * 根据邮箱查找用户。
     */
    fun findBy(email: String) {
        findOne(Example.of(Users().also { it.email = email }))
    }
}
