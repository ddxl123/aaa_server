package com.example.demo.repository;

import com.example.demo.entity.Users
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor

interface UsersRepository : JpaRepository<Users, Long>, JpaSpecificationExecutor<Users> {
}