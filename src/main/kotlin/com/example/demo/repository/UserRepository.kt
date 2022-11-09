package com.example.demo.repository

import com.example.demo.pojo.User
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.stereotype.Component

@Component
interface UserRepository : CrudRepository<User, Long> {
}