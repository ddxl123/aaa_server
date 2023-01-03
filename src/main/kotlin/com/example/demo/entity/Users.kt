package com.example.demo.entity

import com.example.demo.entity.base.BaseIdAutoAssignable
import com.example.demo.share_generator.client_table_generator.annotation.ClientColumn
import com.example.demo.share_generator.client_table_generator.annotation.ClientTable
import javax.persistence.*
import kotlin.jvm.Transient


@ClientTable
@Entity
class Users : BaseIdAutoAssignable() {

    @Transient
    @ClientColumn(isOnlyLocal = true)
    @Column(nullable = false)
    var local_token: String? = null

    @ClientColumn
    @Column(nullable = false, length = 20)
    var username: String? = null

    /**
     * 当为手机号等登录时，密码为空。
     */
    @ClientColumn
    @Column(nullable = true, length = 20)
    var password: String? = null

    @ClientColumn
    @Column(nullable = true, length = 20)
    var email: String? = null


    /**
     * 因为前缀带有 +xx，因此是 String 类型。
     */
    @ClientColumn
    @Column(nullable = true)
    var phone: String? = null

    @ClientColumn
    @Column(nullable = true)
    var age: Byte? = null
}

