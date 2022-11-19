package com.example.demo.entity.base

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
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long? = null

}