package com.example.demo.repository

import com.example.demo.entity.Users
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Component

@Component
interface UserRepository : CrudRepository<Users, Long> {
}