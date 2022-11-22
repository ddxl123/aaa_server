package com.example.demo.entity

import com.example.demo.entity.base.BaseIdAutoAssignable
import com.example.demo.share_generator.client_table_generator.annotation.ClientColumn
import com.example.demo.share_generator.client_table_generator.annotation.ClientTable
import javax.persistence.*


@ClientTable
@Entity
class Users : BaseIdAutoAssignable() {
    @ClientColumn
    @Column(nullable = true, length = 20)
    var username: String? = null

    @ClientColumn
    @Column(nullable = true, length = 20)
    var password: String? = null

    @ClientColumn
    @Column(nullable = true, length = 20)
    var email: String? = null

    @ClientColumn
    @Column(nullable = true)
    var age: Byte? = null
}

