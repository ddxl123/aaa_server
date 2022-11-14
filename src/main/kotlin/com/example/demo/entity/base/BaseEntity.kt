package com.example.demo.entity.base

import java.time.Instant
import javax.persistence.*


@MappedSuperclass
open class BaseEntity {

    /**
     * 由客户端或服务器生成。
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long? = null
    /**
     * TODO: 需要使用客户端生成数据存入到数据库。
     */
    @Column(nullable = false)
    var createdAt: Instant? = null

    /**
     * TODO: 需要使用客户端生成数据存入到数据库。
     */
    @Column(nullable = false)
    var updatedAt: Instant? = null
}