package com.example.demo.entity.base

import java.time.Instant
import javax.persistence.*


@MappedSuperclass
open class BaseTimeEntity {

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