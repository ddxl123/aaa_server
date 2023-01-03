package com.example.demo.entity

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor

interface UsersRepository : JpaRepository<Users, Long>, JpaSpecificationExecutor<Users>
