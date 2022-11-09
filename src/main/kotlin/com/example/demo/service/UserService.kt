package com.example.demo.service

import com.example.demo.pojo.User

interface UserService {
    fun getAll(): Iterable<User>
}