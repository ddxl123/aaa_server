package com.example.demo.service

import com.example.demo.pojo.User
import com.example.demo.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class UserServiceImpl(
        @Autowired
        val userRepository: UserRepository
) : UserService {

    override fun getAll(): Iterable<User> {
        return userRepository.findAll();
    }
}