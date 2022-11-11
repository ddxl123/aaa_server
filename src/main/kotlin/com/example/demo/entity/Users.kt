package com.example.demo.entity

import com.example.demo.entity.base.BaseEntity
import javax.persistence.*


@Entity
class Users : BaseEntity() {
    @Column(nullable = true, length = 20)
    var username: String? = null

    @Column(nullable = true, length = 20)
    var password: String? = null

    @Column(nullable = true, length = 20)
    var email: String? = null

    @Column(nullable = true)
    var age: Byte? = null
}

