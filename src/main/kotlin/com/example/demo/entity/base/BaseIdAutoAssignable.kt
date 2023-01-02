package com.example.demo.entity.base

import com.example.demo.share_generator.client_table_generator.annotation.ClientColumn
import javax.persistence.Column
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.MappedSuperclass

@MappedSuperclass
open class BaseIdAutoAssignable : BaseTimeEntity() {

    /**
     * 自动分配的 id，由客户端或服务器自动生成。
     *
     * 即需要 id 生成策略 [GeneratedValue]
     */
    @ClientColumn
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(unique = true, nullable = false)
    var id: Long = -1

}