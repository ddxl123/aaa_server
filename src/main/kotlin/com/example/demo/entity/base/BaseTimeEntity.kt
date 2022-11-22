package com.example.demo.entity.base

import com.example.demo.share_generator.client_table_generator.annotation.ClientColumn
import java.time.Instant
import javax.persistence.*

@MappedSuperclass
open class BaseTimeEntity : BaseEntity() {

    /**
     * TODO: 需要使用客户端生成数据存入到数据库。
     */
    @ClientColumn
    @Column(nullable = false)
    var createdAt: Instant? = null

    /**
     * TODO: 需要使用客户端生成数据存入到数据库。
     */
    @ClientColumn
    @Column(nullable = false)
    var updatedAt: Instant? = null
}