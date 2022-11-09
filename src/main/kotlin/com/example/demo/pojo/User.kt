package com.example.demo.pojo

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table
class User {
    @Id
    @GeneratedValue
    val id: Long? = null

    val username: String? = null

    val passWord: String? = null
}
