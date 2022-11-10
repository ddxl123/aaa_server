package com.example.demo.entity.base

import java.io.Serializable
import java.time.Instant
import javax.persistence.Column
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.MappedSuperclass


@MappedSuperclass
open class BaseEntity(

        // TODO:占时是根据数据库自动选择生成的方式，之后可能要修改为客户端生成id再存入到数据库。
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        var id: Long? = null,

        // TODO: 需要使用客户端生成数据存入到数据库。
        @Column(nullable = false)
        var createdAt: Instant? = null,

        // TODO: 需要使用客户端生成数据存入到数据库。
        @Column(nullable = false)
        var updatedAt: Instant? = null

) : Serializable